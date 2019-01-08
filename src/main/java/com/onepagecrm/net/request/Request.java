package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.ConnectivityException;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.exceptions.TimeoutException;
import com.onepagecrm.models.helpers.TextHelper;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.models.serializers.BaseSerializer;
import com.onepagecrm.net.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * Created by Cillian Myles on 08/01/2019.
 * Copyright (c) 2019 OnePageCRM. All rights reserved.
 */

@SuppressWarnings({"WeakerAccess", "MismatchedQueryAndUpdateOfCollection", "unused", "UnusedAssignment"})
public abstract class Request {

    protected static final Logger LOG = Logger.getLogger(Request.class.getName());

    public static final int AUTH_DEV_SERVER;
    public static final int AUTH_PROD_SERVER;
    public static final int APP_US_SERVER;
    public static final int APP_EU_SERVER;
    public static final int DEV_SERVER;
    public static final int STAGING_SERVER;
    public static final int ATLAS_SERVER;
    public static final int CALYPSO_SERVER;
    public static final int DEIMOS_SERVER;
    public static final int DRACO_SERVER;
    public static final int GANYMEDE_SERVER;
    public static final int GEMINI_SERVER;
    public static final int ORION_SERVER;
    public static final int PEGASUS_SERVER;
    public static final int PHOBOS_SERVER;
    public static final int SECURE_SERVER;
    public static final int SIRIUS_SERVER;
    public static final int TAURUS_SERVER;
    public static final int TITAN_SERVER;
    public static final int VIRGO_SERVER;
    public static final int VOYAGER_SERVER;
    public static final int LOCAL_DEV_SERVER;
    public static final int NETWORK_DEV_SERVER;
    public static final int MOCK_REQUEST_SERVER;
    public static final int CUSTOM_URL_SERVER;

    public static final int MIN;
    public static final int MAX;
    public static final int DEFAULT_AUTH_SERVER;
    public static final int UNRESOLVED_SERVER;

    static {
        int counter = -2;

        AUTH_DEV_SERVER = counter++;
        AUTH_PROD_SERVER = counter++;

        APP_US_SERVER = counter++; // 0 <- **want to always keep as default**
        APP_EU_SERVER = counter++;
        DEV_SERVER = counter++;
        STAGING_SERVER = counter++;
        ATLAS_SERVER = counter++;
        CALYPSO_SERVER = counter++;
        DEIMOS_SERVER = counter++;
        DRACO_SERVER = counter++;
        GANYMEDE_SERVER = counter++;
        GEMINI_SERVER = counter++;
        ORION_SERVER = counter++;
        PEGASUS_SERVER = counter++;
        PHOBOS_SERVER = counter++;
        SECURE_SERVER = counter++;
        SIRIUS_SERVER = counter++;
        TAURUS_SERVER = counter++;
        TITAN_SERVER = counter++;
        VIRGO_SERVER = counter++;
        VOYAGER_SERVER = counter++;

        LOCAL_DEV_SERVER = counter++;
        NETWORK_DEV_SERVER = counter++;
        MOCK_REQUEST_SERVER = counter++;
        CUSTOM_URL_SERVER = counter++;

        MIN = AUTH_DEV_SERVER;
        MAX = CUSTOM_URL_SERVER;

        DEFAULT_AUTH_SERVER = AUTH_PROD_SERVER;
        UNRESOLVED_SERVER = -99;
    }

    protected static final String AUTH_DEV_NAME = "AUTH/DEV";
    protected static final String AUTH_PROD_NAME = "AUTH/PROD";
    protected static final String APP_US_NAME = "APP/US";
    protected static final String APP_EU_NAME = "APP/EU";
    protected static final String DEV_NAME = "DEV";
    protected static final String STAGING_NAME = "STAGING";
    protected static final String ATLAS_NAME = "ATLAS";
    protected static final String CALYPSO_NAME = "CALYPSO";
    protected static final String DEIMOS_NAME = "DEIMOS";
    protected static final String GANYMEDE_NAME = "GANYMEDE";
    protected static final String DRACO_NAME = "DRACO";
    protected static final String GEMINI_NAME = "GEMINI";
    protected static final String ORION_NAME = "ORION";
    protected static final String PEGASUS_NAME = "PEGASUS";
    protected static final String PHOBOS_NAME = "PHOBOS";
    protected static final String SECURE_NAME = "SECURE";
    protected static final String SIRIUS_NAME = "SIRIUS";
    protected static final String TAURUS_NAME = "TAURUS";
    protected static final String TITAN_NAME = "TITAN";
    protected static final String VIRGO_NAME = "VIRGO";
    protected static final String VOYAGER_NAME = "VOYAGER";
    protected static final String LOCAL_DEV_NAME = "LOCAL";
    protected static final String NETWORK_DEV_NAME = "NETWORK";
    protected static final String CUSTOM_NAME = "CUSTOM";

