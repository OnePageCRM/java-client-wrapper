package com.onepagecrm.models.serializer;

import com.onepagecrm.models.CallResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Logger;

public class CallResultSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(CallResultSerializer.class.getName());

    public static String toJsonKeyValuePair(CallResult callResult) {
        return callResult.toString();
    }

    public static String toJsonObject(CallResult callResult) {
        JSONObject callObject = new JSONObject();
        addJsonStringValue(callResult.getId(), callObject, CALL_RESULT_TAG);
        addJsonStringValue(callResult.getText(), callObject, TEXT_TAG);
        return callObject.toString();
    }

    public static String toJsonArray(List<CallResult> results) {
        JSONArray resultsArray = new JSONArray();
        for (int i = 0; i < results.size(); i++) {
            try {
                resultsArray.put(new JSONObject(toJsonObject(results.get(i))));
            } catch (JSONException e) {
                LOG.severe("Error creating JSONObject out of CallResult");
                LOG.severe(e.toString());
            }
        }
        return resultsArray.toString();
    }
}
