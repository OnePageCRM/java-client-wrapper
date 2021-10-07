package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

import java.util.Map;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class PostRequest extends SignedRequest {

    /**
     * Constructor which takes empty request body.
     *
     * @param endpoint
     * @param query
     */
    public PostRequest(String endpoint, String query) {
        setType();
        setEndpointUrl(endpoint);
        if (query == null) {
            authenticate();
        } else {
            addQuery(query);
            authenticate();
        }
    }

    /**
     * Constructor which takes JSON string for request body.
     *
     * @param endpoint
     * @param query
     * @param jsonBody
     */
    public PostRequest(String endpoint, String query, String jsonBody) {
        this.requestBody = jsonBody;
        setType();
        setEndpointUrl(endpoint);
        if (query == null) {
            authenticate();
        } else {
            addQuery(query);
            authenticate();
        }
    }

    /**
     * Constructor which takes map of key-value pairs for request body.
     *
     * @param endpoint
     * @param query
     * @param paramBody
     */
    public PostRequest(String endpoint, String query, Map<String, Object> paramBody) {
        this.params = paramBody;
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
        this.type = Type.POST;
    }

    public PostRequest addQuery(String query) {
        this.endpointUrl += query;
        return this;
    }

    public void authenticate() {
        setRequestBody();
        setAuthData((!OnePageCRM.COMPLEX_AUTH) ?
                new BasicAuthData(OnePageCRM.getInstance().getUserId(), OnePageCRM.getInstance().getApiKey()) :
                new OnePageAuthData(OnePageCRM.getInstance().getUserId(), OnePageCRM.getInstance().getApiKey(),
                                    Request.POST, endpointUrl, requestBody));
    }
}