    protected static final String AUTH_DEV_URL = "https://sso.dev.onepagecrm.com";
    protected static final String AUTH_PROD_URL = "https://secure.onepagecrm.com";
    protected static final String APP_US_URL = "https://app.onepagecrm.com";
    protected static final String APP_EU_URL = "https://eu.onepagecrm.com";
    protected static final String DEV_URL = "https://dev.onepagecrm.com";
    protected static final String STAGING_URL = "https://staging.onepagecrm.com";
    protected static final String ATLAS_URL = "https://atlas.dev.onepagecrm.com";
    protected static final String CALYPSO_URL = "https://calypso.dev.onepagecrm.com";
    protected static final String DEIMOS_URL = "https://deimos.dev.onepagecrm.com";
    protected static final String GANYMEDE_URL = "https://ganymede.dev.onepagecrm.com";
    protected static final String DRACO_URL = "https://draco.dev.onepagecrm.com";
    protected static final String GEMINI_URL = "https://gemini.dev.onepagecrm.com";
    protected static final String ORION_URL = "https://orion.dev.onepagecrm.com";
    protected static final String PEGASUS_URL = "https://pegasus.dev.onepagecrm.com";
    protected static final String PHOBOS_URL = "https://phobos.dev.onepagecrm.com";
    protected static final String SECURE_URL = "https://secure.dev.onepagecrm.com";
    protected static final String SIRIUS_URL = "https://sirius.dev.onepagecrm.com";
    protected static final String TAURUS_URL = "https://taurus.dev.onepagecrm.com";
    protected static final String TITAN_URL = "https://titan.dev.onepagecrm.com";
    protected static final String VIRGO_URL = "https://virgo.dev.onepagecrm.com";
    protected static final String VOYAGER_URL = "https://voyager.dev.onepagecrm.com";
    protected static String LOCAL_DEV_URL = "http://localhost:3000";
    protected static String NETWORK_DEV_URL = "http://10.100.0.15";
    protected static String CUSTOM_URL = "http://10.100.0.15";

    public static void setLocalDevUrl(String customUrl) {
        LOCAL_DEV_URL = customUrl;
        sServerUrlMap.put(LOCAL_DEV_SERVER, LOCAL_DEV_URL);
    }

    public static void setNetworkDevUrl(String customUrl) {
        NETWORK_DEV_URL = customUrl;
        sServerUrlMap.put(NETWORK_DEV_SERVER, NETWORK_DEV_URL);
    }

    public static void setCustomUrl(String customUrl) {
        CUSTOM_URL = customUrl;
        sServerUrlMap.put(CUSTOM_URL_SERVER, CUSTOM_URL);
    }

    private static final Map<Integer, String> sServerNameMap = new HashMap<>();

