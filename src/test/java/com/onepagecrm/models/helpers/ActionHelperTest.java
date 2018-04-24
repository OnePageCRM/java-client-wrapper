package com.onepagecrm.models.helpers;

import com.onepagecrm.models.Action;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Cillian Myles on 24/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ActionHelperTest {

    // For this test TODAY is APR 24, 2018 (2018-04-24).
    private static final LocalDate TODAY = LocalDate.of(2018, 4, 24);

    // ASAP
    private Action mAsapAction;
    private String mAsapFormatted;

    // Yesterday / 2018-04-23 / APR 23, 2018
    private Action mYesterdayAction;
    private LocalDate mYesterdayDate;
    private String mYesterdayFormatted;

    // Today / 2018-04-24 / TODAY
    private Action mTodayAction;
    private LocalDate mTodayDate;
    private String mTodayFormatted;

    // Yesterday / 2018-04-25 / APR 25, 2018
    private Action mTomorrowAction;
    private LocalDate mTomorrowDate;
    private String mTomorrowFormatted;

    // ASAP
    private Action mWaitingAction;
    private String mWaitingFormatted;

    @Before
    public void setUp() throws Exception {
        mAsapFormatted = "ASAP";
        mAsapAction = new Action().setStatus(Action.Status.ASAP);

        mYesterdayFormatted = "APR 23";
        mYesterdayDate = LocalDate.of(2018, 4, 23);
        mYesterdayAction = new Action().setJ8Date(mYesterdayDate);

        mTodayFormatted = "TODAY";
        mTodayDate = LocalDate.of(2018, 4, 24);
        mTodayAction = new Action().setJ8Date(mTodayDate);

        mTomorrowFormatted = "APR 25";
        mTomorrowDate = LocalDate.of(2018, 4, 25);
        mTomorrowAction = new Action().setJ8Date(mTomorrowDate);

        mWaitingFormatted = "WAITING";
        mWaitingAction = new Action().setStatus(Action.Status.WAITING);
    }

    @Test
    public void testFormatting_FriendlyDateText() {
        assertEquals("Formatted friendly Action date does not match",
                mAsapFormatted,
                ActionHelper.getFriendlyDate(mAsapAction, TODAY));

        assertEquals("Formatted friendly Action date does not match",
                mYesterdayFormatted,
                ActionHelper.getFriendlyDate(mYesterdayAction, TODAY));

        assertEquals("Formatted friendly Action date does not match",
                mTodayFormatted,
                ActionHelper.getFriendlyDate(mTodayAction, TODAY));

        assertEquals("Formatted friendly Action date does not match",
                mTomorrowFormatted,
                ActionHelper.getFriendlyDate(mTomorrowAction, TODAY));

        assertEquals("Formatted friendly Action date does not match",
                mWaitingFormatted,
                ActionHelper.getFriendlyDate(mWaitingAction, TODAY));
    }
}
