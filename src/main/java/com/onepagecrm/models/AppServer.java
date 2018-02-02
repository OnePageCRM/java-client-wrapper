package com.onepagecrm.models;

import com.onepagecrm.models.serializers.AppServerSerializer;
import com.onepagecrm.net.ApiResource;

/**
 * Created by Cillian Myles on 01/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class AppServer extends ApiResource {

    private String id;
    private String name;
    private String metadataUrl;

    @Override
    public String toString() {
        return AppServerSerializer.toJsonString(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public AppServer setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AppServer setName(String pName) {
        name = pName;
        return this;
    }

    public String getMetadataUrl() {
        return metadataUrl;
    }

    public AppServer setMetadataUrl(String pMetadataUrl) {
        metadataUrl = pMetadataUrl;
        return this;
    }
}