    static {
        sServerNameMap.put(AUTH_DEV_SERVER, AUTH_DEV_NAME);
        sServerNameMap.put(AUTH_PROD_SERVER, AUTH_PROD_NAME);
        sServerNameMap.put(APP_US_SERVER, APP_US_NAME);
        sServerNameMap.put(APP_EU_SERVER, APP_EU_NAME);
        sServerNameMap.put(DEV_SERVER, DEV_NAME);
        sServerNameMap.put(STAGING_SERVER, STAGING_NAME);
        sServerNameMap.put(ATLAS_SERVER, ATLAS_NAME);
        sServerNameMap.put(CALYPSO_SERVER, CALYPSO_NAME);
        sServerNameMap.put(DEIMOS_SERVER, DEIMOS_NAME);
        sServerNameMap.put(GANYMEDE_SERVER, GANYMEDE_NAME);
        sServerNameMap.put(DRACO_SERVER, DRACO_NAME);
        sServerNameMap.put(GEMINI_SERVER, GEMINI_NAME);
        sServerNameMap.put(ORION_SERVER, ORION_NAME);
        sServerNameMap.put(PEGASUS_SERVER, PEGASUS_NAME);
        sServerNameMap.put(PHOBOS_SERVER, PHOBOS_NAME);
        sServerNameMap.put(SECURE_SERVER, SECURE_NAME);
        sServerNameMap.put(SIRIUS_SERVER, SIRIUS_NAME);
        sServerNameMap.put(TAURUS_SERVER, TAURUS_NAME);
        sServerNameMap.put(TITAN_SERVER, TITAN_NAME);
        sServerNameMap.put(VIRGO_SERVER, VIRGO_NAME);
        sServerNameMap.put(VOYAGER_SERVER, VOYAGER_NAME);
        sServerNameMap.put(LOCAL_DEV_SERVER, LOCAL_DEV_NAME);
        sServerNameMap.put(NETWORK_DEV_SERVER, NETWORK_DEV_NAME);
        sServerNameMap.put(CUSTOM_URL_SERVER, CUSTOM_NAME);
    }

    private static final Map<Integer, String> sServerUrlMap = new HashMap<>();

    static {
        sServerUrlMap.put(AUTH_DEV_SERVER, AUTH_DEV_URL);
        sServerUrlMap.put(AUTH_PROD_SERVER, AUTH_PROD_URL);
        sServerUrlMap.put(APP_US_SERVER, APP_US_URL);
        sServerUrlMap.put(APP_EU_SERVER, APP_EU_URL);
        sServerUrlMap.put(DEV_SERVER, DEV_URL);
        sServerUrlMap.put(STAGING_SERVER, STAGING_URL);
        sServerUrlMap.put(ATLAS_SERVER, ATLAS_URL);
        sServerUrlMap.put(CALYPSO_SERVER, CALYPSO_URL);
        sServerUrlMap.put(DEIMOS_SERVER, DEIMOS_URL);
        sServerUrlMap.put(GANYMEDE_SERVER, GANYMEDE_URL);
        sServerUrlMap.put(DRACO_SERVER, DRACO_URL);
        sServerUrlMap.put(GEMINI_SERVER, GEMINI_URL);
        sServerUrlMap.put(ORION_SERVER, ORION_URL);
        sServerUrlMap.put(PEGASUS_SERVER, PEGASUS_URL);
        sServerUrlMap.put(PHOBOS_SERVER, PHOBOS_URL);
        sServerUrlMap.put(SECURE_SERVER, SECURE_URL);
        sServerUrlMap.put(SIRIUS_SERVER, SIRIUS_URL);
        sServerUrlMap.put(TAURUS_SERVER, TAURUS_URL);
        sServerUrlMap.put(TITAN_SERVER, TITAN_URL);
        sServerUrlMap.put(VIRGO_SERVER, VIRGO_URL);
        sServerUrlMap.put(VOYAGER_SERVER, VOYAGER_URL);
        sServerUrlMap.put(LOCAL_DEV_SERVER, LOCAL_DEV_URL);
        sServerUrlMap.put(NETWORK_DEV_SERVER, NETWORK_DEV_URL);
        sServerUrlMap.put(CUSTOM_URL_SERVER, CUSTOM_URL);
    }

    private static final Map<String, Integer> sNameServerMap = new HashMap<>();

