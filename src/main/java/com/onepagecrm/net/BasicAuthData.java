package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Utilities;
import org.apache.commons.codec.binary.Base64;

import java.util.Locale;
import java.util.logging.Logger;

public class BasicAuthData extends AuthData {

    private static final Logger LOG = Logger.getLogger(BasicAuthData.class.getName());

    /**
     * @param userId - Unique identifier of the user.
     * @param apiKey - Like the API password.
     */
    public BasicAuthData(String userId, String apiKey) {
        super(userId, apiKey);
        updateSignature();
    }

    /**
     * @param user - User to use when calculate authorization data.
     */
    public BasicAuthData(User user) {
        super(user.getId(), user.getAuthKey());
        updateSignature();
    }

    /**
     * Method which handles the process of calculating auth data i.e.
     * "Authorization" - "Basic #{Base64.encode("userId:apiKey")}".
     *
     * @return
     */
    public String calculateSignature() {
        if (OnePageCRM.DEBUG) {
            LOG.info(Utilities.repeatedString("*", 40));
            LOG.info("--- AUTHENTICATION ---");
        }
        final String login = getUserId();
        final String password = getApiKey();
        final String toBeEncoded = String.format(Locale.ENGLISH, "%s:%s", login, password);
        if (OnePageCRM.DEBUG) {
            LOG.info("login: " + login);
            LOG.info("password: " + password);
            LOG.info("toBeEncoded: " + toBeEncoded);
        }
        byte[] encodeAuthBytes = new byte[0];
        try {
            if (Utilities.notNullOrEmpty(login) && Utilities.notNullOrEmpty(password)) {
                encodeAuthBytes = Base64.encodeBase64(toBeEncoded.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            LOG.severe("Error encoding the auth data.");
            LOG.severe(e.toString());
        }
        final String encodedAuth = encodeAuthBytes.length == 0 ? "" : new String(encodeAuthBytes);
        final String basicAuth = String.format(Locale.ENGLISH, "Basic %s", encodedAuth);
        if (OnePageCRM.DEBUG) {
            LOG.info("b64(toBeEncoded): " + encodedAuth);
            LOG.info("Authorization: " + basicAuth);
        }
        return basicAuth;
    }
}
