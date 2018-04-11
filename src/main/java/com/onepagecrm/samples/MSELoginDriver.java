package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.net.API;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class MSELoginDriver {

    private static final Logger LOG = Logger.getLogger(MSELoginDriver.class.getName());

    public static void main(String[] args) throws OnePageException {
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

        // NOTICE: no need to set server.

        OnePageCRM.setDebug(true);

        StartupData startupData = API.App.startup(
                Request.AUTH_DEV_SERVER,
                prop.getProperty("username"),
                prop.getProperty("password")
        );


        StartupData startupDataCmp = API.App.startup();

        LOG.info(Utilities.repeatedString("*", 40));
        LOG.info("STARTUP *1*: " + startupData);
        LOG.info("STARTUP *2*: " + startupDataCmp);
        LOG.info("STARTUP equal: " + startupData.equals(startupDataCmp));
        LOG.info(Utilities.repeatedString("*", 40));
    }
}
