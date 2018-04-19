package com.onepagecrm.models.serializers;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ZonedDateTimeSerializer extends DateTimeSerializer<ZonedDateTime> {

    private static volatile ZonedDateTimeSerializer INSTANCE;

    private static final Object sLock = new Object();

    public static ZonedDateTimeSerializer getInstance() {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = new ZonedDateTimeSerializer();
                }
            }
        }
        return INSTANCE;
    }

    private ZonedDateTimeSerializer() {
        // Don't allow external initialisation.
    }

    @Override
    public DateTimeFormatter defaultFormatter() {
        return DateTimeFormatter.ofPattern(DateTimeSerializer.PATTERN_FRIENDLY_TIME_DATE_YEAR);
    }

    @Override
    public ZonedDateTime parse(String t, DateTimeFormatter formatter) {
        throw new IllegalArgumentException("ZonedDateTime should only be used for formatting/displaying.");
    }

    @Override
    public ZoneId defaultZoneId() {
        throw new IllegalArgumentException("ZonedDateTime should already have time zone data.");
    }

    @Override
    public String format(ZonedDateTime zonedDateTime) {
        return format(zonedDateTime, defaultFormatter());
    }

    @Override
    public String format(ZonedDateTime zonedDateTime, DateTimeFormatter formatter) {
        if (zonedDateTime == null) {
            return null;
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter cannot be null.");
        }
        return zonedDateTime.format(formatter);
    }

    @Override
    public String format(ZonedDateTime zonedDateTime, ZoneId zoneId, DateTimeFormatter formatter) {
        throw new IllegalArgumentException("ZonedDateTime should already have time zone data.");
    }
}
