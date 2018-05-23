package com.onepagecrm.models.helpers;

import com.onepagecrm.models.internal.SystemClock;
import com.onepagecrm.models.serializers.time.DateTimeTestHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.threeten.bp.Clock;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

/**
 * Created by Cillian Myles on 23/05/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class DateTimeHelperTest {

    // For this test TODAY is MAY 23, 2018 (2018-05-23).
    private static final LocalDate TODAY = LocalDate.parse("2018-05-23");
    private static final Instant NOW = Instant.parse("2018-05-23T09:00:00Z");
    private static final ZoneId UTC = DateTimeTestHelper.ZONE_ID_UTC;
    private static final Clock FIXED_CLOCK = Clock.fixed(NOW, UTC);

    // Yesterday / 2018-05-22 / May 22, 2018
    private LocalDate mYesterdayDate;
    private String mYesterdayFormattedFriendly;
    private String mYesterdayFormatted;

    // Today / 2018-05-23 / TODAY
    private LocalDate mTodayDate;
    private String mTodayFormattedFriendly;
    private String mTodayFormatted;

    // Tomorrow / 2018-05-24 / May 24, 2018
    private LocalDate mTomorrowDate;
    private String mTomorrowFormattedFriendly;
    private String mTomorrowFormatted;

    @BeforeClass
    public static void init() throws Exception {
        // Use hardcoded Clock instead of real system clock.
        SystemClock.inject(FIXED_CLOCK);
    }

    @Before
    public void setUp() throws Exception {
        mYesterdayFormattedFriendly = "May 22, 2018";
        mYesterdayFormatted = "2018-05-22";
        mYesterdayDate = TODAY.minusDays(1);

        mTodayFormattedFriendly = "TODAY";
        mTodayFormatted = "2018-05-23";
        mTodayDate = TODAY;

        mTomorrowFormattedFriendly = "May 24, 2018";
        mTomorrowFormatted = "2018-05-24";
        mTomorrowDate = TODAY.plusDays(1);
    }

    @After
    public void tearDown() throws Exception {

    }

    @AfterClass
    public static void destroy() throws Exception {
        SystemClock.restoreToDefault();
    }

    @Test
    public void formatDate() {

    }

    @Test
    public void formatDateYear() {

    }

    @Test
    public void formatDateFriendly() {

    }

    @Test
    public void formatDateYearFriendly() {

    }
}