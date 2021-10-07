package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 08/09/2017.
 */
public class API440ContactCompany {

    private static final Logger LOG = Logger.getLogger(API440ContactCompany.class.getName());

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");

            // Load the properties file
            prop.load(input);

        } catch (IOException e) {
            LOG.severe("Error loading the config.properties file");
            LOG.severe(e.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.severe("Error closing the config.properties file");
                    LOG.severe(e.toString());
                }
            }
        }

        OnePageCRM.init(Request.STAGING_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        final String endpoint = "contacts/59b2a7f59007ba09298e0048";
        final String query = "?fields=all,deals(all),notes(all),calls(all)";
        final String body = "{\"id\":\"59b2a7f59007ba09298e0048\",\"last_name\":\"API-440\",\"company_name\":\"OnePageCRM\",\"status_id\":\"559cd19f6f6e656707000005\",\"owner_id\":\"559cd1866f6e656707000001\",\"starred\":false,\"address_list\":[{}],\"tags\":[\"\"]}";
        PutRequest errorRequest = new PutRequest(endpoint, query, body);
        errorRequest.send();
    }
}
