package com.onepagecrm.models.serializers;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Cillian Myles on 19/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class LocalDateSerializerTest extends DateTimeTestHelper {

    // Thu, 01 Jan 1970
    private LocalDate dateNo1LocalDate;
    private String dateNo1Formatted;

    // Fri, 01 Jul 2016
    private LocalDate dateNo2LocalDate;
    private String dateNo2Formatted;

    @Before
    public void setUp() throws Exception {
        dateNo1LocalDate = LocalDate.of(1970, 1, 1);
        dateNo1Formatted = "1970-01-01";

        dateNo2LocalDate = LocalDate.of(2016, 7, 1);
        dateNo2Formatted = "2016-07-01";
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
    public void testParsing_StringToLocalDate_WithZoneIdAndFormat() throws Exception {
        LocalDate date = null;
        Exception exception = null;
        try {
            date = LocalDateSerializer.getInstance().parse(dateNo1Formatted, ZONE_ID_UTC, FORMAT_LOCAL_DATE);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", date);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testParsing_StringToLocalDate_WithZoneId() throws Exception {
        LocalDate date = null;
        Exception exception = null;
        try {
            date = LocalDateSerializer.getInstance().parse(dateNo1Formatted, ZONE_ID_UTC);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", date);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testParsing_StringToLocalDate_WithFormat() throws Exception {
        assertEquals("Parsed LocalDate does not match",
                dateNo1LocalDate,
                LocalDateSerializer.getInstance().parse(dateNo1Formatted, FORMAT_LOCAL_DATE));

        assertEquals("Parsed LocalDate does not match",
                dateNo2LocalDate,
                LocalDateSerializer.getInstance().parse(dateNo2Formatted, FORMAT_LOCAL_DATE));

        assertNull("Parsed LocalDate should be null",
                LocalDateSerializer.getInstance().parse(null, FORMAT_LOCAL_DATE));

        assertNull("Parsed LocalDate should be null",
                LocalDateSerializer.getInstance().parse("", FORMAT_LOCAL_DATE));
    }

    @Test
    public void testParsing_StringToLocalDate_Default() throws Exception {
        assertEquals("Parsed LocalDate does not match",
                dateNo1LocalDate,
                LocalDateSerializer.getInstance().parse(dateNo1Formatted));

        assertEquals("Parsed LocalDate does not match",
                dateNo2LocalDate,
                LocalDateSerializer.getInstance().parse(dateNo2Formatted));

        assertNull("Parsed LocalDate should be null",
                LocalDateSerializer.getInstance().parse(null));

        assertNull("Parsed LocalDate should be null",
                LocalDateSerializer.getInstance().parse(""));
    }

    @Test
    public void testFormatting_LocalDateToString_WithZoneIdAndFormat() throws Exception {
        String formatted = null;
        Exception exception = null;
        try {
            formatted = LocalDateSerializer.getInstance().format(dateNo1LocalDate, ZONE_ID_UTC, FORMAT_LOCAL_DATE);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", formatted);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testFormatting_LocalDateToString_WithZoneId() throws Exception {
        String formatted = null;
        Exception exception = null;
        try {
            formatted = LocalDateSerializer.getInstance().format(dateNo1LocalDate, ZONE_ID_UTC);
        } catch (Exception e) {
            exception = e;
        }
        assertNull("Expected exception to be thrown", formatted);
        assertNotNull("Expected exception to be thrown", exception);
    }

    @Test
    public void testFormatting_LocalDateToString_WithFormat() throws Exception {
        assertEquals("Formatted LocalDate does not match",
                dateNo1Formatted,
                LocalDateSerializer.getInstance().format(dateNo1LocalDate, FORMAT_LOCAL_DATE));

        assertEquals("Formatted LocalDate does not match",
                dateNo2Formatted,
                LocalDateSerializer.getInstance().format(dateNo2LocalDate, FORMAT_LOCAL_DATE));

        assertNull("Formatted LocalDate should be null",
                LocalDateSerializer.getInstance().format(null, FORMAT_LOCAL_DATE));
    }

    @Test
    public void testFormatting_LocalDateToString_Default() throws Exception {
        assertEquals("Formatted LocalDate does not match",
                dateNo1Formatted,
                LocalDateSerializer.getInstance().format(dateNo1LocalDate));

        assertEquals("Formatted LocalDate does not match",
                dateNo2Formatted,
                LocalDateSerializer.getInstance().format(dateNo2LocalDate));

        assertNull("Formatted LocalDate should be null",
                LocalDateSerializer.getInstance().format(null));
    }
}