    static {
        sNameServerMap.put(AUTH_DEV_NAME, AUTH_DEV_SERVER);
        sNameServerMap.put(AUTH_PROD_NAME, AUTH_PROD_SERVER);
        sNameServerMap.put(APP_US_NAME, APP_US_SERVER);
        sNameServerMap.put(APP_EU_NAME, APP_EU_SERVER);
        sNameServerMap.put(DEV_NAME, DEV_SERVER);
        sNameServerMap.put(STAGING_NAME, STAGING_SERVER);
        sNameServerMap.put(ATLAS_NAME, ATLAS_SERVER);
        sNameServerMap.put(CALYPSO_NAME, CALYPSO_SERVER);
        sNameServerMap.put(DEIMOS_NAME, DEIMOS_SERVER);
        sNameServerMap.put(GANYMEDE_NAME, GANYMEDE_SERVER);
        sNameServerMap.put(DRACO_NAME, DRACO_SERVER);
        sNameServerMap.put(GEMINI_NAME, GEMINI_SERVER);
        sNameServerMap.put(ORION_NAME, ORION_SERVER);
        sNameServerMap.put(PEGASUS_NAME, PEGASUS_SERVER);
        sNameServerMap.put(PHOBOS_NAME, PHOBOS_SERVER);
        sNameServerMap.put(SECURE_NAME, SECURE_SERVER);
        sNameServerMap.put(SIRIUS_NAME, SIRIUS_SERVER);
        sNameServerMap.put(TAURUS_NAME, TAURUS_SERVER);
        sNameServerMap.put(TITAN_NAME, TITAN_SERVER);
        sNameServerMap.put(VIRGO_NAME, VIRGO_SERVER);
        sNameServerMap.put(VOYAGER_NAME, VOYAGER_SERVER);
        sNameServerMap.put(LOCAL_DEV_NAME, LOCAL_DEV_SERVER);
        sNameServerMap.put(NETWORK_DEV_NAME, NETWORK_DEV_SERVER);
        sNameServerMap.put(CUSTOM_NAME, CUSTOM_URL_SERVER);
    }

    private static final Map<String, Integer> sUrlServerMap = new HashMap<>();

    static {
        sUrlServerMap.put(AUTH_DEV_URL, AUTH_DEV_SERVER);
        sUrlServerMap.put(AUTH_PROD_URL, AUTH_PROD_SERVER);
        sUrlServerMap.put(APP_US_URL, APP_US_SERVER);
        sUrlServerMap.put(APP_EU_URL, APP_EU_SERVER);
        sUrlServerMap.put(DEV_URL, DEV_SERVER);
        sUrlServerMap.put(STAGING_URL, STAGING_SERVER);
        sUrlServerMap.put(ATLAS_URL, ATLAS_SERVER);
        sUrlServerMap.put(CALYPSO_URL, CALYPSO_SERVER);
        sUrlServerMap.put(DEIMOS_URL, DEIMOS_SERVER);
        sUrlServerMap.put(GANYMEDE_URL, GANYMEDE_SERVER);
        sUrlServerMap.put(DRACO_URL, DRACO_SERVER);
        sUrlServerMap.put(GEMINI_URL, GEMINI_SERVER);
        sUrlServerMap.put(ORION_URL, ORION_SERVER);
        sUrlServerMap.put(PEGASUS_URL, PEGASUS_SERVER);
        sUrlServerMap.put(PHOBOS_URL, PHOBOS_SERVER);
        sUrlServerMap.put(SECURE_URL, SECURE_SERVER);
        sUrlServerMap.put(SIRIUS_URL, SIRIUS_SERVER);
        sUrlServerMap.put(TAURUS_URL, TAURUS_SERVER);
        sUrlServerMap.put(TITAN_URL, TITAN_SERVER);
        sUrlServerMap.put(VIRGO_URL, VIRGO_SERVER);
        sUrlServerMap.put(VOYAGER_URL, VOYAGER_SERVER);
        sUrlServerMap.put(LOCAL_DEV_URL, LOCAL_DEV_SERVER);
        sUrlServerMap.put(NETWORK_DEV_URL, NETWORK_DEV_SERVER);
        sUrlServerMap.put(CUSTOM_URL, CUSTOM_URL_SERVER);
    }

    public static int getServerIdFromName(String name) {
        return getServerIdFromName(name, APP_US_SERVER);
    }

    public static int getServerIdFromName(String name, int defaultServer) {
        final int safeDefault = sServerUrlMap.get(defaultServer) != null ? defaultServer : APP_US_SERVER;
        if (!notNullOrEmpty(name)) {
            return safeDefault;
        }
        final Integer matched = sNameServerMap.get(name);
        return matched != null ? matched : safeDefault;
    }

    public static int getServerIdFromUrl(String url) {
        return getServerIdFromUrl(url, APP_US_SERVER);
    }

    public static int getServerIdFromUrl(String url, int defaultServer) {
        // NOTE: we don't return "safe" default here as in other methods.
        if (!notNullOrEmpty(url)) {
            return defaultServer;
        }
        final Integer matched = sUrlServerMap.get(url);
        return matched != null ? matched : defaultServer;
    }

    public static boolean validServerId(int id) {
        return id >= MIN && id <= MAX;
    }

