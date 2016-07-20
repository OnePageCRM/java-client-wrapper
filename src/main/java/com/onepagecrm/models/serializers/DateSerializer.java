package com.onepagecrm.models.serializers;

import com.onepagecrm.models.internal.OPCRMColors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class DateSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(DateSerializer.class.getName());

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateTimeFormat;

    static {
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static SimpleDateFormat dateCallTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat friendlyDateFormat = new SimpleDateFormat("MMM dd");
    public static SimpleDateFormat friendlyDateAndYearFormat = new SimpleDateFormat("MMM dd, yyyy");
    public static SimpleDateFormat friendlyDateTimeAndYearFormat = new SimpleDateFormat("HH:mm MMM dd, yyyy");

    private static final String ASAP = "ASAP";
    private static final String TODAY = "TODAY";
    private static final String WAITING = "WAITING";

    private static final int ASAP_OVERDUE_COLOR = OPCRMColors.FLAG_RED;
    private static final int TODAY_COLOR = OPCRMColors.FLAG_ORANGE;
    private static final int FUTURE_WAITING_COLOR = OPCRMColors.FLAG_GREY_BROWN;

    public static Long dateInMillis(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date fromFormattedString(String dateStr) {
        if (dateStr != null) {
            try {
                return dateTimeFormat.parse(dateStr);
            } catch (ParseException e) {
                try {
                    return dateFormat.parse(dateStr);
                } catch (ParseException ex) {
                    LOG.severe("Error parsing date string to date object");
                    LOG.severe(e.toString());
                    LOG.severe(ex.toString());
                }
            }
        }
        return null;
    }

    public static Date fromCallFormattedString(String dateStr) {
        try {
            return dateCallTimeFormat.parse(dateStr);
        } catch (Exception ex) {
            LOG.severe("Error parsing date string to date object");
            LOG.severe(ex.toString());
        }
        return null;
    }

    public static String toFormattedDateTimeString(Date date) {
        if (date != null) {
            return dateTimeFormat.format(date);
        }
        return null;
    }

    public static String toFormattedDateString(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        }
        return null;
    }

    public static String toFormattedDateAndYearString(Date date) {
        if (date != null) {
            return friendlyDateAndYearFormat.format(date);
        }
        return null;
    }

    public static String toFormattedDateTimeAndYearString(Date date) {
        if (date != null) {
            return friendlyDateTimeAndYearFormat.format(date);
        }
        return null;
    }

    public static String toFormattedTimeString(Date date) {
        if (date != null) {
            return timeFormat.format(date);
        }
        return null;
    }

    public static String toFriendlyDateString(Date date) {
        Date today = new Date();
        if (date != null) {
            if (dateFormat.format(date).equals(dateFormat.format(today))) {
                return TODAY;
            } else {
                return friendlyDateFormat.format(date);
            }
        }
        return null;
    }

    public static int getDateColour(Date date, String status) {
        if (date != null) {
            return getColorByDate(date);
        } else if (status != null) {
            return getColorByStatus(status);
        } else {
            return FUTURE_WAITING_COLOR;
        }
    }

    public static int getColorByDate(Date date) {
        Date today = new Date();
        Date todayDate = null, actionDate = null;
        try {
            todayDate = dateFormat.parse(dateFormat.format(today));
            actionDate = dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            LOG.severe("Error creating date object using specified format");
            LOG.severe(e.toString());
        }
        if (actionDate.after(todayDate)) {
            return FUTURE_WAITING_COLOR;
        } else if (actionDate.equals(todayDate)) {
            return TODAY_COLOR;
        } else if (actionDate.before(todayDate)) {
            return ASAP_OVERDUE_COLOR;
        } else {
            return FUTURE_WAITING_COLOR;
        }
    }

    public static int getColorByStatus(String status) {
        if (status.equalsIgnoreCase(WAITING)) {
            return FUTURE_WAITING_COLOR;
        } else if (status.equalsIgnoreCase(TODAY)) {
            return TODAY_COLOR;
        } else if (status.equalsIgnoreCase(ASAP)) {
            return ASAP_OVERDUE_COLOR;
        } else {
            return FUTURE_WAITING_COLOR;
        }
    }

    public static Date fromNumString(String s) {
        Number lNumber = parseNumber(s);
        if (lNumber != null) {
            return new Date(lNumber.longValue());
        }
        return null;
    }

    public static Date fromTimestamp(String s) {
        Number lNumber = parseNumber(s);
        if (lNumber != null) {
            return new Date(lNumber.longValue() * 1000);
        }
        return null;
    }

    public static Long toTimestamp(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }
}
