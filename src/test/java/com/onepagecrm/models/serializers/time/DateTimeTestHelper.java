package com.onepagecrm.models.serializers.time;

import com.onepagecrm.models.serializers.DateTimeSerializer;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.TimeZone;

/**
 * Created by Cillian Myles on 17/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class DateTimeTestHelper {

    public static final String TZ_ZONE_ID_UTC = "Z";
    public static final String TZ_ZONE_ID_ET = "America/New_York";

    public static final ZoneId ZONE_ID_UTC = ZoneId.of(TZ_ZONE_ID_UTC);
    public static final ZoneId ZONE_ID_ET = ZoneId.of(TZ_ZONE_ID_ET);

    public static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone(TZ_ZONE_ID_UTC);
    public static final TimeZone TIME_ZONE_ET = TimeZone.getTimeZone(TZ_ZONE_ID_ET);

    public static final DateTimeFormatter FORMAT_LOCAL_DATE = DateTimeSerializer.FORMATTER_DATE;
    public static final DateTimeFormatter FORMAT_INSTANT = DateTimeSerializer.FORMATTER_DATE_TIME;
    public static final DateTimeFormatter FORMAT_ZONED_DATE_TIME = DateTimeSerializer.FORMATTER_FRIENDLY_TIME_DATE_YEAR;
}
