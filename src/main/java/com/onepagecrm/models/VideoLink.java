package com.onepagecrm.models;

import java.io.Serializable;

public class VideoLink extends BaseResource implements Serializable {

    private String link;
    private String resolution;

    public VideoLink(String link, String resolution) {
        this.setLink(link);
        this.setResolution(resolution);
    }

    public VideoLink() {
    }

    public String toString() {
        return "";
        //return UrlSerializer.toJsonObject(this);
    }

    /**
     * Method to compare VideoLink obj's to one another based off of their value attr.
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof VideoLink) {
            VideoLink toCompare = (VideoLink) object;
            if (this.link != null && toCompare.link != null) {
                return this.link.equals(toCompare.link);
            }
        }
        return false;
    }

    public String getResolution() {
        return resolution;
    }

    public String getLink() {
        return link;
    }

    public VideoLink setLink(String link) {
        this.link = link;
        return this;
    }

    public VideoLink setResolution(String resolution) {
        this.resolution = resolution;
        return this;
    }
}
