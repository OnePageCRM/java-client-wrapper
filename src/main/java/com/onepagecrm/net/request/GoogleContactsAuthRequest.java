package com.onepagecrm.net.request;

import com.onepagecrm.models.serializers.BaseSerializer;
import org.json.JSONObject;

import static com.onepagecrm.models.serializers.BaseSerializer.CODE_TAG;
import static com.onepagecrm.net.ApiResource.GOOGLE_CONTACTS_AUTH_ENDPOINT;

/**
 * Created by Anton S. on 3/07/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class GoogleContactsAuthRequest extends PostRequest {

    public GoogleContactsAuthRequest(String oauth2Code) {
        super(GOOGLE_CONTACTS_AUTH_ENDPOINT, null);
        init(oauth2Code);
    }

    private void init(String oauth2Code) {
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
