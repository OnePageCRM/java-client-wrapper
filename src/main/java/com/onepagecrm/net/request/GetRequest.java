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

    public GetRequest(String endpoint, String query, boolean externalEndpoint) {
        getRequest(endpoint, query, externalEndpoint);
    }

    public GetRequest(String endpoint, String query) {
        getRequest(endpoint, query, false);
    }

    @Override
    public void setType() {
        this.type = Type.GET;
    }

    private void getRequest(String endpoint, String query, boolean externalEndpoint) {
        setType();
        setEndpointUrl(endpoint, externalEndpoint);
        if (query == null) {
            authenticate();
        } else {
            addQuery(query);
            authenticate();
        }
    }

    public GetRequest addQuery(String query) {
        this.endpointUrl += query;
        return this;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.loggedInUser) :
                new OnePageAuthData(Account.loggedInUser, Request.GET, endpointUrl, requestBody));
    }
}