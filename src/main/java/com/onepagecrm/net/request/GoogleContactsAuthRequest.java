package com.onepagecrm.net.request;

import com.onepagecrm.models.serializers.BaseSerializer;

import org.json.JSONObject;

import static com.onepagecrm.models.serializers.BaseSerializer.CODE_TAG;
import static com.onepagecrm.models.serializers.BaseSerializer.ENDPOINT_TAG;
import static com.onepagecrm.net.ApiResource.GOOGLE_CONTACTS_AUTH_ENDPOINT;
import static com.onepagecrm.net.ApiResource.GOOGLE_LOGIN_ENDPOINT;
import static com.onepagecrm.net.ApiResource.GOOGLE_SIGNUP_ENDPOINT;

/**
 * Created by Anton S. on 3/07/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class GoogleContactsAuthRequest extends Request {

    public GoogleContactsAuthRequest(String oauth2Code) {
        init(oauth2Code, null);
    }

    public GoogleContactsAuthRequest(String oauth2Code, String serverId) {
        init(oauth2Code, serverId);
    }

    private void init(String oauth2Code, String serverId) {
        setType();
        setEndpointUrl(GOOGLE_CONTACTS_AUTH_ENDPOINT);

        JSONObject params = new JSONObject();
        BaseSerializer.addJsonStringValue(oauth2Code, params, CODE_TAG);
        this.requestBody = params.toString();
    }

    @Override
    public void setType() {
        this.type = Type.POST;
    }
}
