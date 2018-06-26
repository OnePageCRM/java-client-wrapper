package com.onepagecrm.models.serializers.time;

import com.onepagecrm.models.serializers.ZonedDateTimeSerializer;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ZonedDateTimeSerializerTest extends DateTimeTestHelper {

    // Fri, 01 Jul 2016 08:00:00 UTC = 1467360000
    private ZonedDateTime timeNo1DateTimeUTC;
    private ZonedDateTime timeNo1DateTimeUTCPlus1;
    private ZonedDateTime timeNo1DateTimeUTCMinus4;
    private String timeNo1FormattedUTC;
    private String timeNo1FormattedUTCPlus1;
    private String timeNo1FormattedUTCMinus4;

    // Wed, 11 Apr 2018 16:00:00 UTC = 1523462400
    private ZonedDateTime timeNo2DateTimeUTC;
    private ZonedDateTime timeNo2DateTimeUTCPlus1;
    private ZonedDateTime timeNo2DateTimeUTCMinus4;
    private String timeNo2FormattedUTC;
    private String timeNo2FormattedUTCPlus1;
    private String timeNo2FormattedUTCMinus4;

    @Before
    public void setUp() throws Exception {
        timeNo1DateTimeUTC = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1467360000L), ZONE_ID_UTC);
        timeNo1DateTimeUTCPlus1 = timeNo1DateTimeUTC.withZoneSameInstant(ZONE_ID_EU);
        timeNo1DateTimeUTCMinus4 = timeNo1DateTimeUTC.withZoneSameInstant(ZONE_ID_ET);
        timeNo1FormattedUTC = "08:00 Jul 01, 2016";
        timeNo1FormattedUTCPlus1 = "09:00 Jul 01, 2016";
        timeNo1FormattedUTCMinus4 = "04:00 Jul 01, 2016";

        timeNo2DateTimeUTC = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1523462400L), ZONE_ID_UTC);
        timeNo2DateTimeUTCPlus1 = timeNo2DateTimeUTC.withZoneSameInstant(ZONE_ID_EU);
        timeNo2DateTimeUTCMinus4 = timeNo2DateTimeUTC.withZoneSameInstant(ZONE_ID_ET);
        timeNo2FormattedUTC = "16:00 Apr 11, 2018";
        timeNo2FormattedUTCPlus1 = "17:00 Apr 11, 2018";
        timeNo2FormattedUTCMinus4 = "12:00 Apr 11, 2018";
    }

    @Test
    public void testZoneId_DefaultsMatch() throws Exception {
        ZoneId zoneId = null;
        Exception exception = null;
        try {
            zoneId = ZonedDateTimeSerializer.getInstance().defaultZoneId();
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Default ZoneId does not match", zoneId);
        assertNotNull("Exception should be thrown", exception);
    }

    @Test
    public void testParsing_StringToZdt_WithZoneIdAndFormat() throws Exception {
        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTC,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTC, ZONE_ID_UTC, FORMAT_ZONED_DATE_TIME));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTCPlus1,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTCPlus1, ZONE_ID_EU, FORMAT_ZONED_DATE_TIME));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTCMinus4,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTCMinus4, ZONE_ID_ET, FORMAT_ZONED_DATE_TIME));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTC,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTC, ZONE_ID_UTC, FORMAT_ZONED_DATE_TIME));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTCPlus1,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTCPlus1, ZONE_ID_EU, FORMAT_ZONED_DATE_TIME));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTCMinus4,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTCMinus4, ZONE_ID_ET, FORMAT_ZONED_DATE_TIME));

        assertNull("Parsed ZonedDateTime should be null",
                ZonedDateTimeSerializer.getInstance().parse(null, ZONE_ID_UTC, FORMAT_ZONED_DATE_TIME));
    }

    @Test
    public void testParsing_StringToZdt_WithZoneId() throws Exception {
        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTC,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTC, ZONE_ID_UTC));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTCPlus1,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTCPlus1, ZONE_ID_EU));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo1DateTimeUTCMinus4,
                ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTCMinus4, ZONE_ID_ET));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTC,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTC, ZONE_ID_UTC));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTCPlus1,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTCPlus1, ZONE_ID_EU));

        assertEquals("Parsed ZonedDateTime does not match",
                timeNo2DateTimeUTCMinus4,
                ZonedDateTimeSerializer.getInstance().parse(timeNo2FormattedUTCMinus4, ZONE_ID_ET));

        assertNull("Parsed ZonedDateTime should be null",
                ZonedDateTimeSerializer.getInstance().parse(null, ZONE_ID_UTC));
    }

    @Test
    public void testParsing_StringToZdt_WithFormat() throws Exception {
        ZonedDateTime zdt = null;
        Exception exception = null;
        try {
            zdt = ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTC, FORMAT_ZONED_DATE_TIME);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", zdt);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testParsing_StringToZdt_Default() throws Exception {
        ZonedDateTime zdt = null;
        Exception exception = null;
        try {
            zdt = ZonedDateTimeSerializer.getInstance().parse(timeNo1FormattedUTC);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", zdt);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testFormatting_ZdtToString_WithZoneIdAndFormat() throws Exception {
        String formatted = null;
        Exception exception = null;
        try {
            formatted = ZonedDateTimeSerializer.getInstance()
                    .format(timeNo1DateTimeUTC, ZONE_ID_UTC, FORMAT_ZONED_DATE_TIME);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", formatted);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testFormatting_ZdtToString_WithZoneId() throws Exception {
        String formatted = null;
        Exception exception = null;
        try {
            formatted = ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTC, ZONE_ID_UTC);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", formatted);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testFormatting_ZdtToString_WithFormat() throws Exception {
        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTC,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTC, FORMAT_ZONED_DATE_TIME));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTCPlus1,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTCPlus1, FORMAT_ZONED_DATE_TIME));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTCMinus4,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTCMinus4, FORMAT_ZONED_DATE_TIME));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTC,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTC, FORMAT_ZONED_DATE_TIME));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTCMinus4,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTCMinus4, FORMAT_ZONED_DATE_TIME));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTCMinus4,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTCMinus4, FORMAT_ZONED_DATE_TIME));

        assertNull("Formatted ZonedDateTime should be null",
                ZonedDateTimeSerializer.getInstance().format(null, FORMAT_ZONED_DATE_TIME));
    }

    @Test
    public void testFormatting_ZdtToString_Default() throws Exception {
        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTC,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTC));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTCPlus1,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTCPlus1));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo1FormattedUTCMinus4,
                ZonedDateTimeSerializer.getInstance().format(timeNo1DateTimeUTCMinus4));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTC,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTC));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTCPlus1,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTCPlus1));

        assertEquals("Formatted ZonedDateTime does not match",
                timeNo2FormattedUTCMinus4,
                ZonedDateTimeSerializer.getInstance().format(timeNo2DateTimeUTCMinus4));

        assertNull("Formatted ZonedDateTime should be null",
                ZonedDateTimeSerializer.getInstance().format(null));
    }
}
