package com.onepagecrm.net.request;

import com.onepagecrm.models.serializers.BaseSerializer;
import org.json.JSONObject;

import static com.onepagecrm.models.serializers.BaseSerializer.CODE_TAG;
import static com.onepagecrm.models.serializers.BaseSerializer.ENDPOINT_TAG;
import static com.onepagecrm.net.ApiResource.GOOGLE_LOGIN_ENDPOINT;
import static com.onepagecrm.net.ApiResource.GOOGLE_SIGNUP_ENDPOINT;

/**
 * Created by Cillian Myles on 31/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class GoogleAuthRequest extends Request {

    public GoogleAuthRequest(String oauth2Code) {
        init(true, oauth2Code, null);
    }

    public GoogleAuthRequest(String oauth2Code, String serverId) {
        init(false, oauth2Code, serverId);
    }

    private void init(boolean login, String oauth2Code, String serverId) {
        setType();
        setEndpointUrl(login ? GOOGLE_LOGIN_ENDPOINT : GOOGLE_SIGNUP_ENDPOINT);

        JSONObject params = new JSONObject();
        BaseSerializer.addJsonStringValue(oauth2Code, params, CODE_TAG);
        if (!login) {
            BaseSerializer.addJsonStringValue(serverId, params, ENDPOINT_TAG);
        }
        this.requestBody = params.toString();
    }

    @Override
    public void setType() {
        this.type = Request.Type.POST;
    }
}
