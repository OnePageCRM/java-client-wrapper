package com.onepagecrm.models.serializers;

import com.onepagecrm.models.helpers.TextHelper;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;


/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class LocalDateSerializer extends DateTimeSerializer<LocalDate> {

    private static volatile LocalDateSerializer INSTANCE;

    private static final Object sLock = new Object();

    public static LocalDateSerializer getInstance() {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDateSerializer();
                }
            }
        }
        return INSTANCE;
    }

    private LocalDateSerializer() {
        // Don't allow external initialisation.
    }

    @Override
    public DateTimeFormatter defaultFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Override
    public LocalDate parse(String date, DateTimeFormatter formatter) {
        if (TextHelper.isEmpty(date)) {
            throw new IllegalArgumentException("String to parsed cannot be null or empty.");
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter object cannot be null.");
        }
        return LocalDate.parse(date, formatter);
    }

    @Override
    public String format(LocalDate date, DateTimeFormatter formatter) {
        if (date == null) {
            throw new IllegalArgumentException("LocalDate to be formatted cannot be null.");
        }
        if (formatter == null) {
            throw new IllegalArgumentException("DateTimeFormatter object cannot be null.");
        }
        return date.format(formatter);
    }
}
