package com.onepagecrm.models.serializers;

import com.onepagecrm.BaseTest;
import com.onepagecrm.TestHelper;

import java.util.Date;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 20/07/2016.
 */
public class DateSerializerTest extends BaseTest {

    // Thu, 01 Jan 1970 00:00:00 UTC = 0
    private final long theBeginningTs = 0;
    private Date theBeginning;

    // Fri, 01 Jul 2016 08:00:00 UTC = 1467360000
    private final long julyFirst2016Morning8Ts = 1467360000;
    private Date julyFirst2016Morning8;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        theBeginning = TestHelper.dateTheBeginning();
        julyFirst2016Morning8 = TestHelper.dateJuly1st2016Morning8();
    }

    public void testTimestampParsing() throws Exception {
        assertEquals(theBeginning, DateSerializer.fromTimestamp(String.valueOf(theBeginningTs)));
        assertEquals(julyFirst2016Morning8, DateSerializer.fromTimestamp(String.valueOf(julyFirst2016Morning8Ts)));
    }

    public void testTimestampCreation() throws Exception {
        assertEquals(theBeginningTs, (long) DateSerializer.toTimestamp(theBeginning));
        assertEquals(julyFirst2016Morning8Ts, (long) DateSerializer.toTimestamp(julyFirst2016Morning8));
    }
}
