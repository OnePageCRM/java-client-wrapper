package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.helpers.ActionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class ActionSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(ActionSerializer.class.getName());

    public static Action fromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            JSONObject actionObject = responseObject.optJSONObject(ACTION_TAG);
            return fromJsonObject(actionObject);

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;
        } catch (JSONException e) {
            LOG.severe("Could not find action object tags");
            LOG.severe(e.toString());
            return new Action();
        }
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
                action.setCreatedAt(InstantSerializer.getInstance().parse(actionObject.optString(CREATED_AT_TAG)));
            }
            if (actionObject.has(MODIFIED_AT_TAG)) {
                action.setModifiedAt(InstantSerializer.getInstance().parse(actionObject.optString(MODIFIED_AT_TAG)));
            }
            if (actionObject.has(STATUS_TAG)) {
                String status = actionObject.getString(STATUS_TAG);
                action.setStatus(Action.Status.fromString(status));
            }
            if (actionObject.has(DATE_TAG)) {
                action.setDate(LocalDateSerializer.getInstance().parse(actionObject.optString(DATE_TAG)));
            }
            if (actionObject.has(EXACT_TIME_TAG)) {
                action.setExactTime(InstantSerializer.getInstance().ofSeconds(actionObject.optLong(EXACT_TIME_TAG)));
            }
            if (actionObject.has(POSITION_TAG)) {
                if (!actionObject.isNull(POSITION_TAG)) {
                    int position = actionObject.getInt(POSITION_TAG);
                    action.setPosition(position);
                }
            }
            final int calculatedFlagColor = ActionHelper.calculateFlagColor(action);
            action.setFlagColor(calculatedFlagColor);

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
                    InstantSerializer.getInstance().format(action.getCreatedAt()),
                    actionObject,
                    CREATED_AT_TAG
            );
            addJsonStringValue(
                    InstantSerializer.getInstance().format(action.getModifiedAt()),
                    actionObject,
                    MODIFIED_AT_TAG
            );
            if (action.getStatus() != null) {
                addJsonStringValue(action.getStatus().toString(), actionObject, STATUS_TAG);
                switch (action.getStatus()) {
                    case DATE:
                    case QUEUED_WITH_DATE:
                        addJsonStringValue(
                                LocalDateSerializer.getInstance().format(action.getDate()),
                                actionObject,
                                DATE_TAG
                        );
                        break;
                    case DATE_TIME:
                        addJsonStringValue(
                                LocalDateSerializer.getInstance().format(action.getDate()),
                                actionObject,
                                DATE_TAG
                        );
                        addJsonLongValue(
                                InstantSerializer.getInstance().seconds(action.getExactTime()),
                                actionObject,
                                EXACT_TIME_TAG
                        );
                        break;
                }
            }
        }
        return actionObject.toString();
    }

    public static String toJsonArray(List<Action> actions) {
        JSONArray actionsArray = new JSONArray();
        if (actions != null && !actions.isEmpty()) {
            for (Action action : actions) {
                try {
                    actionsArray.put(new JSONObject(toJsonObject(action)));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of list of Actions.");
                    LOG.severe(e.toString());
                }
            }
        }
        return actionsArray.toString();
    }
}
