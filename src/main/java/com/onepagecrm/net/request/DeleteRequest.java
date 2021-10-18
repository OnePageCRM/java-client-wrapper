package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.Account;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class DeleteRequest extends SignedRequest {

    public DeleteRequest(String endpoint) {
        setType();
        setEndpointUrl(endpoint);
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.currentUser) :
                new OnePageAuthData(Account.currentUser, Request.DELETE, endpointUrl, requestBody));
    }

    public DeleteRequest(String endpoint, String query) {
        setType();
        setEndpointUrl(endpoint);
        if (query == null) {
            authenticate();
        } else {
            addQuery(query);
            authenticate();
        }
    }

    @Override
    public void setType() {
        this.type = Type.DELETE;
    }

    public DeleteRequest addQuery(String query) {
        this.endpointUrl += query;
        return this;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(Account.currentUser) :
                new OnePageAuthData(Account.currentUser, Request.DELETE, endpointUrl, requestBody));
    }
}