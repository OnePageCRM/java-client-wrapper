package com.onepagecrm.models.serializers.time;

import com.onepagecrm.models.serializers.InstantSerializer;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

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
    public void testZoneId_DefaultsMatch() throws Exception {
        ZoneId zoneId = null;
        Exception exception = null;
        try {
            zoneId = InstantSerializer.getInstance().defaultZoneId();
        } catch (Exception e) {
            exception = e;
        }
        assertNull("No exception should be thrown", exception);
        assertEquals("Default ZoneId does not match", ZONE_ID_UTC, zoneId);
    }

    @Test
    public void testParsing_StringToInstant_WithZoneIdAndFormat() throws Exception {

    }

    @Test
    public void testParsing_StringToInstant_WithZoneId() throws Exception {

    }

    @Test
    public void testParsing_StringToInstant_WithFormat() throws Exception {

    }

    @Test
    public void testParsing_StringToInstant_Default() throws Exception {
        assertEquals("Parsed Instant does not match",
                timeNo1Instant,
                InstantSerializer.getInstance().parse(timeNo1Formatted));

        assertEquals("Parsed Instant does not match",
                timeNo2Instant,
                InstantSerializer.getInstance().parse(timeNo2Formatted));

        assertNull("Parsed Instant should be null",
                InstantSerializer.getInstance().parse(null));

        assertNull("Parsed Instant should be null",
                InstantSerializer.getInstance().parse(""));
    }

    @Test
    public void testFormatting_InstantToString_WithZoneIdAndFormat() throws Exception {

    }

    @Test
    public void testFormatting_InstantToString_WithZone() throws Exception {

    }

    @Test
    public void testFormatting_InstantToString_WithFormat() throws Exception {

    }

    @Test
    public void testFormatting_InstantToString_Default() throws Exception {
        assertEquals("Formatted Instant does not match",
                timeNo1Formatted,
                InstantSerializer.getInstance().format(timeNo1Instant));

        assertEquals("Formatted Instant does not match",
                timeNo2Formatted,
                InstantSerializer.getInstance().format(timeNo2Instant));

        assertNull("Formatted Instant should be null",
                InstantSerializer.getInstance().format(null));
    }

    @Test
    public void testConversion_SecondsToInstant() throws Exception {
        assertEquals("Generated Instant does not match",
                timeNo1Instant,
                InstantSerializer.getInstance().ofSeconds(timeNo1Secs));

        assertEquals("Generated Instant does not match",
                timeNo2Instant,
                InstantSerializer.getInstance().ofSeconds(timeNo2Secs));

        assertNull("Generated Instant should be null",
                InstantSerializer.getInstance().ofSeconds(null));
    }

    @Test
    public void testConversion_MillisToInstant() throws Exception {
        assertEquals("Generated Instant does not match",
                timeNo1Instant,
                InstantSerializer.getInstance().ofMillis(timeNo1Millis));

        assertEquals("Generated Instant does not match",
                timeNo2Instant,
                InstantSerializer.getInstance().ofMillis(timeNo2MMillis));

        assertNull("Generated Instant should be null",
                InstantSerializer.getInstance().ofMillis(null));
    }

    @Test
    public void testConversion_InstantToSeconds() throws Exception {
        assertEquals("Converted Instant does not match",
                timeNo1Secs,
                InstantSerializer.getInstance().seconds(timeNo1Instant));

        assertEquals("Converted Instant does not match",
                timeNo2Secs,
                InstantSerializer.getInstance().seconds(timeNo2Instant));

        assertNull("Converted Instant should be null",
                InstantSerializer.getInstance().seconds(null));
    }

    @Test
    public void testConversion_InstantToMillis() throws Exception {
        assertEquals("Converted Instant does not match",
                timeNo1Millis,
                InstantSerializer.getInstance().millis(timeNo1Instant));

        assertEquals("Converted Instant does not match",
                timeNo2MMillis,
                InstantSerializer.getInstance().millis(timeNo2Instant));

        assertNull("Converted Instant should be null",
                InstantSerializer.getInstance().millis(null));
    }
}
