package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Utilities;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OnePageAuthData extends AuthData {

    private static final Logger LOG = Logger.getLogger(OnePageAuthData.class.getName());

    private int timestamp;
    private String type;
    private String url;
    private String body;

    /**
     * Constructor which will only be used for login (no user details known
     * yet).
     *
     * @param type
     * @param url
     * @param body
     */
    public OnePageAuthData(String type, String url, String body) {
        super(null, null);
        this.type = type;
        this.url = url;
        this.body = body;
        // No need to calculate signature here (login)!
    }

    /**
     * Constructor which will be used for every API other than logging in.
     *
     * @param user
     * @param type
     * @param url
     * @param body
     */
    public OnePageAuthData(User user, String type, String url, String body) {
        super(user.getId(), user.getAuthKey());
        this.timestamp = getUnixTime();
        this.type = type;
        this.url = url;
        this.body = body;
        updateSignature();
    }

    /**
     * Constructor used for testing to manually enter/fabricate timestamp.
     *
     * @param user
     * @param type
     * @param url
     * @param body
     */
    public OnePageAuthData(User user, int timestamp, String type, String url, String body) {
        super(user.getId(), user.getAuthKey());
        this.timestamp = timestamp;
        this.type = type;
        this.url = url;
        this.body = body;
        updateSignature();
    }

    /**
     * Method which handles the process of calculating auth data i.e.
     * X-OnePageCRM-Auth.
     *
     * @return
     */
    public String calculateSignature() {
        if (OnePageCRM.DEBUG) {
            LOG.info("*************************************");
            LOG.info("--- AUTHENTICATION ---");
        }
        final String thisUserId = getUserId() != null ? getUserId() : "";
        final String thisApiKey = getApiKey() != null ? getApiKey() : "";
        final int thisTimestamp = getTimestamp() != 0 ? getTimestamp() : getUnixTime();
        final String thisType = getType() != null ? getType() : "";
        final String thisUrl = getUrl() != null ? getUrl() : "";
        final String thisBody = getBody() != null ? getBody() : "";

        byte[] decodedApiKey = new byte[0];
        try {
            if (Utilities.notNullOrEmpty(thisApiKey)) {
                decodedApiKey = Base64.decodeBase64(thisApiKey.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            LOG.severe("Error decoding the ApiKey");
            LOG.severe(e.toString());
        }
        String urlHash = convertStringToSha1Hash(thisUrl);
        if (OnePageCRM.DEBUG) {
            LOG.info("URL=" + thisUrl);
            LOG.info("hash(URL)=" + urlHash);
        }
        String signature = thisUserId + "." + thisTimestamp + "." + thisType.toUpperCase() + "." + urlHash;
        if (thisType.equals("POST") || thisType.equals("PUT")) {
            if (thisBody != null) {
                String bodyHash = convertStringToSha1Hash(thisBody);
                signature += "." + bodyHash;
                if (OnePageCRM.DEBUG) {
                    LOG.info("BODY=" + thisBody);
                    LOG.info("hash(BODY)=" + bodyHash);
                }
            }
        }
        if (OnePageCRM.DEBUG) {
            LOG.info("Signature=" + signature);
        }
        return makeHMACSHA256Signature(decodedApiKey, signature);
    }

    /**
     * Acquires the SHA-1 hash of a given String.
     *
     * @param toBeHashed
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    private String convertStringToSha1Hash(String toBeHashed) {
        if (!Utilities.notNullOrEmpty(toBeHashed)) {
            return "";
        }
        MessageDigest encoder = null;
        try {
            encoder = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            LOG.severe("Could not use SHA1 hashing algorithm");
            LOG.severe(e.toString());
        }
        byte[] urlBuffer = toBeHashed.getBytes(Charset.forName("UTF-8"));
        encoder.reset();
        encoder.update(urlBuffer);
        return new String(Hex.encodeHex(encoder.digest()));
    }

    /**
     * Delivers the final signature i.e. X-OnePageCRM-Auth.
     *
     * @param apiKey
     * @param signature
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    private String makeHMACSHA256Signature(byte[] apiKey, String signature) {
        if (apiKey == null || apiKey.length == 0 || !Utilities.notNullOrEmpty(signature)) {
            return "";
        }
        byte[] signatureBuffer = signature.getBytes(Charset.forName("UTF-8"));
        SecretKey secretKey = new SecretKeySpec(apiKey, "HMACSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HMACSHA256");
        } catch (NoSuchAlgorithmException e) {
            LOG.severe("Could not use SHA256 hashing algorithm");
            LOG.severe(e.toString());
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            LOG.severe("Error decoding the ApiKey");
            LOG.severe(e.toString());
        }
        String sha256Hash = new String(Hex.encodeHex(mac.doFinal(signatureBuffer)));
        if (OnePageCRM.DEBUG) {
            LOG.info("hash(Signature)=" + sha256Hash);
        }
        return sha256Hash;
    }

    /**
     * Method acquires the current Unix-style time.
     * <p/>
     * Unix-style time is the amount of milliseconds elapsed since 01 Jan 1970.
     *
     * @return
     */
    public int getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }
}
