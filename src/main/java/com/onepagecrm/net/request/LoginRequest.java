package com.onepagecrm.net.request;

import com.onepagecrm.models.serializers.LoginSerializer;

import static com.onepagecrm.net.ApiResource.LOGIN_ENDPOINT;
import static com.onepagecrm.net.ApiResource.QUERY_FULL_RESPONSE;

@SuppressWarnings("WeakerAccess")
public class LoginRequest extends Request {

    public LoginRequest(String username, String password) {
        this(username, password, false);
    }

    public LoginRequest(String username, String password, boolean full) {
        this(username, password, full ? "?" + QUERY_FULL_RESPONSE : null);
    }

    public LoginRequest(String username, String password, String query) {
        this.requestBody = LoginSerializer.toJsonObject(username, password);
        setType();
        setEndpointUrl(LOGIN_ENDPOINT);
        addQuery(query);
    }

    @Override
    public void setType() {
        this.type = Type.POST;
    }
}
