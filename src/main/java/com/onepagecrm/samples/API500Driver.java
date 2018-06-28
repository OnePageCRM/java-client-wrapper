package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.PredefinedAction;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 28/06/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class API500Driver {

    private static final Logger LOG = Logger.getLogger(API500Driver.class.getName());

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

        OnePageCRM.setServer(Request.STAGING_SERVER);

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User: " + loggedInUser);

        final List<PredefinedAction> actions = Action.listPredefined();
        int i = 0;
        for (PredefinedAction action : actions) {
            LOG.info("[" + (i++) + "] - " + action.toString());
        }

        // TODO: ensure no errors thrown!?
    }
}
