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

import static org.junit.Assert.assertEquals;

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
    private String mYesterdayFormatted;
    private String mYesterdayFormattedYear;
    private String mYesterdayFormattedFriendly;
    private String mYesterdayFormattedYearFriendly;

    // Today / 2018-05-23 / TODAY
    private LocalDate mTodayDate;
    private String mTodayFormatted;
    private String mTodayFormattedYear;
    private String mTodayFormattedFriendly;
    private String mTodayFormattedYearFriendly;

    // Tomorrow / 2018-05-24 / May 24, 2018
    private LocalDate mTomorrowDate;
    private String mTomorrowFormatted;
    private String mTomorrowFormattedYear;
    private String mTomorrowFormattedFriendly;
    private String mTomorrowFormattedYearFriendly;

    @BeforeClass
    public static void init() throws Exception {
        // Use hardcoded Clock instead of real system clock.
        SystemClock.inject(FIXED_CLOCK);
    }

    @Before
    public void setUp() throws Exception {
        mYesterdayDate = TODAY.minusDays(1);
        mYesterdayFormatted = "May 22";
        mYesterdayFormattedYear = "May 22, 2018";
        mYesterdayFormattedFriendly = "May 22";
        mYesterdayFormattedYearFriendly = "May 22, 2018";

        mTodayDate = TODAY;
        mTodayFormatted = "May 23";
        mTodayFormattedYear = "May 23, 2018";
        mTodayFormattedFriendly = "TODAY";
        mTodayFormattedYearFriendly = "TODAY";

        mTomorrowDate = TODAY.plusDays(1);
        mTomorrowFormatted = "May 24";
        mTomorrowFormattedYear = "May 24, 2018";
        mTomorrowFormattedFriendly = "May 24";
        mTomorrowFormattedYearFriendly = "May 24, 2018";
    }

    @After
    public void tearDown() throws Exception {

    }

    @AfterClass
    public static void destroy() throws Exception {
        SystemClock.restoreToDefault();
    }

    @Test
    public void formatDate() throws Exception {
        assertEquals("Formatted date does not match",
                mYesterdayFormatted,
                DateTimeHelper.formatDate(mYesterdayDate));

        assertEquals("Formatted date does not match",
                mTodayFormatted,
                DateTimeHelper.formatDate(mTodayDate));

        assertEquals("Formatted date does not match",
                mTomorrowFormatted,
                DateTimeHelper.formatDate(mTomorrowDate));
    }

    @Test
    public void formatDateYear() throws Exception {
        assertEquals("Formatted date does not match",
                mYesterdayFormattedYear,
                DateTimeHelper.formatDateYear(mYesterdayDate));

        assertEquals("Formatted date does not match",
                mTodayFormattedYear,
                DateTimeHelper.formatDateYear(mTodayDate));

        assertEquals("Formatted date does not match",
                mTomorrowFormattedYear,
                DateTimeHelper.formatDateYear(mTomorrowDate));
    }

    @Test
    public void formatDateFriendly() throws Exception {
        assertEquals("Formatted date does not match",
                mYesterdayFormattedFriendly,
                DateTimeHelper.formatDateFriendly(mYesterdayDate));

        assertEquals("Formatted date does not match",
                mTodayFormattedFriendly,
                DateTimeHelper.formatDateFriendly(mTodayDate));

        assertEquals("Formatted date does not match",
                mTomorrowFormattedFriendly,
                DateTimeHelper.formatDateFriendly(mTomorrowDate));
    }

    @Test
    public void formatDateYearFriendly() throws Exception {
        assertEquals("Formatted date does not match",
                mYesterdayFormattedYearFriendly,
                DateTimeHelper.formatDateYearFriendly(mYesterdayDate));

        assertEquals("Formatted date does not match",
                mTodayFormattedYearFriendly,
                DateTimeHelper.formatDateYearFriendly(mTodayDate));

        assertEquals("Formatted date does not match",
                mTomorrowFormattedYearFriendly,
                DateTimeHelper.formatDateYearFriendly(mTomorrowDate));
    }
}