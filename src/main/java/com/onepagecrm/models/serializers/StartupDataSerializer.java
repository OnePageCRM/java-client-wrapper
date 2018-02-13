package com.onepagecrm.models.serializers;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.APIException;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.User;
import com.onepagecrm.net.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 03/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class StartupDataSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(StartupDataSerializer.class.getSimpleName());

    private final static StartupData DEFAULT = new StartupData(null, null);

    public static StartupData fromResponse(Response response) throws APIException {
        JSONObject dataObject = (JSONObject) BaseSerializer.fromResponse(response);
        return fromJsonObject(dataObject);
    }

    // TODO: delete eventually (serializers refactor AND-686).
    public static StartupData fromString(String response) throws APIException {
        try {
            BaseSerializer.fromString(response); // Needed to throw errors.
            JSONObject responseObject = new JSONObject(response);
            return fromJsonObject(responseObject);

        } catch (JSONException e) {
            LOG.severe("Error parsing startup data JSON.");
            LOG.severe(e.toString());
            e.printStackTrace();
            return DEFAULT;
        }
    }

    public static StartupData fromJsonObject(JSONObject responseObject) throws APIException {
        if (responseObject == null) {
            return DEFAULT;
        }

        User user = LoginSerializer.fromString(responseObject.toString());
        ContactList stream = null;
        ContactList contacts = null;
        DealList deals = null;
        boolean fullResponse = false;

        JSONObject dataObject = responseObject.optJSONObject(DATA_TAG);
        if (dataObject == null) dataObject = responseObject;

        if (dataObject.has(ACTION_STREAM_DATA_TAG)) {
            fullResponse = true;
            stream = ContactListSerializer.fromJsonObject(dataObject.optJSONObject(ACTION_STREAM_DATA_TAG));
        }
        if (dataObject.has(CONTACT_DATA_TAG)) {
            fullResponse = true;
            contacts = ContactListSerializer.fromJsonObject(dataObject.optJSONObject(CONTACT_DATA_TAG));
        }
        if (dataObject.has(DEAL_DATA_TAG)) {
            fullResponse = true;
            deals = DealListSerializer.fromJsonObject(dataObject.optJSONObject(DEAL_DATA_TAG));
        }

        final String endpointUrl = OnePageCRM.getEndpointUrl();

        return !fullResponse ?
                new StartupData(endpointUrl, user) :
                new StartupData(endpointUrl, user, stream, contacts, deals);
    }
}