    public static String getServerName(int serverId) {
        return getServerName(serverId, APP_US_NAME);
    }

    public static String getServerName(int serverId, String defaultName) {
        final String safeDefault = sNameServerMap.get(defaultName) != null ? defaultName : APP_US_NAME;
        if (!validServerId(serverId)) {
            return safeDefault;
        }
        final String matched = sServerNameMap.get(serverId);
        return matched != null ? matched : safeDefault;
    }

    public static String getServerUrl(int serverId) {
        return getServerUrl(serverId, APP_US_URL);
    }

    public static String getServerUrl(int serverId, String defaultUrl) {
        final String safeDefault = sServerUrlMap.containsValue(defaultUrl) ? defaultUrl : APP_US_URL;
        if (!validServerId(serverId)) {
            return safeDefault;
        }
        final String matched = sServerUrlMap.get(serverId);
        return matched != null ? matched : safeDefault;
    }

    public static String getServerApiUrl(int serverId) {
        return getServerUrl(serverId) + "/" + API_SUB_ENDPOINT;
    }

    public static boolean isAppServer(int serverId) {
        return validServerId(serverId)
                && (serverId == APP_US_SERVER
                || serverId == APP_EU_SERVER);
    }

    public static boolean isDevServer(int serverId) {
        return validServerId(serverId)
                && (serverId == DEV_SERVER
                || serverId == STAGING_SERVER
                || serverId == ATLAS_SERVER
                || serverId == CALYPSO_SERVER
                || serverId == DEIMOS_SERVER
                || serverId == GANYMEDE_SERVER
                || serverId == DRACO_SERVER
                || serverId == GEMINI_SERVER
                || serverId == ORION_SERVER
                || serverId == PEGASUS_SERVER
                || serverId == PHOBOS_SERVER
                || serverId == SECURE_SERVER
                || serverId == SIRIUS_SERVER
                || serverId == TAURUS_SERVER
                || serverId == TITAN_SERVER
                || serverId == VIRGO_SERVER
                || serverId == VOYAGER_SERVER);
    }

    public static final String API_SUB_ENDPOINT = "api/v3";
    public static final String FORMAT_JSON = "json";

    protected String endpointUrl;

    protected enum Type {
        GET, POST, PUT, DELETE, PATCH
    }

    protected Type type = Type.GET; // default type GET

    protected static final String GET = "GET";
    protected static final String POST = "POST";
    protected static final String PUT = "PUT";
    protected static final String DELETE = "DELETE";
    protected static final String PATCH = "PATCH";

    protected String requestBody = "";
    protected Map<String, Object> params;
    protected Response response;

    private static final String ACCEPTS_TAG = "Accepts";
    private static final String ACCEPTS = "application/json";

    private static final String CONTENT_TYPE_TAG = "Content-Type";
    private static final String CONTENT_TYPE = "application/json";

    private static final String USER_AGENT_TAG = "User-Agent";

    protected static final String X_UID = "X-OnePageCRM-UID";
    protected static final String X_TS = "X-OnePageCRM-TS";
    protected static final String X_AUTH = "X-OnePageCRM-AUTH";
    protected static final String X_SOURCE = "X-OnePageCRM-SOURCE";
    protected static final String X_APP_VERSION = "X-OnePageCRM-APP-VERSION";

    protected static final String AUTHORIZATION = "Authorization";

    public static final int DEFAULT_TIME_OUT_MS = 10000; // 10 seconds

    protected HttpURLConnection connection;

    public abstract void setType();

    public void setEndpointUrl(String endpoint) {
        setEndpointUrl(endpoint, false);
    }

    public void setEndpointUrl(String endpoint, boolean externalEndpoint) {
        if (externalEndpoint) {
            endpointUrl = endpoint;
        } else {
            endpointUrl = getServerApiUrl(OnePageCRM.SERVER) + "/" + endpoint + "." + FORMAT_JSON;
        }
    }

    /**
     * Method require to send HTTP request.
     *
     * @return response
     */
    public Response send() throws OnePageException {
        boolean mockingRequest = (OnePageCRM.SERVER == MOCK_REQUEST_SERVER);
        if (!mockingRequest) {
            setupAndConnect();
            setRequestMethod();
            setRequestBody();
            setRequestHeaders();
            writeRequestBody();
            getResponse();
            connection.disconnect();
            return response;
        } else {
            return mockRequest();
        }
    }

