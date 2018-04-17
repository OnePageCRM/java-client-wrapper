package com.onepagecrm.models.serializers;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.jdk8.DefaultInterfaceTemporalAccessor;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public abstract class DateTimeSerializer<T extends DefaultInterfaceTemporalAccessor> {

    public static final String PATTERN_DATE_DEFAULT = "yyyy-MM-dd";

    public abstract DateTimeFormatter defaultFormatter();

    public abstract T parse(String t, DateTimeFormatter formatter);

    public T parse(String t) {
        return parse(t, defaultFormatter());
    }

    public abstract String format(T t, DateTimeFormatter formatter);

    public String format(T t) {
        return format(t, defaultFormatter());
    }
}
