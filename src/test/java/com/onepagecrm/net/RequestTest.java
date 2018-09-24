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
@SuppressWarnings("unused")
public class RequestTest {

    // App Server details (APP/US, STAGING, APP/EU)
    private static final int APP_SERVER_DEFAULT = 0;
    private static final String APP_URL_DEFAULT = "https://app.onepagecrm.com";
    private static final String API_URL_DEFAULT = "https://app.onepagecrm.com/api/v3";
    private static final String APP_NAME_DEFAULT = "APP/US";

    private static final String APP_URL_STAGING = "https://staging.onepagecrm.com";
    private static final String API_URL_STAGING = "https://staging.onepagecrm.com/api/v3";
    private static final String APP_NAME_STAGING = "STAGING";

    private static final String APP_URL_EU = "https://eu.onepagecrm.com";
    private static final String API_URL_EU = "https://eu.onepagecrm.com/api/v3";
    private static final String APP_NAME_EU = "APP/EU";

    // Authentication/SSO details (dev/prod)
    private static final int AUTH_SERVER_DEV = -2;
    private static final int AUTH_SERVER_PROD = -1;
    private static final String AUTH_URL_DEV = "https://sso.dev.onepagecrm.com";
    private static final String AUTH_URL_PROD = "https://secure.onepagecrm.com";

    @Test
    public void serverIdMatchingByUrl_appUsServer() {
        assertEquals("Server id should be that of APP/US",
                Request.APP_US_SERVER, Request.getServerIdFromUrl(APP_URL_DEFAULT));
    }

    @Test
    public void serverIdMatchingByName_appUsServer() {
        assertEquals("Server id should be that of APP/US",
                Request.APP_US_SERVER, Request.getServerIdFromName(APP_NAME_DEFAULT));
    }

    @Test
    public void serverIdMatchingByUrl_devServer() {
        assertEquals("Server id should be that of STAGING",
                Request.STAGING_SERVER, Request.getServerIdFromUrl(APP_URL_STAGING));
    }

    @Test
    public void serverIdMatchingByName_devServer() {
        assertEquals("Server id should be that of STAGING",
                Request.STAGING_SERVER, Request.getServerIdFromName(APP_NAME_STAGING));
    }

    @Test
    public void serverIdMatchingByUrl_appEuServer() {
        assertEquals("Server id should be that of APP/EU",
                Request.APP_EU_SERVER, Request.getServerIdFromUrl(APP_URL_EU));
    }

    @Test
    public void serverIdMatchingByName_appEuServer() {
        assertEquals("Server id should be that of APP/EU",
                Request.APP_EU_SERVER, Request.getServerIdFromName(APP_NAME_EU));
    }

    @Test
    public void urlMatchingErrorCases_belowMin() {
        assertEquals("Server id less than MIN should default to APP/US",
                APP_URL_DEFAULT, Request.getServerUrl(Request.MIN - 1));
    }

    @Test
    public void urlMatchingErrorCases_aboveMax() {
        assertEquals("Server id more than MAX should default to APP/US",
                APP_URL_DEFAULT, Request.getServerUrl(Request.MAX + 1));
    }

    @Test
    public void testAppAndApiUrlGeneration_min() {
        testAppAndApiUrlGen(Request.MIN);
    }

    @Test
    public void testAppAndApiUrlGeneration_max() {
        testAppAndApiUrlGen(Request.MAX);
    }

    @Test
    public void testAppAndApiUrlGeneration_randomInBetween() {
        final int randomInBetween = TestHelper.randomInRange((Request.MIN + 1), (Request.MAX - 1));
        testAppAndApiUrlGen(randomInBetween);
    }

    private void testAppAndApiUrlGen(int serverId) {
        final String appUrl = Request.getServerUrl(serverId);
        final String apiUrl = Request.getServerApiUrl(serverId);

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
    public void testMobileRequirements_defaultAppUrl() {
        assertEquals("Default App urls do not match",
                APP_URL_DEFAULT, Request.getServerUrl(APP_SERVER_DEFAULT));
    }

    @Test
    public void testMobileRequirements_defaultApiUrl() {
        assertEquals("Default API urls do not match",
                API_URL_DEFAULT, Request.getServerApiUrl(APP_SERVER_DEFAULT));
    }

    @Test
    public void testMobileRequirements_devAuthUrl() {
        assertEquals("Auth urls do not match",
                AUTH_URL_DEV, Request.getServerUrl(AUTH_SERVER_DEV));
    }

    @Test
    public void testMobileRequirements_prodAuthUrl() {
        assertEquals("Auth urls do not match",
                AUTH_URL_PROD, Request.getServerUrl(AUTH_SERVER_PROD));
    }
}
