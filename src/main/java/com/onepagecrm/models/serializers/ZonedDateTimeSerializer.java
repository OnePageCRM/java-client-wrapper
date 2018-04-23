package com.onepagecrm.models.serializers;

import com.onepagecrm.models.helpers.TextHelper;
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
    public ZoneId defaultZoneId() {
        throw new IllegalStateException("ZonedDateTime should already have time zone data");
    }

    @Override
    public DateTimeFormatter defaultFormatter() {
        return DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR;
    }

    @Override
    public ZonedDateTime parse(String zonedDateTime, ZoneId zoneId, DateTimeFormatter formatter) {
        if (TextHelper.isEmpty(zonedDateTime)) {
            return null;
        }
        if (zoneId == null) {
            throw new IllegalArgumentException("ZonedDateTime needs ZoneId info");
        }
        return ZonedDateTime.parse(zonedDateTime, formatter);
    }

    @Override
    public ZonedDateTime parse(String zonedDateTime, ZoneId zoneId) {
        return parse(zonedDateTime, zoneId, defaultFormatter());
    }

    @Override
    public ZonedDateTime parse(String zonedDateTime, DateTimeFormatter formatter) {
        throw new IllegalStateException("ZonedDateTime should only be used for formatting/displaying");
    }

    @Override
    public ZonedDateTime parse(String zonedDateTime) {
        return parse(zonedDateTime, defaultFormatter());
    }

    @Override
    public String format(ZonedDateTime zonedDateTime, ZoneId zoneId, DateTimeFormatter formatter) {
        throw new IllegalStateException("ZonedDateTime should already have time zone data");
    }

    @Override
    public String format(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return format(zonedDateTime, zoneId, defaultFormatter());
    }

    @Override
    public String format(ZonedDateTime zonedDateTime, DateTimeFormatter formatter) {
        if (zonedDateTime == null) {
            return null;
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter cannot be null");
        }
        return zonedDateTime.format(formatter);
    }

    @Override
    public String format(ZonedDateTime zonedDateTime) {
        return format(zonedDateTime, defaultFormatter());
    }
}
