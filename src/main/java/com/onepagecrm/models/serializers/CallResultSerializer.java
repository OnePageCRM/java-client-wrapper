package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Account;
import com.onepagecrm.models.CallResult;
import com.onepagecrm.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 01/08/2017.
 */
@SuppressWarnings({"WeakerAccess", "ForLoopReplaceableByForEach", "unused"})
public class CallResultSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(CallResultSerializer.class.getName());

    @SuppressWarnings("unchecked")
    public static List<CallResult> fromJsonObject(JSONObject dataObject) {
        if (dataObject == null) {
            return null;
        }

        JSONObject callResultsObject = dataObject.optJSONObject(CALL_RESULTS_TAG);
        if (callResultsObject == null) {
            return defaultCallResults();
        }

        Map<String, CallResult> resultMap = new HashMap<>();
        List<CallResult> resultList = new LinkedList<>();
        Iterator<String> iterator = callResultsObject.keys();
        int index = 0;

        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                String value = callResultsObject.getString(key);
                CallResult callResult = new CallResult()
                        .setId(key)
                        .setDisplay(value)
                        .setPosition(index);
                resultMap.put(key, callResult);
                resultList.add(callResult);

            } catch (JSONException e) {
                LOG.severe("Failed to parse all values in call_results object");
                LOG.severe(e.toString());
            }
            index++;
        }

        JSONArray callResultsOrderArray = dataObject.optJSONArray(CALL_RESULTS_ORDER_TAG);
        if (callResultsOrderArray != null) {
            List<CallResult> orderedList = new LinkedList<>();
            String id;
            for (int i = 0; i < callResultsOrderArray.length(); i++) {
                try {
                    id = callResultsOrderArray.getString(i);
                    CallResult result = resultMap.get(id);
                    result.setPosition(i);
                    orderedList.add(result);

                } catch (JSONException e) {
                    LOG.severe("Failed to parse all values in call_results_order array");
                    LOG.severe(e.toString());
                }
            }
            return orderedList;
        }

        return resultList;
    }

    public static String toJsonObject(CallResult callResult) {
        JSONObject callObject = new JSONObject();
        addJsonStringValue(callResult.getDisplay(), callObject, callResult.getId());
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

    private static void printCallResults(List<CallResult> callResults) {
        if (callResults == null || callResults.isEmpty()) return;
        for (int i = 0; i < callResults.size(); i++) {
            CallResult result = callResults.get(i);
            LOG.info("call_result[" + i + "] : " + result.getDisplay());
        }
    }

    public static String toJsonString(CallResult callResult) {
        if (callResult.getId() != null && callResult.getDisplay() != null) {
            return "\"" + callResult.getId() + "\":\"" + callResult.getDisplay() + "\"";
        }
        return "";
    }

    public static List<CallResult> getCurrentUser() {
        final User currentUser = Account.currentUser;
        final boolean callResultsExist = currentUser != null && currentUser.isValid() &&
                currentUser.getAccount() != null && currentUser.getAccount().getCallResults() != null;
        return (callResultsExist) ?
                currentUser.getAccount().getCallResults() : new ArrayList<CallResult>();
    }

    public static List<CallResult> defaultCallResults() {
        List<CallResult> callResults = new LinkedList<>();
        callResults.add(new CallResult().setPosition(0).setId("interested").setDisplay("Interested"));
        callResults.add(new CallResult().setPosition(1).setId("not_interested").setDisplay("Not interested"));
        callResults.add(new CallResult().setPosition(2).setId("left_message").setDisplay("Left message"));
        callResults.add(new CallResult().setPosition(3).setId("no_answer").setDisplay("No answer"));
        callResults.add(new CallResult().setPosition(4).setId("other").setDisplay("Other"));
        return callResults;
    }
}
