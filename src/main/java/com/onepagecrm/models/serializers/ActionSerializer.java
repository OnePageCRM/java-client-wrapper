package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ActionSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(ActionSerializer.class.getName());

    public static Action fromString(String pResponseBody) {
        Action action = new Action();
        try {
            JSONObject responseObject = new JSONObject(pResponseBody);
            JSONObject data = responseObject.optJSONObject(DATA_TAG);
            JSONObject actionObj = data.optJSONObject(ACTION_TAG);
            action = fromJsonObject(actionObj);
        } catch (JSONException e) {
            LOG.severe("Could not find action object tags");
            LOG.severe(e.toString());
        }

        return action;
    }


    public static List<Action> listFromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonArray(responseObject.optJSONArray(ACTIONS_TAG));

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;
        } catch (JSONException e) {
            LOG.severe("Error parsing JSON" + e);
            return new LinkedList<>();
        }
    }

    public static Action fromJsonObject(JSONObject actionObject) {
        Action action = new Action();
        String status = null;
        Date exactTime = null;
        try {
            // Fix for some objects not having name.
            if (actionObject.has(ACTION_TAG)) {
                actionObject = actionObject.getJSONObject(ACTION_TAG);
            }
            // Now parse the info.
            if (actionObject.has(ID_TAG)) {
                action.setId(actionObject.getString(ID_TAG));
            }
            if (actionObject.has(CONTACT_ID_TAG)) {
                action.setContactId(actionObject.getString(CONTACT_ID_TAG));
            }
            if (actionObject.has(TEXT_TAG)) {
                action.setText(actionObject.getString(TEXT_TAG));
            }
            if (actionObject.has(ASSIGNEE_ID_TAG)) {
                action.setAssigneeId(actionObject.getString(ASSIGNEE_ID_TAG));
            }
            if (actionObject.has(CREATED_AT_TAG)) {
                String createdAtStr = actionObject.getString(CREATED_AT_TAG);
                Date createdAt = DateSerializer.fromFormattedString(createdAtStr);
                action.setCreatedAt(createdAt);
            }
            if (actionObject.has(MODIFIED_AT_TAG)) {
                String modifiedAtStr = actionObject.getString(MODIFIED_AT_TAG);
                Date modifiedAt = DateSerializer.fromFormattedString(modifiedAtStr);
                action.setModifiedAt(modifiedAt);
            }
            if (actionObject.has(STATUS_TAG)) {
                status = actionObject.getString(STATUS_TAG);
                action.setStatus(status);
            }
            if (actionObject.has(DATE_TAG)) {
                if (!actionObject.isNull(DATE_TAG)) {
                    String dateStr = actionObject.getString(DATE_TAG);
                    exactTime = DateSerializer.fromFormattedString(dateStr);
                    action.setDate(exactTime);
                }
            }
            if (actionObject.has(EXACT_TIME_TAG)) {
                if (!actionObject.isNull(EXACT_TIME_TAG)) {
                    String exactTimeStr = String.valueOf(actionObject.getInt(EXACT_TIME_TAG));
                    exactTime = DateSerializer.fromTimestamp(exactTimeStr);
                    action.setExactTime(exactTime);
                }
            }
            action.setDateColor(DateSerializer.getDateColour(exactTime, status));

            return action;

        } catch (JSONException e) {
            LOG.severe("Error parsing Action object");
            LOG.severe(e.toString());
        }
        return new Action();
    }

    public static List<Action> fromJsonArray(JSONArray actionsArray) {
        List<Action> actions = new LinkedList<>();
        for (int i = 0; i < actionsArray.length(); i++) {
            try {
                actions.add(fromJsonObject(actionsArray.getJSONObject(i)));
            } catch (JSONException e) {
                LOG.severe("Error parsing Action array");
                LOG.severe(e.toString());
            }
        }
        return actions;
    }

    public static String toJsonObject(Action action) {
        JSONObject actionObject = new JSONObject();
        if (action != null) {
            addJsonStringValue(action.getId(), actionObject, ID_TAG);
            addJsonStringValue(action.getContactId(), actionObject, CONTACT_ID_TAG);
            addJsonStringValue(action.getText(), actionObject, TEXT_TAG);
            addJsonStringValue(action.getAssigneeId(), actionObject, ASSIGNEE_ID_TAG);
            addJsonStringValue(
                    DateSerializer.toFormattedDateTimeString(action.getCreatedAt()),
                    actionObject,
                    CREATED_AT_TAG
            );
            addJsonStringValue(
                    DateSerializer.toFormattedDateTimeString(action.getModifiedAt()),
                    actionObject,
                    MODIFIED_AT_TAG
            );
            addJsonStringValue(action.getStatus(), actionObject, STATUS_TAG);
            addJsonStringValue(
                    DateSerializer.toFormattedDateString(action.getDate()),
                    actionObject,
                    DATE_TAG
            );
        }
        return actionObject.toString();
    }

    public static String toJsonArray(List<Action> actions) {
        JSONArray actionsArray = new JSONArray();
        if (actions != null && !actions.isEmpty()) {
            for (int i = 0; i < actions.size(); i++) {
                try {
                    actionsArray.put(new JSONObject(toJsonObject(actions.get(i))));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of list of Actions.");
                    LOG.severe(e.toString());
                }
            }
        }
        return actionsArray.toString();
    }
}
