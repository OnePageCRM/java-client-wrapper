package com.onepagecrm.models.internal;

import com.onepagecrm.models.BaseResource;
import com.onepagecrm.models.serializers.LoginDataSerializer;

import java.util.Objects;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class LoginData extends BaseResource {

    private String username;
    private String password;
    private boolean fullResponse;
    private String endpointUrl;
    private String samlResponse;
    private String relayState;

    public LoginData(String username, String password, boolean fullResponse) {
        this.username = username;
        this.password = password;
        this.fullResponse = fullResponse;
    }

    public LoginData() {

    }

    @Override
    public String toString() {
        return LoginDataSerializer.toJsonString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginData loginData = (LoginData) o;
        return Objects.equals(endpointUrl, loginData.endpointUrl) &&
                Objects.equals(samlResponse, loginData.samlResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endpointUrl, samlResponse, relayState);
    }

    public boolean isValid() {
        return notNullOrEmpty(endpointUrl) && notNullOrEmpty(samlResponse);
    }

    public String getUsername() {
        return username;
    }

    public LoginData setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginData setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isFullResponse() {
        return fullResponse;
    }

    public LoginData setFullResponse(boolean fullResponse) {
        this.fullResponse = fullResponse;
        return this;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public LoginData setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public String getSamlResponse() {
        return samlResponse;
    }

    public LoginData setSamlResponse(String samlResponse) {
        this.samlResponse = samlResponse;
        return this;
    }

    public String getRelayState() {
        return relayState;
    }

    public LoginData setRelayState(String relayState) {
        this.relayState = relayState;
        return this;
    }
}
