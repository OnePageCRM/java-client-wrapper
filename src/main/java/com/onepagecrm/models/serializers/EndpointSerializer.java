package com.onepagecrm.models.serializers;

import java.net.URI;

/**
 * Created by Cillian Myles on 06/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class EndpointSerializer {

    private final static String PATH_SEPARATOR = "/";
    private final static String SCHEME_SEPARATOR = "://";

    public static String full(String endpoint) {
        if (endpoint == null) return "";
        return endpoint.substring(0, endpoint.lastIndexOf(PATH_SEPARATOR));
    }

    public static String base(String endpoint) {
        if (endpoint == null) return "";
        URI url = URI.create(endpoint);
        return url.getScheme() + SCHEME_SEPARATOR + url.getAuthority();
    }
}
