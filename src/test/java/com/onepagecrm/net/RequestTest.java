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

    // Production (APP/US)
    private static final int APP_SERVER_DEFAULT = 0;
    private static final String APP_URL_DEFAULT = "https://app.onepagecrm.com";
    private static final String API_URL_DEFAULT = "https://app.onepagecrm.com/api/v3";

    // Authentication (SSO)
    private static final int AUTH_SERVER_DEV = -2;
    private static final int AUTH_SERVER_PROD = -1;
    private static final String AUTH_URL_DEV = "http://sso.dev.onepagecrm.com";
    private static final String AUTH_URL_PROD = "https://secure.onepagecrm.com";

    @Test
    public void appUsServerIsDefault() {
        assertEquals("Server URLs do not match",
                APP_URL_DEFAULT, Request.getServerUrl(APP_SERVER_DEFAULT));

        assertEquals("Server URLs (API) do not match",
                API_URL_DEFAULT, Request.getServerApiUrl(APP_SERVER_DEFAULT));
    }

    @Test
    public void testErrorCases() {
        assertEquals("Server id less than MIN should default to APP/US",
                APP_URL_DEFAULT, Request.getServerUrl(Request.MIN - 1));

        assertEquals("Server id more than MAX should default to APP/US",
                APP_URL_DEFAULT, Request.getServerUrl(Request.MAX + 1));
    }

    @Test
    public void testAppAndApiUrlGeneration() {
        final int randomServerId = TestHelper.randomInRange(Request.MIN, Request.MAX);
        final String appUrl = Request.getServerUrl(randomServerId);
        final String apiUrl = Request.getServerApiUrl(randomServerId);

        assertTrue("API url should be longer than APP url.",
                apiUrl.length() > appUrl.length());

        assertTrue("API url should contain the APP url.",
                apiUrl.contains(appUrl));

        assertTrue("API url should start with the APP url.",
                apiUrl.startsWith(appUrl));

        assertTrue("API url should contain the API & version sub-endpoints.",
                apiUrl.endsWith(Request.API_SUB_ENDPOINT));
    }

    @Test
    public void testMobileRequirements_devAuthUrl() {
        assertEquals("Server URLs do not match",
                AUTH_URL_DEV, Request.getServerUrl(AUTH_SERVER_DEV));
    }

    @Test
    public void testMobileRequirements_prodAuthUrl() {
        assertEquals("Server URLs do not match",
                AUTH_URL_PROD, Request.getServerUrl(AUTH_SERVER_PROD));
    }
}
