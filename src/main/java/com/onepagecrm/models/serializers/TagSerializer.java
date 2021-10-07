package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TagSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(TagSerializer.class.getName());

    public static List<Tag> fromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;

        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject parsedObject = new JSONObject(parsedResponse);
            return fromJsonArray(parsedObject.getJSONArray(TAGS_TAG));

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;

        } catch (JSONException e) {
            LOG.severe("Could not find Tags tag");
            LOG.severe(e.toString());
        }
        return new ArrayList<>();
    }

    public static boolean fromStringSave(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        boolean saveSuccessful = false;

        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject parsedObject = new JSONObject(parsedResponse);
            if (parsedObject.length() > 0) {
                if (updateAccountTags(responseBody)) {
                    saveSuccessful = true;
                }
            }

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;

        } catch (JSONException e) {
            LOG.severe("Could not find create new JSONObject out of response");
            LOG.severe(e.toString());
        }

        return saveSuccessful;
    }

    public static boolean fromStringDelete(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        boolean saveSuccessful = false;

        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject parsedObject = new JSONObject(parsedResponse);
            if (parsedObject.length() == 0) {
                if (updateAccountTags(responseBody)) {
                    saveSuccessful = true;
                }
            }

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;

        } catch (JSONException e) {
            LOG.severe("Could not find create new JSONObject out of response");
            LOG.severe(e.toString());
        }

        return saveSuccessful;
    }

    private static boolean updateAccountTags(String responseBody) {
        boolean updateSuccessful = true;
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            JSONObject tagsObject = responseObject.getJSONObject(TAGS_TAG);
            JSONArray tagsArray = tagsObject.getJSONArray(TAGS_TAG);
            Account.currentUser.getAccount().setTags(TagSerializer.fromJsonArray(tagsArray));
        } catch (JSONException e) {
            LOG.severe("Could not find the Tags tag in response");
            LOG.severe(e.toString());
            updateSuccessful = false;
        }
        return updateSuccessful;
    }

    public static List<Tag> fromJsonArray(JSONArray tagsArray) {
        List<Tag> tags = new ArrayList<>();
        if (tagsArray != null) {
            for (int j = 0; j < tagsArray.length(); j++) {
                JSONObject tagObject;
                try {
                    tagObject = tagsArray.getJSONObject(j);
                    tags.add(fromJsonObject(tagObject));
                } catch (JSONException e) {
                    LOG.severe("Error parsing Tag array");
                    LOG.severe(e.toString());
                }
            }
        }
        return tags;
    }

    public static Tag fromJsonObject(JSONObject tagObject) {
        Tag tag = new Tag();
        try {
            if (tagObject.has(NAME_TAG)) {
                tag.setName(tagObject.getString(NAME_TAG));
            }
            if (tagObject.has(COUNTS_TAG)) {
                tag.setCount(tagObject.getInt(COUNTS_TAG));
            }
            if (tagObject.has(TOTAL_COUNT_TAG)) {
                tag.setTotalCount(tagObject.getInt(TOTAL_COUNT_TAG));
            }
            if (tagObject.has(ACTION_STREAM_COUNT_TAG)) {
                tag.setActionStreamCount(tagObject.getInt(ACTION_STREAM_COUNT_TAG));
            }
        } catch (JSONException e) {
            LOG.severe("Error parsing Tag object");
            LOG.severe(e.toString());
        }
        return tag;
    }

    public static String toJsonObject(Tag tag) {
        if (tag.getName() != null) {
            JSONObject tagObject = new JSONObject();
            addJsonStringValue(tag.getName(), tagObject, NAME_TAG);
            addJsonIntegerValue(tag.getCount(), tagObject, COUNTS_TAG);
            addJsonIntegerValue(tag.getTotalCount(), tagObject, TOTAL_COUNT_TAG);
            addJsonIntegerValue(tag.getActionStreamCount(), tagObject, ACTION_STREAM_COUNT_TAG);
            return tagObject.toString();
        }
        return null;
    }

    public static String toJsonArray(List<Tag> tags) {
        JSONArray tagsArray = new JSONArray();
        if (tags != null && !tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                try {
                    tagsArray.put(new JSONObject(toJsonObject(tags.get(i))));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of Tags");
                    LOG.severe(e.toString());
                }
            }
        }
        return tagsArray.toString();
    }
}
