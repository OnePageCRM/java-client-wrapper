package com.onepagecrm.net;

import com.onepagecrm.TestHelper;
import com.onepagecrm.net.request.Request;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Cillian Myles on 09/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class RequestTest {

    private static final int DEFAULT_SERVER_ID = 0;
    private static final String DEFAULT_URL = "https://app.onepagecrm.com";
    private static final String DEFAULT_API_URL = "https://app.onepagecrm.com/api/v3";

    @Test
    public void appUsServerIsDefault() {
        assertEquals("Server URLs do not match",
                DEFAULT_URL, Request.getServerUrl(DEFAULT_SERVER_ID));

        assertEquals("Server URLs (API) do not match",
                DEFAULT_API_URL, Request.getServerApiUrl(DEFAULT_SERVER_ID));
    }

    @Test
    public void testErrorCases() {
        assertEquals("Server URLs do not match",
                DEFAULT_URL, Request.getServerUrl(Request.MIN - 1));

        assertEquals("Server URLs do not match",
                DEFAULT_URL, Request.getServerUrl(Request.MAX + 1));
    }

    @Test
    public void testServerUrlAndApiUrlGeneration() {
        final int randomServerId = TestHelper.randomInRange(Request.MIN, Request.MAX);
        final String serverUrl = Request.getServerUrl(randomServerId);
        final String apiUrl = Request.getServerApiUrl(randomServerId);

        assertTrue("API url should be longer than server url.",
                apiUrl.length() > serverUrl.length());

        assertTrue("API url should contain the server url.",
                apiUrl.contains(serverUrl));

        assertTrue("API url should start with the server url.",
                apiUrl.startsWith(serverUrl));

        assertTrue("API url should contain the api & version sub-endpoints.",
                apiUrl.endsWith(Request.API_SUB_ENDPOINT));
    }


}
