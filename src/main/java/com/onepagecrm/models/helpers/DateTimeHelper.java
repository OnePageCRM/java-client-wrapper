package com.onepagecrm.models.helpers;

import com.onepagecrm.models.internal.SystemClock;
import com.onepagecrm.models.serializers.DateTimeSerializer;
import com.onepagecrm.models.serializers.InstantSerializer;
import com.onepagecrm.models.serializers.LocalDateSerializer;
import com.onepagecrm.models.serializers.ZonedDateTimeSerializer;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

import static com.onepagecrm.models.helpers.ActionHelper.STATUS_TODAY;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DateTimeHelper {

    public static LocalDate today() {
        return LocalDate.now(SystemClock.getInstance());
    }

    public static boolean isToday(LocalDate date) {
        return date != null && date.isEqual(today());
    }

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(SystemClock.getInstance());
    }

    public static ZonedDateTime nowZoned(ZoneId zoneId) {
        return ZonedDateTime.of(nowLocal(), zoneId);
    }

    public static Instant nowUTC() {
        return Instant.now(SystemClock.getInstance());
    }

    public static DateTimeFormatter timeFormat(boolean is24hr) { // DateSerializer#getDateTimeYearFormat
        return is24hr
                ? DateTimeSerializer.FORMATTER_FRIENDLY_TIME
                : DateTimeSerializer.FORMATTER_FRIENDLY_TIME_AM_PM;
    }

    public static DateTimeFormatter timeDateYearFormat(boolean is24hr) { // DateSerializer#getDateTimeYearFormat
        return is24hr
                ? DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR
                : DateTimeSerializer.FORMATTER_FRIENDLY_TIME_AM_PM_DATE_YEAR;
    }

    public static String formatFriendlyDate(LocalDate date) {
        if (date == null) return null;
        // Return date in format "MMM dd" (uppercase).
        return DateTimeHelper.isToday(date)
                ? STATUS_TODAY
                : LocalDateSerializer.getInstance()
                .format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE)
                .toUpperCase(Locale.ENGLISH);
    }

    public static String formatFriendlyDate(ZonedDateTime date) {
        if (date == null) return null;
        // Return date in format "MMM dd" (uppercase).
        return DateTimeHelper.isToday(date.toLocalDate())
                ? STATUS_TODAY
                : ZonedDateTimeSerializer.getInstance()
                .format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE)
                .toUpperCase(Locale.ENGLISH);
    }

    public static String formatFriendlyDate(Instant date) {
        if (date == null) return null;
        // Return date in format "MMM dd" (uppercase).
        return DateTimeHelper.isToday(date.atZone(InstantSerializer.getInstance().defaultZoneId()).toLocalDate())
                ? STATUS_TODAY
                : InstantSerializer.getInstance()
                .format(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE)
                .toUpperCase(Locale.ENGLISH);
    }

    public static LocalDate fromFriendlyDate(String date) {
        if (date == null) return null;
        return LocalDateSerializer.getInstance()
                .parse(date, DateTimeSerializer.FORMATTER_FRIENDLY_DATE);
    }
}
