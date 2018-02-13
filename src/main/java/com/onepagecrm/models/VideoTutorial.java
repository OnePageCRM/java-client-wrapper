package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.serializers.VideoTutorialSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VideoTutorial extends ApiResource implements Serializable {

    private static final Logger LOG = Logger.getLogger(VideoTutorial.class.getSimpleName());

    private static final String VIDEO_TUTORIALS_LINK = "https://cdn-static.onepagecrm.com/videos/android_tutorial.txt";

    private String id;
    private List<VideoLink> videoLinks;
    private String name;

    public static List<VideoTutorial> list() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        Request request = new GetRequest(VIDEO_TUTORIALS_LINK, Query.fromParams(params), true);
        Response response = request.send();
        return VideoTutorialSerializer.fromString(response.getResponseBody());
    }

    public VideoTutorial() {
    }

    @Override
    public String toString() {
        return VideoTutorialSerializer.toJsonObject(this);
    }

    /**
     * Method to compare VideoLinks to one another based off of their name.
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof VideoTutorial) {
            VideoTutorial toCompare = (VideoTutorial) object;
            if (this.name != null && toCompare.name != null) {
                return this.name.equals(toCompare.name);
            }
        }
        return false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public VideoTutorial setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public VideoTutorial setName(String name) {
        this.name = name;
        return this;
    }

    public List<VideoLink> getVideoLinks() {
        return videoLinks;
    }

    public VideoTutorial setVideoLinks(List<VideoLink> videoLinks) {
        this.videoLinks = videoLinks;
        return this;
    }
}
