package com.onepagecrm.models.helpers;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.internal.OPCRMColors;
import com.onepagecrm.models.internal.PredefinedAction;
import com.onepagecrm.models.serializers.DateTimeSerializer;
import com.onepagecrm.models.serializers.LocalDateSerializer;
import com.onepagecrm.models.serializers.ZonedDateTimeSerializer;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ActionHelper {

    private static final Logger LOG = Logger.getLogger(ActionHelper.class.getSimpleName());

    /*
     * Constants.
     */

    public static final String STATUS_ASAP = "ASAP";
    public static final String STATUS_TODAY = "TODAY";
    public static final String STATUS_WAITING = "WAITING";

    public static final int COLOR_ASAP_OVERDUE = OPCRMColors.FLAG_RED;
    public static final int COLOR_TODAY = OPCRMColors.FLAG_ORANGE;
    public static final int COLOR_FUTURE_WAITING = OPCRMColors.FLAG_GREY_BROWN;
    public static final int COLOR_DEFAULT = COLOR_FUTURE_WAITING;

    /**
     * Default date/time is TODAY at 9am.
     *
     * @return LocalDateTime object for TODAY at 9am.
     */
    public static LocalDateTime defaultLocalDateTime() {
        // Default is TODAY at 9am.
        return LocalDateTime.now()
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * Default date/time is TODAY at 9am w/ time zone info.
     *
     * @param zoneId - of the device / OnePage settings.
     * @return ZonedDateTime object for TODAY at 9am.
     */
    public static ZonedDateTime defaultZonedDateTime(ZoneId zoneId) {
        // Default is TODAY at 9am.
        return ZonedDateTime.of(defaultLocalDateTime(), zoneId);
    }

    // TODO: validate zoneid and zdt!?
    public static Action promote(ZoneId zoneId, PredefinedAction predefined) {
        final String actionText = predefined != null ? predefined.getText() : null;
        final int daysToBeAdded = predefined != null ? predefined.getDays() : 0;
        // Default date is TODAY at 9am.
        LocalDate today = DateHelper.today();
        ZonedDateTime todayZdt = defaultZonedDateTime(zoneId);
        // Apply the updated date/time to action.
        return new Action()
                .setText(actionText)
                .setJ8Date(today.plusDays(daysToBeAdded))
                .setJ8ExactTime(todayZdt.plusDays(daysToBeAdded));
    }

    /*
     * Dates.
     */

    public static String getFriendlyDate(Action action) { // Action#getFriendlyDateString
        if (action == null) {
            return null;
        }

        if (action.getJ8Date() != null) {
            // Return date in format "MMM dd" (uppercase).
            return LocalDateSerializer.getInstance().format(
                    action.getJ8Date(), DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
        } else if (action.getStatus() != null) {
            // Return status (uppercase).
            return action.getStatus().toString().toUpperCase();
        }

        return null; // <- needed to correctly display contacts w/out NA's in Action Stream.
    }

    public static String getFriendlyActionText(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        if (action == null || action.getJ8ExactTime() == null) {
            return actionText;
        }
        return formatTimeAndActionText(zoneId, is24hr, action);
    }

    public static String getFriendlyTimeAndDate(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        if (action == null || action.getJ8ExactTime() == null) {
            return actionText;
        }
        return formatTimeAndDate(zoneId, is24hr, action);
    }

    // ----------------------------------------

    public static String formatDate(Action action) {
        final LocalDate date = action != null ? action.getJ8Date() : DateHelper.today();
        return LocalDateSerializer.getInstance().format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }

    public static String formatDateYear(Action action) {
        final LocalDate date = action != null ? action.getJ8Date() : DateHelper.today();
        return LocalDateSerializer.getInstance().format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR);
    }

    public static String formatTimeAndDate(ZoneId zoneId, boolean is24hr, Action action) {
        final ZonedDateTime exactTime = action != null && action.getJ8ExactTime(zoneId) != null
                ? action.getJ8ExactTime(zoneId)
                : defaultZonedDateTime(zoneId);
        return ZonedDateTimeSerializer.getInstance().format(exactTime, DateHelper.timeDateYearFormat(is24hr));
    }

    public static String formatTimeAndActionText(ZoneId zoneId, boolean is24hr, Action action) {
        final String actionText = action != null && action.getText() != null ? action.getText() : "";
        final ZonedDateTime exactTime = action != null && action.getJ8ExactTime() != null
                ? action.getJ8ExactTime(zoneId)
                : defaultZonedDateTime(zoneId);
        return String.format("%s %s",
                ZonedDateTimeSerializer.getInstance().format(exactTime, DateHelper.timeFormat(is24hr)),
                actionText);
    }

    // ----------------------------------------

    public static Action extractDate(ZoneId zoneId, boolean is24hr, Action action, String dateString) {
        if (TextHelper.isEmpty(dateString)) {
            return action;
        }
        if (action == null) {
            action = new Action();
        }

        final Instant instant = parseDate(zoneId, is24hr, action.getStatus(), dateString);
        final LocalDate date = instant != null ? LocalDateTime.ofInstant(instant, zoneId).toLocalDate() : null;

        if (action.getStatus() == null) {
            action.setStatus(Action.Status.OTHER); // TODO: verify!?
        }

        switch (action.getStatus()) {
            case DATE:
            case QUEUED_WITH_DATE: {
                action.setJ8Date(date);
                break;
            }
            case DATE_TIME: {
                action.setJ8ExactTime(instant);
                action.setJ8Date(date);
                break;
            }
        }

        return action;
    }

    public static Instant parseDate(ZoneId zoneId, boolean is24hr, Action.Status status, String dateString) {
        if (status == null) {
            status = Action.Status.DATE;
        }

        ZonedDateTime dateTime;

        switch (status) {
            default: {
                dateTime = ZonedDateTimeSerializer.getInstance().parse(
                        dateString, zoneId, DateTimeSerializer.FORMATTER_FRIENDLY_DATE_YEAR);
                break;
            }
            case DATE_TIME: {
                dateTime = ZonedDateTimeSerializer.getInstance().parse(
                        dateString, zoneId, DateHelper.timeDateYearFormat(is24hr));
                break;
            }
        }

        return dateTime != null ? dateTime.toInstant() : null;
    }

    /*
     * Flags/colours.
     */

    public static int getFlagColor(Action action) {
        if (action == null) {
            return COLOR_DEFAULT;
        }
        return getFlagColor(action.getJ8Date(), action.getStatus());
    }

    private static int getFlagColor(LocalDate date, Action.Status status) {
        if (date != null) {
            return getColorByDate(date);
        } else if (status != null) {
            return getColorByStatus(status);
        }
        return COLOR_DEFAULT;
    }

    private static int getFlagColor(LocalDate date, String status) {
        if (date != null) {
            return getColorByDate(date);
        } else if (status != null) {
            return getColorByStatus(status);
        }
        return COLOR_DEFAULT;
    }

    private static int getColorByDate(LocalDate date) {
        if (date == null) {
            return COLOR_DEFAULT;
        }

        final LocalDate today = DateHelper.today();

        if (date.isAfter(today)) {
            return COLOR_FUTURE_WAITING;
        } else if (date.isEqual(today)) {
            return COLOR_TODAY;
        } else if (date.isBefore(today)) {
            return COLOR_ASAP_OVERDUE;
        }

        return COLOR_DEFAULT;
    }

    private static int getColorByStatus(Action.Status status) {
        if (status == null) {
            return COLOR_DEFAULT;
        }
        switch (status) {
            case ASAP: {
                return COLOR_ASAP_OVERDUE;
            }
            case WAITING: {
                return COLOR_FUTURE_WAITING;
            }
        }
        return COLOR_DEFAULT;
    }

    private static int getColorByStatus(String status) {
        if (TextHelper.isEmpty(status)) {
            return COLOR_DEFAULT;
        }

        if (STATUS_WAITING.equalsIgnoreCase(status)) {
            return COLOR_FUTURE_WAITING;
        } else if (STATUS_TODAY.equalsIgnoreCase(status)) {
            return COLOR_TODAY;
        } else if (STATUS_ASAP.equalsIgnoreCase(status)) {
            return COLOR_ASAP_OVERDUE;
        }

        return COLOR_DEFAULT;
    }
}
