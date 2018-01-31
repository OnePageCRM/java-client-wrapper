package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.exceptions.S3Exception;
import com.onepagecrm.models.internal.FileReference;
import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.models.internal.S3Data;
import com.onepagecrm.models.internal.S3FileReference;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.models.serializers.S3FileReferenceSerializer;
import com.onepagecrm.net.request.Request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 27/09/2017.
 */
@SuppressWarnings({"PointlessArithmeticExpression", "WeakerAccess"})
public class MultipartUpload {

    private static final Logger LOG = Logger.getLogger(MultipartUpload.class.getSimpleName());

    private static final String DEFAULT_USER_AGENT = "Java/1.7.0_80";
    private static final String TWO_HYPHENS = "--";
    private static final String LINE_END = "\r\n";

    /**
     * Upload a file to a server using multi-part upload.
     */
    public static S3FileReference perform(S3Data data, Map<String, String> params, String filePath) throws OnePageException {
        return perform(data.getUrl(), params, new FileReference(filePath), null);
    }

    /**
     * Upload a file to a server using multi-part upload.
     */
    public static S3FileReference perform(S3Data data, Map<String, String> params, String filePath, InputStream uploadStream) throws OnePageException {
        return perform(data.getUrl(), params, new FileReference(filePath), uploadStream);
    }

    /**
     * Upload a file to a server using multi-part upload.
     */
    public static S3FileReference perform(S3Data data, Map<String, String> params, FileReference fileRef) throws OnePageException {
        return perform(data.getUrl(), params, fileRef, null);
    }

    /**
     * Upload a file to a server using multi-part upload.
     */
    public static S3FileReference perform(S3Data data, Map<String, String> params, FileReference fileRef, InputStream uploadStream) throws OnePageException {
        return perform(data.getUrl(), params, fileRef, uploadStream);
    }

    /**
     * Upload a file to a server using multi-part upload.
     */
    public static S3FileReference perform(String urlTo, Map<String, String> params, FileReference fileRef, InputStream uploadStream) throws OnePageException {
        if (!Utilities.notNullOrEmpty(urlTo) || fileRef == null) {
            return null;
        }

        S3FileReference createdFileRef = new S3FileReference(fileRef);
        params = params != null ? params : new HashMap<String, String>();
        OnePageException toBeThrown = null;

        HttpURLConnection connection;
        DataOutputStream requestStream;
        InputStream responseStream;

        String filePath = fileRef.getPath();
        String fileName = fileRef.getName();
        String mimeType = fileRef.getMimeType();
        long fileSize = fileRef.getSize();

        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";

        // -------------------------------
        if (OnePageCRM.DEBUG) {
            LOG.info("url: " + urlTo);
            LOG.info("path: " + filePath);
            LOG.info("name: " + fileName);
            LOG.info("mime: " + mimeType);
            LOG.info("size: " + fileSize);
            LOG.info("params: " + params);
        }
        // -------------------------------

        try {
            URL url = new URL(urlTo);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Request.DEFAULT_TIME_OUT_MS);
            HttpURLConnection.setFollowRedirects(true);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String userAgentProp = System.getProperty("http.agent");
            String userAgent = Utilities.notNullOrEmpty(userAgentProp) ? userAgentProp : DEFAULT_USER_AGENT;

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // -------------------------------
            LOG.info(Utilities.repeatedString("*", 40));
            LOG.info("--- REQUEST ---");
            LOG.info("Type: " + connection.getRequestMethod());
            LOG.info("Url: " + connection.getURL());
            LOG.info("User-Agent: " + userAgent);
            // -------------------------------

            requestStream = new DataOutputStream(connection.getOutputStream());
            requestStream.writeBytes(TWO_HYPHENS + boundary + LINE_END);

            // Upload POST Data
            for (String key : params.keySet()) {
                String value = params.get(key);
                LOG.info(key + ": " + value);

                requestStream.writeBytes(TWO_HYPHENS + boundary + LINE_END);
                requestStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
                requestStream.writeBytes("Content-Type: text/plain" + LINE_END);
                requestStream.writeBytes(LINE_END);
                requestStream.writeBytes(value);
                requestStream.writeBytes(LINE_END);
            }

            requestStream.writeBytes(LINE_END);

            requestStream.writeBytes(TWO_HYPHENS + boundary + LINE_END);
            requestStream.writeBytes("Content-Disposition: form-data; name=\"" + "file" + "\"; filename=\"" + fileName + "\"" + LINE_END);
            requestStream.writeBytes("Content-Type: " + mimeType + LINE_END);
            requestStream.writeBytes("Content-Transfer-Encoding: binary" + LINE_END);
            requestStream.writeBytes(LINE_END);

            // Read from file and write to server (via DataOutputStream).
            if (uploadStream == null) {
                File localFile = new File(filePath);
                fileSize = localFile.length();
                uploadStream = new FileInputStream(localFile);
            }
            if (fileSize > S3FileReference.MAX_SIZE_BYTES) {
                final String message = S3Exception.tooLargeMessage();
                toBeThrown = new S3Exception(message).setErrorMessage(message);
                throw toBeThrown;
            }
            createdFileRef.setSize(fileSize);
            FileUtilities.copy(uploadStream, requestStream, false);
            requestStream.writeBytes(LINE_END);

            // Finish communication with server.
            requestStream.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + LINE_END);

            if (connection.getResponseCode() >= 400) {
                responseStream = connection.getErrorStream();
            } else {
                responseStream = connection.getInputStream();
            }
            String result = convertStreamToString(responseStream);

            // -------------------------------
            LOG.info("--- RESPONSE ---");
            LOG.info("Code: " + connection.getResponseCode());
            LOG.info("Message: " + connection.getResponseMessage());
            LOG.info("Body: " + result);
            LOG.info(Utilities.repeatedString("*", 40));
            // -------------------------------

            // Parse XML response and add to S3FileRef data.
            try {
                S3FileReference parsed = S3FileReferenceSerializer.fromString(result);
                createdFileRef.setLocation(parsed.getLocation());
                createdFileRef.setBucket(parsed.getBucket());
                createdFileRef.setKey(parsed.getKey());
                createdFileRef.setEtag(parsed.getEtag());

            } catch (OnePageException e) {
                toBeThrown = e;
            }

            // Finish / tidy up.
            uploadStream.close();
            responseStream.close();
            requestStream.flush();
            requestStream.close();

        } catch (Exception e) {
            LOG.severe("Error in multi-part upload");
            LOG.severe(e.toString());
            e.printStackTrace();
        }

        if (toBeThrown != null) {
            throw toBeThrown;
        }

        return createdFileRef;
    }

    private static String convertStreamToString(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            is.close();
            return sb.toString();

        } catch (IOException e) {
            LOG.severe("Error reading from response stream.");
            LOG.severe(e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
