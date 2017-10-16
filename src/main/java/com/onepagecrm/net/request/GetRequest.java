package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.Account;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class GetRequest extends SignedRequest {

    public GetRequest(String endpoint) {
        setType();
        setEndpointUrl(endpoint);
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.GET, endpointUrl, requestBody));
    }

    public GetRequest(String endpoint, String query) {
        setType();
        setEndpointUrl(endpoint);
        addQuery(query);
        authenticate();
    }

    @Override
    public void setType() {
        this.type = Type.GET;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.GET, endpointUrl, requestBody));
    }
}