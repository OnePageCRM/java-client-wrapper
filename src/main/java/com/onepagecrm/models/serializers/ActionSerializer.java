package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.helpers.ActionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;

import java.util.Date;
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
                String status = actionObject.getString(STATUS_TAG);
                action.setStatus( Action.Status.fromString(status));
            }
            if (actionObject.has(DATE_TAG)) {
                if (!actionObject.isNull(DATE_TAG)) {
                    String dateStr = actionObject.getString(DATE_TAG);
                    Date date = DateSerializer.fromFormattedString(dateStr);
                    action.setDate(date);
                }
                // NEW !!
                LocalDate localDate = LocalDateSerializer.getInstance().parse(actionObject.optString(DATE_TAG));
                action.setJ8Date(localDate);
            }
            if (actionObject.has(EXACT_TIME_TAG)) {
                if (!actionObject.isNull(EXACT_TIME_TAG)) {
                    String exactTimeStr = String.valueOf(actionObject.getInt(EXACT_TIME_TAG));
                    Date exactTime = DateSerializer.fromTimestamp(exactTimeStr);
                    action.setExactTime(exactTime);
                }
                // NEW !!
                Instant exactTime = InstantSerializer.getInstance().ofSeconds(actionObject.optLong(EXACT_TIME_TAG));
                action.setJ8ExactTime(exactTime);
            }
            if (actionObject.has(POSITION_TAG)) {
                if (!actionObject.isNull(POSITION_TAG)) {
                    int position = actionObject.getInt(POSITION_TAG);
                    action.setPosition(position);
                }
            }

            final int dateColor = ActionHelper.getFlagColor(action);
            action.setDateColor(dateColor);

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
            if (action.getStatus() != null) {
                addJsonStringValue(action.getStatus().toString(), actionObject, STATUS_TAG);
                switch (action.getStatus()) {
                    case DATE:
                    case QUEUED_WITH_DATE:
                        addJsonStringValue(
                                LocalDateSerializer.getInstance().format(action.getJ8Date()),
                                actionObject,
                                DATE_TAG
                        );
                        break;
                    case DATE_TIME:
                        addJsonStringValue(
                                LocalDateSerializer.getInstance().format(action.getJ8Date()),
                                actionObject,
                                DATE_TAG
                        );
                        addJsonLongValue(
                                InstantSerializer.getInstance().seconds(action.getJ8ExactTime()),
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
