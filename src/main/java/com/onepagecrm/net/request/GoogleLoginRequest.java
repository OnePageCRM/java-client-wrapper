package com.onepagecrm.net.request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cillian Myles on 31/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 *
 * @see GoogleAuthRequest
 */
@Deprecated
public class GoogleLoginRequest extends Request {

    @Deprecated
    public GoogleLoginRequest(String oauth2Code, boolean login) {
        setType();
        setEndpointUrl(login ? "google_plus" : "google_plus/signup");
        JSONObject params = new JSONObject();
        try {
            params.put("code", oauth2Code);
            this.requestBody = params.toString();
        } catch (JSONException e) {
            LOG.severe("Error creating JSON");
            LOG.severe(e.toString());
        }
    }

    @Override
    public void setType() {
        this.type = Type.POST;
    }
}
