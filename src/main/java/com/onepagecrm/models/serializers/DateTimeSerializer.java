package com.onepagecrm.models.serializers;

import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.jdk8.DefaultInterfaceTemporalAccessor;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public abstract class DateTimeSerializer<T extends DefaultInterfaceTemporalAccessor> {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_DEFAULT = PATTERN_DATE;
    // TODO: add more patterns

    public abstract DateTimeFormatter defaultFormatter();

    public abstract T parse(String t, DateTimeFormatter formatter);

    public T parse(String t) {
        return parse(t, defaultFormatter());
    }

    public abstract ZoneId defaultZoneId();

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
