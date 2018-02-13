package com.onepagecrm.models.serializers;

import com.onepagecrm.models.VideoLink;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VideoLinkSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(VideoLinkSerializer.class.getName());

    public static List<VideoLink> fromJsonArray(JSONArray videoLinksArray) {
        List<VideoLink> videoLinks = new ArrayList<>();
        for (int j = 0; j < videoLinksArray.length(); j++) {
            JSONObject videoLinkObject;
            try {
                videoLinkObject = videoLinksArray.getJSONObject(j);
                videoLinks.add(fromJsonObject(videoLinkObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing videoLink array");
                LOG.severe(e.toString());
            }
        }
        return videoLinks;
    }

    public static VideoLink fromJsonObject(JSONObject videoLinkObject) {
        VideoLink videoLink = new VideoLink();
        try {
            String link = videoLinkObject.getString(LINK_TAG);
            String resolution = videoLinkObject.getString(RESOLUTION_TAG);
            return videoLink
                    .setLink(link)
                    .setResolution(resolution);
        } catch (JSONException e) {
            LOG.severe("Error parsing videoLink object");
            LOG.severe(e.toString());
        }
        return videoLink;
    }

    public static String toJsonObject(VideoLink videoLink) {
        if (videoLink.getLink() != null) {
            JSONObject videoLinkObject = new JSONObject();
            addJsonStringValue(videoLink.getLink(), videoLinkObject, LINK_TAG);
            addJsonStringValue(videoLink.getResolution(), videoLinkObject, RESOLUTION_TAG);
            return videoLinkObject.toString();
        } else {
            return null;
        }
    }

    public static String toJsonArray(List<VideoLink> videoLinks) {
        JSONArray videoLinksArray = new JSONArray();
        if (videoLinks != null && !videoLinks.isEmpty()) {
            for (int i = 0; i < videoLinks.size(); i++) {
                try {
                    if (videoLinks.get(i).getLink() != null) {
                        videoLinksArray.put(new JSONObject(toJsonObject(videoLinks.get(i))));
                    }
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of VideoLinks");
                    LOG.severe(e.toString());
                }
            }
        }
        return videoLinksArray.toString();
    }
}
