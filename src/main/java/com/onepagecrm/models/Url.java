package com.onepagecrm.models;

import com.onepagecrm.models.serializers.UrlSerializer;

import java.io.Serializable;

@SuppressWarnings("SpellCheckingInspection")
public class Url extends BaseResource implements Serializable {

    public static final String TYPE_WEBSITE = "website";
    public static final String TYPE_BLOG = "blog";
    public static final String TYPE_TWITTER = "twitter";
    public static final String TYPE_LINKEDIN = "linkedin";
    public static final String TYPE_XING = "xing";
    public static final String TYPE_FACEBOOK = "facebook";
    public static final String TYPE_GOOGLE_PLUS = "google_plus";
    public static final String TYPE_OTHER = "other";

    private String type;
    private String value;

    public Url(String type, String value) {
        this.setType(type);
        this.setValue(value);
    }

    public Url() {}

    public String toString() {
        return UrlSerializer.toJsonObject(this);
    }

    /**
     * Method to compare Url obj's to one another based off of their value attr.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Url) {
            Url toCompare = (Url) object;
            if (this.value != null && toCompare.value != null) {
                return this.value.equals(toCompare.value);
            }
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public Url setType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Url setValue(String value) {
        this.value = value;
        return this;
    }
}
