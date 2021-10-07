package com.onepagecrm.net.request;

import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.BaseSerializer;
import com.onepagecrm.models.serializers.LoginDataSerializer;

/**
 * Created by Cillian Myles on 31/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings("unused")
public class LoginRequest extends Request {

    public LoginRequest(LoginData loginData) {
        init(loginData);
    }

    private void init(LoginData loginData) {
        setType();
        setEndpointUrl(BaseSerializer.LOGIN_TAG);
        this.requestBody = LoginDataSerializer.toJsonString(loginData);
    }

    @Override
    public void setType() {
        this.type = Type.POST;
    }
}
