package com.onepagecrm.models.serializers;

import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.jdk8.DefaultInterfaceTemporalAccessor;

import static org.threeten.bp.format.DateTimeFormatter.ofPattern;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class DateTimeSerializer<T extends DefaultInterfaceTemporalAccessor> {

    // Dates.
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER_DATE = ofPattern(PATTERN_DATE);

    // Times.
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final DateTimeFormatter FORMATTER_DATE_TIME = ofPattern(PATTERN_DATE_TIME);

    // Friendly (mixes).
    public static final String PATTERN_FRIENDLY_TIME = "HH:mm";
    public static final String PATTERN_FRIENDLY_TIME_AM_PM = "hh:mma";
    public static final String PATTERN_FRIENDLY_DATE = "MMM dd";
    public static final String PATTERN_FRIENDLY_DATE_YEAR = "MMM dd, yyyy";
    public static final String PATTERN_FRIENDLY_TIME_DATE_YEAR = "HH:mm MMM dd, yyyy";
    public static final String PATTERN_FRIENDLY_TIME_AM_PM_DATE_YEAR = "hh:mma MMM dd, yyyy";

    public static final DateTimeFormatter FORMATTER_FRIENDLY_TIME = ofPattern(PATTERN_FRIENDLY_TIME);
    public static final DateTimeFormatter FORMATTER_FRIENDLY_TIME_AM_PM = ofPattern(PATTERN_FRIENDLY_TIME_AM_PM);
    public static final DateTimeFormatter FORMATTER_FRIENDLY_DATE = ofPattern(PATTERN_FRIENDLY_DATE);
    public static final DateTimeFormatter FORMATTER_FRIENDLY_DATE_YEAR = ofPattern(PATTERN_FRIENDLY_DATE_YEAR);
    public static final DateTimeFormatter FORMATTER_FRIENDLY_TIME_DATE_YEAR = ofPattern(PATTERN_FRIENDLY_TIME_DATE_YEAR);
    public static final DateTimeFormatter FORMATTER_FRIENDLY_TIME_AM_PM_DATE_YEAR = ofPattern(PATTERN_FRIENDLY_TIME_AM_PM_DATE_YEAR);

    public abstract ZoneId defaultZoneId();

    public abstract DateTimeFormatter defaultFormatter();

    public abstract T parse(String t, ZoneId zoneId, DateTimeFormatter formatter);

    public T parse(String t, ZoneId zoneId) {
        return parse(t, zoneId, defaultFormatter());
    }

    public T parse(String t, DateTimeFormatter formatter) {
        return parse(t, defaultZoneId(), formatter);
    }

    public T parse(String t) {
        return parse(t, defaultZoneId(), defaultFormatter());
    }

    public abstract String format(T t, ZoneId zoneId, DateTimeFormatter formatter);

    public String format(T t, ZoneId zoneId) {
        return format(t, zoneId, defaultFormatter());
    }

    public String format(T t, DateTimeFormatter formatter) {
        return format(t, defaultZoneId(), formatter);
    }

    public String format(T t) {
        return format(t, defaultZoneId(), defaultFormatter());
    }
}
