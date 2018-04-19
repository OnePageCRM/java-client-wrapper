package com.onepagecrm.models.serializers;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class InstantSerializerTest extends DateTimeTestHelper {

    // Thu, 01 Jan 1970 00:00:00 UTC = 0
    private Long timeNo1Secs;
    private Long timeNo1Millis;
    private Instant timeNo1Instant;
    private String timeNo1Formatted;

    // Fri, 01 Jul 2016 08:00:00 UTC = 1467360000
    private Long timeNo2Secs;
    private Long timeNo2MMillis;
    private Instant timeNo2Instant;
    private String timeNo2Formatted;

    @Before
    public void setUp() throws Exception {
        timeNo1Secs = 0L;
        timeNo1Millis = timeNo1Secs * 1000;
        timeNo1Instant = Instant.ofEpochSecond(timeNo1Secs);
        timeNo1Formatted = "1970-01-01T00:00:00Z";

        timeNo2Secs = 1467360000L;
        timeNo2MMillis = timeNo2Secs * 1000;
        timeNo2Instant = Instant.ofEpochSecond(timeNo2Secs);
        timeNo2Formatted = "2016-07-01T08:00:00Z";
    }

    @Test
    public void testParsing_StringToInstant() throws Exception {
        assertEquals(timeNo1Instant, InstantSerializer.getInstance().parse(timeNo1Formatted));
        assertEquals(timeNo2Instant, InstantSerializer.getInstance().parse(timeNo2Formatted));
        assertNull(InstantSerializer.getInstance().parse(null));
        assertNull(InstantSerializer.getInstance().parse(""));
    }

    @Test
    public void testFormatting_InstantToString() throws Exception {
        assertEquals(timeNo1Formatted, InstantSerializer.getInstance().format(timeNo1Instant));
        assertEquals(timeNo2Formatted, InstantSerializer.getInstance().format(timeNo2Instant));
        assertNull(InstantSerializer.getInstance().format(null));
    }

    // TODO: test formatting with time zone data

    @Test
    public void testConversion_SecondsToInstant() throws Exception {
        assertEquals(timeNo1Instant, InstantSerializer.getInstance().ofSeconds(timeNo1Secs));
        assertEquals(timeNo2Instant, InstantSerializer.getInstance().ofSeconds(timeNo2Secs));
        assertNull(InstantSerializer.getInstance().ofSeconds(null));
    }

    @Test
    public void testConversion_MillisToInstant() throws Exception {
        assertEquals(timeNo1Instant, InstantSerializer.getInstance().ofMillis(timeNo1Millis));
        assertEquals(timeNo2Instant, InstantSerializer.getInstance().ofMillis(timeNo2MMillis));
        assertNull(InstantSerializer.getInstance().ofMillis(null));
    }

    @Test
    public void testConversion_InstantToSeconds() throws Exception {
        assertEquals(timeNo1Secs, InstantSerializer.getInstance().seconds(timeNo1Instant));
        assertEquals(timeNo2Secs, InstantSerializer.getInstance().seconds(timeNo2Instant));
        assertNull(InstantSerializer.getInstance().seconds(null));
    }

    @Test
    public void testConversion_InstantToMillis() throws Exception {
        assertEquals(timeNo1Millis, InstantSerializer.getInstance().millis(timeNo1Instant));
        assertEquals(timeNo2MMillis, InstantSerializer.getInstance().millis(timeNo2Instant));
        assertNull(InstantSerializer.getInstance().millis(null));
    }
}
