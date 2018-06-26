package com.onepagecrm.models.internal;

import org.threeten.bp.Clock;

/**
 * Created by Cillian Myles on 25/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public final class SystemClock {

    private static volatile Clock INSTANCE;

    private static final Object sLock = new Object();

    public static Clock getInstance() {
        return getInstance(null);
    }

    public static void inject(Clock injected) {
        INSTANCE = null;
        getInstance(injected);
    }

    public static void restoreToDefault() {
        inject(null);
    }

    private static Clock getInstance(Clock clock) {
        if (INSTANCE == null) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = clock != null ? clock : Clock.systemDefaultZone();
                }
            }
        }
        return INSTANCE;
    }
}
