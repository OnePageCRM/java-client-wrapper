package com.onepagecrm.models.helpers;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.internal.OPCRMColors;
import com.onepagecrm.models.internal.SystemClock;
import com.onepagecrm.models.serializers.time.DateTimeTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Clock;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import static org.junit.Assert.assertEquals;

/**
 * Created by Cillian Myles on 24/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings("FieldCanBeLocal")
public class ActionHelperTest {

    // For this test TODAY is APR 22, 2018 (2018-04-22).
    private static final LocalDate TODAY = LocalDate.of(2018, 4, 22);
    private static final Instant NOW = Instant.parse("2018-04-22T09:00:00Z");
    private static final ZoneId ZONE_ID_UTC = DateTimeTestHelper.ZONE_ID_UTC;
    private static final Clock CLOCK = Clock.fixed(NOW, ZONE_ID_UTC);

    // ASAP
    private Action mAsapAction;
    private String mAsapFormatted;
    private int mAsapColor;

    // Yesterday / 2018-04-22 / APR 21, 2018
    private Action mYesterdayAction;
    private LocalDate mYesterdayDate;
    private String mYesterdayFormatted;
    private int mYesterdayColor;

    // Today / 2018-04-22 / TODAY
    private Action mTodayAction;
    private LocalDate mTodayDate;
    private String mTodayFormatted;
    private int mTodayColor;

    // Yesterday / 2018-04-23 / APR 25, 2018
    private Action mTomorrowAction;
    private LocalDate mTomorrowDate;
    private String mTomorrowFormatted;
    private int mTomorrowColor;

    // ASAP
    private Action mWaitingAction;
    private String mWaitingFormatted;
    private int mWaitingColor;

    @Before
    public void setUp() throws Exception {
        // Use hardcoded CLOCK instead of real system clock.
        SystemClock.inject(CLOCK);

        mAsapFormatted = "ASAP";
        mAsapColor = OPCRMColors.FLAG_RED;
        mAsapAction = new Action().setStatus(Action.Status.ASAP);

        mYesterdayFormatted = "APR 21";
        mYesterdayColor = OPCRMColors.FLAG_RED;
        mYesterdayDate = TODAY.minusDays(1);
        mYesterdayAction = new Action().setJ8Date(mYesterdayDate);

        mTodayFormatted = "TODAY";
        mTodayColor = OPCRMColors.FLAG_ORANGE;
        mTodayDate = TODAY;
        mTodayAction = new Action().setJ8Date(mTodayDate);

        mTomorrowFormatted = "APR 23";
        mTomorrowColor = OPCRMColors.FLAG_GREY_BROWN;
        mTomorrowDate = TODAY.plusDays(1);
        mTomorrowAction = new Action().setJ8Date(mTomorrowDate);

        mWaitingFormatted = "WAITING";
        mWaitingColor = OPCRMColors.FLAG_GREY_BROWN;
        mWaitingAction = new Action().setStatus(Action.Status.WAITING);
    }

    @After
    public void tearDown() throws Exception {
        SystemClock.restoreToDefault();
    }

    @Test
    public void testFormatting_FriendlyDateText() throws Exception {
        assertEquals("Formatted friendly Action date does not match",
                mAsapFormatted,
                ActionHelper.getFriendlyDate(mAsapAction/*, TODAY*/));

        assertEquals("Formatted friendly Action date does not match",
                mYesterdayFormatted,
                ActionHelper.getFriendlyDate(mYesterdayAction/*, TODAY*/));

        assertEquals("Formatted friendly Action date does not match",
                mTodayFormatted,
                ActionHelper.getFriendlyDate(mTodayAction/*, TODAY*/));

        assertEquals("Formatted friendly Action date does not match",
                mTomorrowFormatted,
                ActionHelper.getFriendlyDate(mTomorrowAction/*, TODAY*/));

        assertEquals("Formatted friendly Action date does not match",
                mWaitingFormatted,
                ActionHelper.getFriendlyDate(mWaitingAction/*, TODAY*/));
    }

    @Test
    public void testFormatting_FlagColor() throws Exception {
        assertEquals("Formatted Action flag color does not match",
                mAsapColor,
                ActionHelper.getFlagColor(mAsapAction/*, TODAY*/));

        assertEquals("Formatted Action flag color does not match",
                mYesterdayColor,
                ActionHelper.getFlagColor(mYesterdayAction/*, TODAY*/));

        assertEquals("Formatted Action flag color does not match",
                mTodayColor,
                ActionHelper.getFlagColor(mTodayAction/*, TODAY*/));

        assertEquals("Formatted Action flag color does not match",
                mTomorrowColor,
                ActionHelper.getFlagColor(mTomorrowAction/*, TODAY*/));

        assertEquals("Formatted Action flag color does not match",
                mWaitingColor,
                ActionHelper.getFlagColor(mWaitingAction/*, TODAY*/));
    }
}
