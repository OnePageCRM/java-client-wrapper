package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.Account;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class PutRequest extends SignedRequest {

    /**
     * Constructor which takes JSON string for request body.
     *
     * @param endpoint
     * @param query
     * @param jsonBody
     */
    public PutRequest(String endpoint, String query, String jsonBody) {
        this.requestBody = jsonBody;
        setType();
        setEndpointUrl(endpoint);
        addQuery(query);
        authenticate();
    }

    public PutRequest(String endpoint) {
        setType();
        setEndpointUrl(endpoint);
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.PUT, endpointUrl, ""));
        setAuthData(new BasicAuthData(Account.loggedInUser));
    }

    @Override
    public void setType() {
        this.type = Type.PUT;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.PUT, endpointUrl, requestBody));
    }
}
