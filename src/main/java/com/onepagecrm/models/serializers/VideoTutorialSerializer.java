package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.VideoLink;
import com.onepagecrm.models.VideoTutorial;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VideoTutorialSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(VideoTutorialSerializer.class.getName());

    public static List<VideoTutorial> fromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;

        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            JSONArray videoTutorialsArray = responseObject.getJSONArray(VIDEO_TUTORIALS_TAG);
            return fromJsonArray(videoTutorialsArray);

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;

        } catch (JSONException e) {
            LOG.severe("Could not find Video Tutorials videoTutorial");
            LOG.severe(e.toString());
        }
        return new ArrayList<>();
    }

    public static List<VideoTutorial> fromJsonArray(JSONArray videoTutorialsArray) {
        List<VideoTutorial> videoTutorials = new ArrayList<>();
        if (videoTutorialsArray != null) {
            for (int j = 0; j < videoTutorialsArray.length(); j++) {
                JSONObject videoTutorialObject;
                try {
                    videoTutorialObject = videoTutorialsArray.getJSONObject(j);
                    videoTutorials.add(fromJsonObject(videoTutorialObject));
                } catch (JSONException e) {
                    LOG.severe("Error parsing VideoTutorial link array");
                    LOG.severe(e.toString());
                }
            }
        }
        return videoTutorials;
    }

    public static VideoTutorial fromJsonObject(JSONObject videoLinkObject) {
        VideoTutorial videoTutorial = new VideoTutorial();
        try {
            if (videoLinkObject.has(VIDEO_NAME_TAG)) {
                videoTutorial.setName(videoLinkObject.getString(VIDEO_NAME_TAG));
            }
            if (videoLinkObject.has(LINKS_TAG)) {
                JSONArray videoLinksArray = videoLinkObject.getJSONArray(LINKS_TAG);
                List<VideoLink> videoLinks = VideoLinkSerializer.fromJsonArray(videoLinksArray);
                if (!videoLinks.isEmpty()) videoTutorial.setVideoLinks(videoLinks);
            }
        } catch (JSONException e) {
            LOG.severe("Error parsing Tag object");
            LOG.severe(e.toString());
        }
        return videoTutorial;
    }

    public static String toJsonObject(VideoTutorial videoTutorial) {
        if (videoTutorial.getName() != null) {
            JSONObject videoTutorialObject = new JSONObject();
            addJsonStringValue(videoTutorial.getName(), videoTutorialObject, VIDEO_NAME_TAG);

            // Serialize VideoLinks.
            try {
                JSONArray videoLinksArray = new JSONArray(VideoLinkSerializer.toJsonArray(videoTutorial.getVideoLinks()));
                addJsonArray(videoLinksArray, videoTutorialObject, LINKS_TAG);
            } catch (JSONException e) {
                LOG.severe("Error creating VideoLinks array while constructing VideoTutorial object");
                LOG.severe(e.toString());
            }

            return videoTutorialObject.toString();
        }
        return null;
    }

    public static String toJsonArray(List<VideoTutorial> videoTutorials) {
        JSONArray videoTutorialsArray = new JSONArray();
        if (videoTutorials != null && !videoTutorials.isEmpty()) {
            for (int i = 0; i < videoTutorials.size(); i++) {
                try {
                    videoTutorialsArray.put(new JSONObject(toJsonObject(videoTutorials.get(i))));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of VideoTutorials");
                    LOG.severe(e.toString());
                }
            }
        }
        return videoTutorialsArray.toString();
    }
}
