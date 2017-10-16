package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.Account;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class PatchRequest extends SignedRequest {

    /**
     * Constructor which takes JSON string for request body.
     *
     * @param endpoint
     * @param query
     * @param jsonBody
     */
    public PatchRequest(String endpoint, String query, String jsonBody) {
        this.requestBody = jsonBody;
        setType();
        setEndpointUrl(endpoint);
        addQuery(query);
        authenticate();
    }

    public PatchRequest(String endpoint) {
        setType();
        setEndpointUrl(endpoint);
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.PATCH, endpointUrl, ""));
    }

    @Override
    public void setType() {
        this.type = Type.PATCH;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.PATCH, endpointUrl, requestBody));
    }
}
