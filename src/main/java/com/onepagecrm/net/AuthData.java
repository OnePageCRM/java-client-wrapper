package com.onepagecrm.net;

import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 08/01/2019.
 * Copyright (c) 2019 OnePageCRM. All rights reserved.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class AuthData {

    private static final Logger LOG = Logger.getLogger(AuthData.class.getName());

    private String userId;
    private String apiKey;
    private String signature;

    /**
     * @param userId - Unique identifier of the user.
     * @param apiKey - Like the API password.
     */
    public AuthData(String userId, String apiKey) {
        this.userId = userId;
        this.apiKey = apiKey;
    }

    /**
     * Method which handles the process of calculating auth data.
     *
     * @return - Authentication data.
     */
    public abstract String calculateSignature();

    public void updateSignature() {
        this.setSignature(calculateSignature());
    }

    private void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserId() {
        return userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSignature() {
        return signature;
    }
}
