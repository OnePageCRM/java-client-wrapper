package com.onepagecrm.models.helpers;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.internal.OPCRMColors;
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

import static com.onepagecrm.models.serializers.time.DateTimeTestHelper.ZONE_ID_ET;
import static com.onepagecrm.models.serializers.time.DateTimeTestHelper.ZONE_ID_UTC;
import static org.junit.Assert.assertEquals;

/**
 * Created by Cillian Myles on 24/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings("FieldCanBeLocal")
public class ActionHelperTest {

    // For this test TODAY is APR 22, 2018 (2018-04-22).
    private static final LocalDate TODAY = LocalDate.parse("2018-04-22");
    private static final Instant NOW = Instant.parse("2018-04-22T09:00:00Z");
    private static final ZoneId UTC = DateTimeTestHelper.ZONE_ID_UTC;
    private static final Clock FIXED_CLOCK = Clock.fixed(NOW, UTC);

    // ASAP
    private Action mAsapAction;
    private String mAsapFriendlyDate;
    private int mAsapColor;

    // Yesterday / 2018-04-21 / APR 21, 2018
    private Action mYesterdayAction;
    private LocalDate mYesterdayDate;
    private String mYesterdayFriendlyDate;
    private int mYesterdayColor;

    // Today / 2018-04-22 / TODAY
    private Action mTodayAction;
    private LocalDate mTodayDate;
    private String mTodayFriendlyDate;
    private int mTodayColor;

    // Tomorrow / 2018-04-23 / APR 23, 2018
    private Action mTomorrowAction;
    private LocalDate mTomorrowDate;
    private String mTomorrowFriendlyDate;
    private int mTomorrowColor;

    // ASAP
    private Action mWaitingAction;
    private String mWaitingFriendlyDate;
    private int mWaitingColor;

    // DATE w/ TIME
    // -> 1467360000
    // -> Fri, 01 Jul 2016 08:00:00 UTC
    // -> Fri, 01 Jul 2016 04:00:00 EST
    private Action mTimedAction;
    private Instant mTimedExactTime;
    private String mTimedActionText;

    @BeforeClass
    public static void init() throws Exception {
        // Use hardcoded Clock instead of real system clock.
        SystemClock.inject(FIXED_CLOCK);
    }

    @Before
    public void setUp() throws Exception {
        mAsapFriendlyDate = "ASAP";
        mAsapColor = OPCRMColors.FLAG_RED;
        mAsapAction = new Action().setStatus(Action.Status.ASAP);

        mYesterdayFriendlyDate = "APR 21";
        mYesterdayColor = OPCRMColors.FLAG_RED;
        mYesterdayDate = TODAY.minusDays(1);
        mYesterdayAction = new Action().setDate(mYesterdayDate);

        mTodayFriendlyDate = "TODAY";
        mTodayColor = OPCRMColors.FLAG_ORANGE;
        mTodayDate = TODAY;
        mTodayAction = new Action().setDate(mTodayDate);

        mTomorrowFriendlyDate = "APR 23";
        mTomorrowColor = OPCRMColors.FLAG_GREY_BROWN;
        mTomorrowDate = TODAY.plusDays(1);
        mTomorrowAction = new Action().setDate(mTomorrowDate);

        mWaitingFriendlyDate = "WAITING";
        mWaitingColor = OPCRMColors.FLAG_GREY_BROWN;
        mWaitingAction = new Action().setStatus(Action.Status.WAITING);

        mTimedActionText = "This action has a date and time!";
        mTimedExactTime = Instant.ofEpochSecond(1467360000L);
        mTimedAction = new Action()
                .setText(mTimedActionText)
                .setExactTime(mTimedExactTime)
                .setStatus(Action.Status.DATE_TIME);
    }

    @After
    public void tearDown() throws Exception {

    }

    @AfterClass
    public static void destroy() throws Exception {
        SystemClock.restoreToDefault();
    }

    @Test
    public void testFormatting_FriendlyDateText() throws Exception {
        assertEquals("Formatted friendly Action date does not match",
                mAsapFriendlyDate,
                ActionHelper.formatFriendlyDate(mAsapAction));

        assertEquals("Formatted friendly Action date does not match",
                mYesterdayFriendlyDate,
                ActionHelper.formatFriendlyDate(mYesterdayAction));

        assertEquals("Formatted friendly Action date does not match",
                mTodayFriendlyDate,
                ActionHelper.formatFriendlyDate(mTodayAction));

        assertEquals("Formatted friendly Action date does not match",
                mTomorrowFriendlyDate,
                ActionHelper.formatFriendlyDate(mTomorrowAction));

        assertEquals("Formatted friendly Action date does not match",
                mWaitingFriendlyDate,
                ActionHelper.formatFriendlyDate(mWaitingAction));
    }

    @Test
    public void testFormatting_FlagColor() throws Exception {
        assertEquals("Formatted Action flag color does not match",
                mAsapColor,
                ActionHelper.calculateFlagColor(mAsapAction));

        assertEquals("Formatted Action flag color does not match",
                mYesterdayColor,
                ActionHelper.calculateFlagColor(mYesterdayAction));

        assertEquals("Formatted Action flag color does not match",
                mTodayColor,
                ActionHelper.calculateFlagColor(mTodayAction));

        assertEquals("Formatted Action flag color does not match",
                mTomorrowColor,
                ActionHelper.calculateFlagColor(mTomorrowAction));

        assertEquals("Formatted Action flag color does not match",
                mWaitingColor,
                ActionHelper.calculateFlagColor(mWaitingAction));
    }

    @Test
    public void testFormatting_TimeAndActionText() throws Exception {
        assertEquals("Formatted Action time & text color does not match",
                String.format("08:00am %s", mTimedActionText),
                ActionHelper.formatTimeAndActionText(ZONE_ID_UTC, false, mTimedAction));

        assertEquals("Formatted Action time & text color does not match",
                String.format("08:00 %s", mTimedActionText),
                ActionHelper.formatTimeAndActionText(ZONE_ID_UTC, true, mTimedAction));

        assertEquals("Formatted Action time & text color does not match",
                String.format("04:00am %s", mTimedActionText),
                ActionHelper.formatTimeAndActionText(ZONE_ID_ET, false, mTimedAction));

        assertEquals("Formatted Action time & text color does not match",
                String.format("04:00 %s", mTimedActionText),
                ActionHelper.formatTimeAndActionText(ZONE_ID_ET, true, mTimedAction));
    }
}
