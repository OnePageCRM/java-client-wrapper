package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.LoginData;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class LoginDataSerializer extends BaseSerializer {

    private static final LoginData DEFAULT = new LoginData();

//    public static LoginData fromResponse(Response response) throws APIException {
//        JSONObject dataObject = (JSONObject) BaseSerializer.fromResponse(response);
//        return fromJsonObject(dataObject);
//    }

    // TODO: delete
    public static LoginData fromString(String response) throws OnePageException {
        //String dataString = (String) BaseSerializer.fromString(response);
        try {
            JSONObject dataObject = new JSONObject(response);
            return fromJsonObject(dataObject);

        } catch (JSONException e) {
            e.printStackTrace();
            return DEFAULT;
        }
    }

    public static LoginData fromJsonObject(JSONObject dataObject) {
        if (dataObject == null) return DEFAULT;
        JSONObject loginParamsObject = dataObject.optJSONObject(LOGIN_PARAMS_TAG);
        if (loginParamsObject == null) loginParamsObject = new JSONObject();
        return new LoginData()
                .setEndpointUrl(dataObject.optString(ENDPOINT_URL_TAG))
                .setSamlResponse(loginParamsObject.optString(SAML_RESPONSE_TAG))
                .setRelayState(loginParamsObject.optString(RELAY_STATE_TAG));
    }

    public static JSONObject toJsonObject(LoginData loginData) {
        JSONObject loginDataObject = new JSONObject();
        if (loginData == null) return loginDataObject;
        addJsonStringValue(loginData.getUsername(), loginDataObject, LOGIN_TAG);
        addJsonStringValue(loginData.getPassword(), loginDataObject, PASSWORD_TAG);
        addJsonBooleanValue(loginData.isFullResponse(), loginDataObject, FULL_RESPONSE_TAG);
        addJsonStringValue(loginData.getSamlResponse(), loginDataObject, SAML_RESPONSE_TAG);
        addJsonStringValue(loginData.getRelayState(), loginDataObject, RELAY_STATE_TAG);
        return loginDataObject;
    }

    public static String toJsonString(LoginData loginData) {
        return toJsonObject(loginData).toString();
    }
}
