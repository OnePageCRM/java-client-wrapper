package com.onepagecrm.models.serializers;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import static org.junit.Assert.assertEquals;
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
    public void testParsing_StringToLocalDate() throws Exception {
        assertEquals(dateNo1LocalDate, LocalDateSerializer.getInstance().parse(dateNo1Formatted));
        assertEquals(dateNo2LocalDate, LocalDateSerializer.getInstance().parse(dateNo2Formatted));
        assertNull(LocalDateSerializer.getInstance().parse(null));
        assertNull(LocalDateSerializer.getInstance().parse(""));
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
    public void testFormatting_LocalDateToString() throws Exception {
        assertEquals(dateNo1Formatted, LocalDateSerializer.getInstance().format(dateNo1LocalDate));
        assertEquals(dateNo2Formatted, LocalDateSerializer.getInstance().format(dateNo2LocalDate));
        assertNull(LocalDateSerializer.getInstance().format(null));
    }

    // TODO: add error cases for parsing
}
