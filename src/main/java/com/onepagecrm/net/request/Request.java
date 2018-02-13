package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.exceptions.TimeoutException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

@SuppressWarnings({"WeakerAccess", "MismatchedQueryAndUpdateOfCollection", "unused"})
public abstract class Request {

    protected static final Logger LOG = Logger.getLogger(Request.class.getName());

    public static final int AUTH_SERVER = -1;
    public static final int APP_SERVER = 0;
    public static final int DEV_SERVER = 1;
    public static final int STAGING_SERVER = 2;
    public static final int ATLAS_SERVER = 3;
    public static final int CALYPSO_SERVER = 4;
    public static final int DEIMOS_SERVER = 5;
    public static final int DRACO_SERVER = 6;
    public static final int GANYMEDE_SERVER = 7;
    public static final int GEMINI_SERVER = 8;
    public static final int ORION_SERVER = 9;
    public static final int PEGASUS_SERVER = 10;
    public static final int PHOBOS_SERVER = 11;
    public static final int SECURE_SERVER = 12;
    public static final int SIRIUS_SERVER = 13;
    public static final int TAURUS_SERVER = 14;
    public static final int TITAN_SERVER = 15;
    public static final int VIRGO_SERVER = 16;
    public static final int VOYAGER_SERVER = 17;
    public static final int LOCAL_DEV_SERVER = 20;
    public static final int NETWORK_DEV_SERVER = 21;
    public static final int MOCK_REQUEST_SERVER = 22;
    public static final int CUSTOM_URL_SERVER = 23;

    protected static final String AUTH_NAME = "AUTH";
    protected static final String APP_NAME = "APP";
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

    protected static final String AUTH_URL = "http://auth.mse.onepagecrm.eu/api/v3/"; // TODO: update URL before going live
    protected static final String APP_URL = "https://app.onepagecrm.com/api/v3/";
    protected static final String DEV_URL = "http://dev.onepagecrm.com/api/v3/";
    protected static final String STAGING_URL = "http://staging.onepagecrm.com/api/v3/";
    protected static final String ATLAS_URL = "http://atlas.dev.onepagecrm.com/api/v3/";
    protected static final String CALYPSO_URL = "http://calypso.dev.onepagecrm.com/api/v3/";
    protected static final String DEIMOS_URL = "http://deimos.dev.onepagecrm.com/api/v3/";
    protected static final String GANYMEDE_URL = "http://ganymede.dev.onepagecrm.com/api/v3/";
    protected static final String DRACO_URL = "http://draco.dev.onepagecrm.com/api/v3/";
    protected static final String GEMINI_URL = "http://gemini.dev.onepagecrm.com/api/v3/";
    protected static final String ORION_URL = "http://orion.dev.onepagecrm.com/api/v3/";
    protected static final String PEGASUS_URL = "http://pegasus.dev.onepagecrm.com/api/v3/";
    protected static final String PHOBOS_URL = "http://phobos.dev.onepagecrm.com/api/v3/";
    protected static final String SECURE_URL = "https://secure.dev.onepagecrm.com/api/v3/";
    protected static final String SIRIUS_URL = "http://sirius.dev.onepagecrm.com/api/v3/";
    protected static final String TAURUS_URL = "http://taurus.dev.onepagecrm.com/api/v3/";
    protected static final String TITAN_URL = "http://titan.dev.onepagecrm.com/api/v3/";
    protected static final String VIRGO_URL = "http://virgo.dev.onepagecrm.com/api/v3/";
    protected static final String VOYAGER_URL = "http://voyager.dev.onepagecrm.com/api/v3/";
    protected static String LOCAL_DEV_URL = "http://localhost:3000/api/v3/";
    protected static String NETWORK_DEV_URL = "http://10.100.0.15/api/v3/";
    protected static String CUSTOM_URL = "http://10.100.0.15/api/v3/";

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
        sServerNameMap.put(AUTH_SERVER, AUTH_NAME);
        sServerNameMap.put(APP_SERVER, APP_NAME);
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
        sServerUrlMap.put(AUTH_SERVER, AUTH_URL);
        sServerUrlMap.put(APP_SERVER, APP_URL);
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
        sNameServerMap.put(AUTH_NAME, AUTH_SERVER);
        sNameServerMap.put(APP_NAME, APP_SERVER);
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

    public static int getServerId(String name) {
        return getServerId(name, APP_SERVER);
    }

    public static int getServerId(String name, int defaultServer) {
        final int safeDefault = sServerUrlMap.get(defaultServer) != null ? defaultServer : APP_SERVER;
        if (!notNullOrEmpty(name)) {
            return safeDefault;
        }
        final Integer matched = sNameServerMap.get(name);
        return matched != null ? matched : safeDefault;
    }

    public static boolean validServerId(int id) {
        return sServerUrlMap.get(id) != null;
    }

    public static String getServerName(int serverId) {
        return getServerName(serverId, APP_NAME);
    }

    public static String getServerName(int serverId, String defaultName) {
        final String safeDefault = sNameServerMap.get(defaultName) != null ? defaultName : APP_NAME;
        if (serverId < AUTH_SERVER || serverId > CUSTOM_URL_SERVER) {
            return safeDefault;
        }
        final String matched = sServerNameMap.get(serverId);
        return matched != null ? matched : safeDefault;
    }

    public static String getServerUrl(int serverId) {
        return getServerUrl(serverId, APP_URL);
    }

    public static String getServerUrl(int serverId, String defaultUrl) {
        final String safeDefault = sServerUrlMap.containsValue(defaultUrl) ? defaultUrl : APP_URL;
        if (serverId < AUTH_SERVER || serverId > CUSTOM_URL_SERVER) {
            return safeDefault;
        }
        final String matched = sServerUrlMap.get(serverId);
        return matched != null ? matched : safeDefault;
    }

    public static String format = ".json";
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
            endpointUrl = sServerUrlMap.get(OnePageCRM.SERVER) + endpoint + format;
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
        String userAgent = System.getProperty("http.agent");
        connection.setRequestProperty(ACCEPTS_TAG, ACCEPTS);
        connection.setRequestProperty(USER_AGENT_TAG, userAgent);
        connection.setRequestProperty(CONTENT_TYPE_TAG, CONTENT_TYPE);

        LOG.info(Utilities.repeatedString("*", 40));
        LOG.info("--- REQUEST ---");
        LOG.info("Type: " + connection.getRequestMethod());
        LOG.info("Url: " + connection.getURL());
        LOG.info("Body: " + requestBody);
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
    private void getResponse() {
        response = new Response();

        getResponseCode();
        getResponseMessage();
        getResponseBody();

        LOG.info("User-Agent: " + connection.getRequestProperty(USER_AGENT_TAG));
        LOG.info("--- RESPONSE ---");
        LOG.info("Code: " + response.getResponseCode());
        LOG.info("Message: " + response.getResponseMessage());
        LOG.info("Body: " + response.getResponseBody());
        LOG.info(Utilities.repeatedString("*", 40));
    }

    private void getResponseCode() {
        try {
            response.setResponseCode(connection.getResponseCode());
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
