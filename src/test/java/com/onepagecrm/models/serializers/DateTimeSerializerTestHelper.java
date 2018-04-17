package com.onepagecrm.models.serializers;

import org.threeten.bp.ZoneId;

import java.util.TimeZone;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public abstract class DateTimeSerializerTestHelper {

    protected static final String TZ_ZONE_ID_UTC = "UTC";
    protected static final String TZ_ZONE_ID_ET = "America/New_York";

    protected static final ZoneId ZONE_ID_UTC = ZoneId.of(TZ_ZONE_ID_UTC);
    protected static final ZoneId ZONE_ID_ET = ZoneId.of(TZ_ZONE_ID_ET);

    protected static TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone(TZ_ZONE_ID_UTC);
    protected static TimeZone TIME_ZONE_ET = TimeZone.getTimeZone(TZ_ZONE_ID_ET);
}
