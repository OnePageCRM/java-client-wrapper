package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.models.User;
import com.onepagecrm.net.API;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 08/08/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class GoogleContactsDriver {

    private static final Logger LOG = Logger.getLogger(GoogleLoginDriver.class.getName());

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

        OnePageCRM.setServer(Request.STAGING_SERVER);

        User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        final String oauth2Code = "";
        API.GoogleContacts.authorize(oauth2Code);
    }
}
