package com.onepagecrm.samples;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.serializers.ActionSerializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 11/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ThreeTenBPDriver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    private static TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    private static TimeZone TIME_ZONE_ET = TimeZone.getTimeZone("America/New_York");

    private static final ZoneId ZONE_ID_UTC = ZoneId.of(TIME_ZONE_UTC.getID());
    private static final ZoneId ZONE_ID_ET = ZoneId.of(TIME_ZONE_ET.getID());

    public static void main(String[] args) throws JSONException {

        // {"id":"5a573ad79007ba6b0a056a30","assignee_id":"559cd1866f6e656707000001","contact_id":"55acc4ee6f6e653fdc000099","text":"Follow up RE project","status":"date_time","done":false,"created_at":"2018-01-11T10:22:15Z","modified_at":"2018-04-11T09:31:52Z","date":"2018-04-11","exact_time":1523462400}

        /*
         {
            "date":"2018-04-11",
            "exact_time":1523462400,
            "created_at":"2018-01-11T10:22:15Z",
            "id":"5a573ad79007ba6b0a056a30",
            "text":"Follow up RE project",
            "contact_id":"55acc4ee6f6e653fdc000099",
            "modified_at":"2018-04-11T09:31:52Z",
            "assignee_id":"559cd1866f6e656707000001",
            "status":"date_time"
          }
         */

        final String actionJson = "{\"date\":\"2018-04-11\"," +
                "\"exact_time\":1523462400," +
                "\"created_at\":\"2018-01-11T10:22:15Z\"," +
                "\"id\":\"5a573ad79007ba6b0a056a30\"," +
                "\"text\":\"Follow up RE project\"," +
                "\"contact_id\":\"55acc4ee6f6e653fdc000099\"," +
                "\"modified_at\":\"2018-04-11T09:31:52Z\"," +
                "\"assignee_id\":\"559cd1866f6e656707000001\"," +
                "\"status\":\"date_time\"}";

        final JSONObject actionObject = new JSONObject(actionJson);

        final Action action = ActionSerializer.fromJsonObject(actionObject);

        LOG.info("Action: " + action);
        LOG.info("Java8: {" + String.format(Locale.ENGLISH,
                "\n\t\"date\":\"%s\"," +
                        "\n\t\"seconds_utc\":%d," +
                        "\n\t\"millis_utc\":%d," +
                        "\n\t\"timestamp_utc\":\"%s\"," +
                        "\n\t\"timestamp_est\":\"%s\"," +
                        "\n\t\"timestamp_est\":\"%s\"," +
                        "\n\t\"formatted_utc\":\"%s\"," +
                        "\n\t\"formatted_est\":\"%s\"" +
                        "\n}",
                action.getJ8Date().toString(),
                action.getJ8ExactTime().toEpochMilli() / 1000,
                action.getJ8ExactTime().toEpochMilli(),
                action.getJ8ExactTime().atZone(ZONE_ID_UTC).format(DateTimeFormatter.ISO_INSTANT),
                action.getJ8ExactTime().atZone(ZONE_ID_ET).toString(),
                action.getJ8ExactTime().atZone(ZONE_ID_ET).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                action.getJ8ExactTime().atZone(ZONE_ID_UTC).format(DateTimeFormatter.ofPattern("hh:mma MMM dd, yyyy")),
                action.getJ8ExactTime().atZone(ZONE_ID_ET).format(DateTimeFormatter.ofPattern("hh:mma MMM dd, yyyy"))
        ));
    }
}
