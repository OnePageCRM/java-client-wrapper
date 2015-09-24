package com.onepagecrm.models.serializer;

import com.onepagecrm.models.Tag;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TagSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(TagSerializer.class.getName());

    public static List<Tag> fromJsonArray(JSONArray tagsArray) {
        List<Tag> tags = new ArrayList<>();
        for (int j = 0; j < tagsArray.length(); j++) {
            JSONObject tagObject;
            try {
                tagObject = tagsArray.getJSONObject(j);
                tags.add(fromJsonObject(tagObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing tag array");
                LOG.severe(e.toString());
            }
        }
        return tags;
    }

    public static Tag fromJsonObject(JSONObject tagObject) {
        Tag tag = new Tag();
        try {
            String name = tagObject.getString(NAME_TAG);
            int counts = tagObject.getInt(COUNTS_TAG);
            int totalCounts = tagObject.getInt(TOTAL_COUNT_TAG);
            if (tagObject.has(ACTION_STREAM_COUNT_TAG)) {
                tag.setActionStreamCount(tagObject.getInt(ACTION_STREAM_COUNT_TAG));
            }
            return tag
                    .setName(name)
                    .setCounts(counts)
                    .setTotalCounts(totalCounts);
        } catch (JSONException e) {
            LOG.severe("Error parsing tag object");
            LOG.severe(e.toString());
        }
        return tag;
    }

    public static String toJsonObject(Tag tag) {
        JSONObject tagObject = new JSONObject();
        addJsonStringValue(tag.getName(), tagObject, NAME_TAG);
        addJsonIntValue(tag.getCounts(), tagObject, COUNTS_TAG);
        addJsonIntValue(tag.getTotalCounts(), tagObject, TOTAL_COUNT_TAG);
        addJsonIntegerValue(tag.getActionStreamCount(), tagObject, ACTION_STREAM_COUNT_TAG);
        return tagObject.toString();
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
