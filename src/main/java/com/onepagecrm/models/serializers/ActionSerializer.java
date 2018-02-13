package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Action;
import com.onepagecrm.models.serializers.impl.SerializableAPI;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 18/09/2016.
 */
public class ActionSerializer extends SerializableAPI<Action> {

    private static final Logger LOG = Logger.getLogger(ActionSerializer.class.getName());

    private static volatile ActionSerializer instance;

    public static ActionSerializer getInstance() {
        if (instance == null) {
            synchronized (ActionSerializer.class) {
                if (instance == null) {
                    instance = new ActionSerializer();
                }
            }
        }
        return instance;
    }

    @Override
    protected Action singleResource() {
        return new Action();
    }

    @Override
    protected String singleTag() {
        return BaseSerializer.ACTION_TAG;
    }

    @Override
    protected String multipleTag() {
        return BaseSerializer.ACTIONS_TAG;
    }

    @Override
    protected Action fromJsonObjectImpl(JSONObject actionObject) {
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
                action.setStatus(Action.Status.fromString(status));
            }
            if (actionObject.has(DATE_TAG) && !actionObject.isNull(DATE_TAG)) {
                String dateStr = actionObject.getString(DATE_TAG);
                exactTime = DateSerializer.fromFormattedString(dateStr);
                action.setDate(exactTime);
            }
            if (actionObject.has(EXACT_TIME_TAG) && !actionObject.isNull(EXACT_TIME_TAG)) {
                String exactTimeStr = String.valueOf(actionObject.getInt(EXACT_TIME_TAG));
                exactTime = DateSerializer.fromTimestamp(exactTimeStr);
                action.setExactTime(exactTime);
            }
            if (actionObject.has(POSITION_TAG) && !actionObject.isNull(POSITION_TAG)) {
                int position = actionObject.getInt(POSITION_TAG);
                action.setPosition(position);
            }
            action.setDateColor(DateSerializer.getDateColour(exactTime, status));
            return action;

        } catch (JSONException e) {
            LOG.severe("Error parsing Action object");
            LOG.severe(e.toString());
            return singleResource();
        }
    }

    @Override
    protected JSONObject toJsonObjectImpl(Action action) {
        JSONObject actionObject = new JSONObject();
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
                            DateSerializer.toFormattedDateString(action.getDate()),
                            actionObject,
                            DATE_TAG
                    );
                    break;
                case DATE_TIME:
                    addJsonStringValue(
                            DateSerializer.toFormattedDateString(action.getDate()),
                            actionObject,
                            DATE_TAG
                    );
                    addJsonLongValue(
                            DateSerializer.toTimestamp(action.getExactTime()),
                            actionObject,
                            EXACT_TIME_TAG
                    );
                    break;
            }
        }
        return actionObject;
    }
}
