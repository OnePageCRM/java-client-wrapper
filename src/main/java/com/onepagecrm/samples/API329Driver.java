package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 05/10/2016.
 */
public class API329Driver {

    private static final Logger LOG = Logger.getLogger(API329Driver.class.getName());

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

        OnePageCRM.setServer(Request.DEV_SERVER);

        final String oauthCode = "4/hBCg7Ori8Z9KnNq_5hQ7AiNbf9JkALsFSBq62cWAsYs";
        User.googleLogin(oauthCode);
    }
}
