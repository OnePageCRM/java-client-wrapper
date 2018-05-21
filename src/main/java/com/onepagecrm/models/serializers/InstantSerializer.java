package com.onepagecrm.models.serializers;

import com.onepagecrm.models.helpers.TextHelper;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class InstantSerializer extends DateTimeSerializer<Instant> {

    private static volatile InstantSerializer INSTANCE;

    private static final Object sLock = new Object();

    public static InstantSerializer getInstance() {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = new InstantSerializer();
                }
            }
        }
        return INSTANCE;
    }

    private InstantSerializer() {
        // Don't allow external initialisation.
    }

    @Override
    public ZoneId defaultZoneId() {
        return ZoneId.of(ZoneOffset.UTC.getId());
    }

    @Override
    public DateTimeFormatter defaultFormatter() {
        return DateTimeFormatter.ISO_INSTANT;
    }

    @Override
    public Instant parse(String instant, ZoneId zoneId, DateTimeFormatter formatter) {
        throw new IllegalStateException("Instant should already have time zone data");
    }

    @Override
    public Instant parse(String instant, ZoneId zoneId) {
        return parse(instant, zoneId, defaultFormatter());
    }

    @Override
    public Instant parse(String instant, DateTimeFormatter formatter) {
        if (TextHelper.isEmpty(instant)) {
            return null;
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter object cannot be null");
        }
        try {
            return ZonedDateTime.parse(instant, formatter.withZone(defaultZoneId())).toInstant();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public Instant parse(String instant) {
        return parse(instant, defaultFormatter());
    }

    @Override
    public String format(Instant instant, ZoneId zoneId, DateTimeFormatter formatter) {
        if (instant == null) {
            return null;
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter object cannot be null");
        }
        return instant.atZone(zoneId).format(formatter);
    }

    @Override
    public String format(Instant instant, ZoneId zoneId) {
        return format(instant, zoneId, defaultFormatter());
    }

    @Override
    public String format(Instant instant, DateTimeFormatter formatter) {
        return format(instant, defaultZoneId(), formatter);
    }

    @Override
    public String format(Instant instant) {
        return format(instant, defaultZoneId(), defaultFormatter());
    }

    public Instant ofSeconds(Long seconds) {
        return seconds != null ? Instant.ofEpochSecond(seconds) : null;
    }

    public Instant ofMillis(Long millis) {
        return millis != null ? Instant.ofEpochMilli(millis) : null;
    }

    public Long seconds(Instant instant) {
        return instant != null ? instant.getEpochSecond() : null;
    }

    public Long millis(Instant instant) {
        return instant != null ? instant.toEpochMilli() : null;
    }
}
