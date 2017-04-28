package com.onepagecrm.net.request;

import com.onepagecrm.models.Account;
import com.onepagecrm.net.BasicAuthData;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class DeleteRequest extends BasicSignedRequest {

    public DeleteRequest(String endpoint) {
        setType();
        setEndpointUrl(endpoint);
        setAuthData(new BasicAuthData(Account.loggedInUser));
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
        setAuthData(new BasicAuthData(Account.loggedInUser));
    }
}
