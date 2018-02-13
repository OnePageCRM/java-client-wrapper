package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.APIException;
import com.onepagecrm.models.AppServer;
import com.onepagecrm.net.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 01/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AppServerSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(AppServerSerializer.class.getSimpleName());

    private static AppServer DEFAULT_SINGLE = new AppServer();
    private static List<AppServer> DEFAULT_LIST = new ArrayList<>();

    public static List<AppServer> fromResponse(Response response) throws APIException {
        JSONObject dataObject = (JSONObject) BaseSerializer.fromResponse(response);
        return fromJsonArray(dataObject.optJSONArray(ENDPOINTS_TAG));
    }

    // TODO: delete eventually (serializers refactor AND-686).
    public static List<AppServer> fromString(String body) throws APIException {
        try {
            String dataString = (String) BaseSerializer.fromString(body);
            JSONObject dataObject = new JSONObject(dataString);
            return fromJsonArray(dataObject.optJSONArray(ENDPOINTS_TAG));

        } catch (JSONException e) {
            LOG.severe("Error parsing server/endpoint data.");
            LOG.severe(e.toString());
            e.printStackTrace();
            return DEFAULT_LIST;
        }
    }

    public static AppServer fromJsonObject(JSONObject serverObject) {
        if (serverObject == null) return DEFAULT_SINGLE;
        return new AppServer()
                .setId(serverObject.optString(ID_TAG))
                .setName(serverObject.optString(NAME_TAG))
                .setMetadataUrl(serverObject.optString(METADATA_URL_TAG));
    }

    public static List<AppServer> fromJsonArray(JSONArray serverArray) {
        List<AppServer> servers = new ArrayList<>();
        if (serverArray == null) return servers;
        for (int i = 0; i < serverArray.length(); i++) {
            JSONObject serverObject = serverArray.optJSONObject(i);
            servers.add(fromJsonObject(serverObject));
        }
        return servers;
    }

    public static JSONObject toJsonObject(AppServer server) {
        JSONObject serverObject = new JSONObject();
        if (server == null) return serverObject;
        addJsonStringValue(server.getId(), serverObject, ID_TAG);
        addJsonStringValue(server.getName(), serverObject, NAME_TAG);
        addJsonStringValue(server.getMetadataUrl(), serverObject, METADATA_URL_TAG);
        return serverObject;
    }

    public static String toJsonString(AppServer server) {
        return toJsonObject(server).toString();
    }

    public static JSONArray toJsonArray(List<AppServer> servers) {
        JSONArray serversArray = new JSONArray();
        if (servers == null) return serversArray;
        for (AppServer server : servers) {
            serversArray.put(toJsonObject(server));
        }
        return serversArray;
    }

    public static String toJsonString(List<AppServer> servers) {
        return toJsonArray(servers).toString();
    }
}