    private Response mockRequest() {
        Response mockResponse = new Response(0, "OK", "MOCKED REQUEST RESPONSE!");
        LOG.info(Utilities.repeatedString("*", 40));
        LOG.info("--- REQUEST ---");
        LOG.info("Type: " + type);
        LOG.info("Url: " + getUrl(this.endpointUrl));
        setRequestBody();
        LOG.info("Body: " + requestBody);
        LOG.info("--- RESPONSE ---");
        LOG.info("Code: " + mockResponse.getResponseCode());
        LOG.info("Message: " + mockResponse.getResponseMessage());
        LOG.info("Body: " + mockResponse.getResponseBody());
        LOG.info(Utilities.repeatedString("*", 40));
        return mockResponse;
    }

    /**
     * Connect to Url using HttpURLConnection class.
     */
    private void setupAndConnect() throws OnePageException {
        URL url = getUrl(this.endpointUrl);
        connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(DEFAULT_TIME_OUT_MS);
            HttpURLConnection.setFollowRedirects(true);
        } catch (java.net.SocketTimeoutException e) {
            String message = "Request timed out after " + (DEFAULT_TIME_OUT_MS / 1000) + " seconds";
            LOG.severe(message);
            LOG.severe(e.toString());
            throw new TimeoutException()
                    .setTimeMs(DEFAULT_TIME_OUT_MS)
                    .setMessage(message)
                    .setErrorName(message);
        } catch (IOException e) {
            LOG.severe("Error connecting to url : " + url);
            LOG.severe(e.toString());
        }
    }

    /**
     * Convert String to Url object.
     *
     * @param url of request
     * @return formed {@link URL url}.
     */
    private URL getUrl(String url) {
        URL requestUrl = null;
        try {
            requestUrl = new URL(url);
        } catch (MalformedURLException e) {
            LOG.severe("Error forming url for request");
            LOG.severe(e.toString());
        }
        return requestUrl;
    }

    /**
     * Set HTTP request method e.g. GET, POST etc.
     */
    private void setRequestMethod() {
        switch (type) {
            case GET:
                try {
                    connection.setRequestMethod(GET);
                } catch (ProtocolException e) {
                    LOG.severe("Could not set request as GET successfully");
                    LOG.severe(e.toString());
                }
                break;
            case POST:
                try {
                    connection.setDoOutput(true);
                    connection.setRequestMethod(POST);
                } catch (ProtocolException e) {
                    LOG.severe("Could not set request as POST successfully");
                    LOG.severe(e.toString());
                }
                break;
            case PUT:
                try {
                    connection.setDoOutput(true);
                    connection.setRequestMethod(PUT);
                } catch (ProtocolException e) {
                    LOG.severe("Could not set request as PUT successfully");
                    LOG.severe(e.toString());
                }
                break;
            case DELETE:
                try {
                    connection.setRequestMethod(DELETE);
                } catch (ProtocolException e) {
                    LOG.severe("Could not set request as DELETE successfully");
                    LOG.severe(e.toString());
                }
                break;
            case PATCH:
                try {
                    connection.setDoOutput(true);
                    connection.setRequestMethod(PATCH);
                } catch (ProtocolException e) {
                    LOG.severe("Could not set request as PATCH successfully");
                    LOG.severe(e.toString());
                }
                break;
        }
    }

    /**
     * Define the headers for the request. This method will be overridden in
     * SignedRequest to include auth headers.
     */
    public void setRequestHeaders() {
        final String userAgent = getUserAgent();
        final String appVersion = OnePageCRM.APP_VERSION;
        connection.setRequestProperty(ACCEPTS_TAG, ACCEPTS);
        connection.setRequestProperty(CONTENT_TYPE_TAG, CONTENT_TYPE);
        connection.setRequestProperty(X_SOURCE, OnePageCRM.SOURCE);
        connection.setRequestProperty(X_APP_VERSION, appVersion);
        connection.setRequestProperty(USER_AGENT_TAG, userAgent);

        LOG.info(Utilities.repeatedString("*", 40));
        LOG.info("--- REQUEST ---");
        LOG.info("Type: " + connection.getRequestMethod());
        LOG.info("Url: " + connection.getURL());
        LOG.info("Body: " + requestBody);
    }

    private String getUserAgent() {
        final String prop = System.getProperty("http.agent");
        return !TextHelper.isEmpty(prop) ? prop : "Java/1.8";
    }

    protected void setRequestBody() {
        if (this.requestBody.equals("")) {
            this.requestBody = BaseSerializer.encodeParams(params);
        }
    }

    /**
     * Actually write the request body using the OutputStreamWriter.
     */
    private void writeRequestBody() {
        if (requestBody != null && !requestBody.equals("")) {
            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(connection.getOutputStream());
            } catch (IOException e) {
                LOG.severe("Could not open output stream to write request body");
                LOG.severe(e.toString());
            }
            if (out != null) {
                try {
                    out.write(requestBody);
                } catch (IOException e) {
                    LOG.severe("Could not write request body");
                    LOG.severe(e.toString());
                }
                try {
                    out.flush();
                } catch (IOException e) {
                    LOG.severe("Could not flush output stream for writing request body");
                    LOG.severe(e.toString());
                }
            }
        }
    }

    /**
     * Acquire the HTTP response code, message and body.
     */
    private void getResponse() throws OnePageException {
        response = new Response();

        getResponseCode();
        getResponseMessage();
        getResponseBody();

        LOG.info("--- HEADERS ---");
        printHeaders();

        LOG.info("--- RESPONSE ---");
        LOG.info("Code: " + response.getResponseCode());
        LOG.info("Message: " + response.getResponseMessage());
        LOG.info("Body: " + response.getResponseBody());
        LOG.info(Utilities.repeatedString("*", 40));
    }

    protected void printHeaders() {
        printHeader(ACCEPTS_TAG);
        printHeader(CONTENT_TYPE_TAG);
        printHeader(X_SOURCE);
        printHeader(X_APP_VERSION);
        printHeader(USER_AGENT_TAG);
    }

    protected void printHeader(final String tag) {
        printValue(tag, connection.getRequestProperty(tag));
    }

    protected void printValue(final String tag, final String value) {
        LOG.info(tag + ": " + value);
    }

    private void getResponseCode() throws OnePageException {
        try {
            response.setResponseCode(connection.getResponseCode());
        } catch (UnknownHostException e) {
            throw new ConnectivityException();
        } catch (IOException e) {
            LOG.severe("Could not get response code");
            LOG.severe(e.toString());
        }
    }

    private void getResponseMessage() {
        try {
            response.setResponseMessage(connection.getResponseMessage());
        } catch (IOException e) {
            LOG.severe("Could not get response message");
            LOG.severe(e.toString());
        }
    }

    private void getResponseBody() {
        switch (type) {
            case GET:
                response.setResponseBody(getGetResponseBody());
                break;
            case POST:
                response.setResponseBody(getPostResponseBody());
                break;
            case PUT:
            case PATCH:
            case DELETE:
                response.setResponseBody(getGetResponseBody());
                break;
        }
    }

    private String getGetResponseBody() {
        Scanner scan = null;
        StringBuilder responseBody = new StringBuilder();
        if (response.getResponseCode() < 300) {
            try {
                scan = new Scanner(connection.getInputStream());
            } catch (IOException e) {
                LOG.severe("Could not get response body");
                LOG.severe(e.toString());
            }
        } else {
            scan = new Scanner(connection.getErrorStream());
        }

        if (scan != null) {
            while (scan.hasNext()) {
                responseBody.append(scan.nextLine());
            }
            scan.close();
        }
        return responseBody.toString();
    }

    private String getPostResponseBody() {
        BufferedReader br = null;
        StringBuilder responseBody = new StringBuilder();
        if (response.getResponseCode() < 300) {
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                LOG.severe("Could not open input stream to get response body of POST request");
                LOG.severe(e.toString());
            }
        } else {
            br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        String output;
        if (br != null) {
            try {
                while ((output = br.readLine()) != null) {
                    responseBody.append(output);
                }
            } catch (IOException e) {
                LOG.severe("Could not read line using buffered reader");
                LOG.severe(e.toString());
            }
            try {
                br.close();
            } catch (IOException e) {
                LOG.severe("Could not close buffered reader");
                LOG.severe(e.toString());
            }
        }
        return responseBody.toString();
    }
}
