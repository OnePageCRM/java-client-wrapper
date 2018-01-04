package com.onepagecrm.net.request;

import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.BaseSerializer;
import com.onepagecrm.models.serializers.LoginDataSerializer;

public class LoginRequest extends Request {

    public LoginRequest(String username, String password, boolean fullResponse) {
        init(new LoginData(username, password, fullResponse));
    }

    public LoginRequest(String username, String password) {
        init(new LoginData(username, password, false));
    }

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
