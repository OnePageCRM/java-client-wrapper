package com.onepagecrm.models.helpers;

import com.onepagecrm.models.internal.SystemClock;
import com.onepagecrm.models.serializers.DateTimeSerializer;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class DateHelper {

    public static LocalDate today() {
        return LocalDate.now(SystemClock.getInstance());
    }

//    public static LocalDate today(ZoneId zoneId) {
//        return LocalDate.now(zoneId);
//    }

    public static boolean isToday(LocalDate date) {
        return date != null && date.isEqual(today());
    }

//    public static boolean isToday(ZoneId zoneId, LocalDate date) {
//        return date != null && date.isEqual(today(zoneId));
//    }

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
}
