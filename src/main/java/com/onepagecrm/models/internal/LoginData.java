package com.onepagecrm.models.internal;

import com.onepagecrm.models.BaseResource;

import java.util.Objects;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class LoginData extends BaseResource {

    private String endpointUrl;
    private String samlResponse;
    private String relayState;

    public LoginData() {

    }

    @Override
    public String toString() {
        return "LoginData{" +
                "endpointUrl='" + endpointUrl + '\'' +
                ", samlResponse='" + samlResponse + '\'' +
                ", relayState='" + relayState + '\'' +
                '}';
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
