package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CreateQaWhenNoNaDriver {

    private static final Logger LOG = Logger.getLogger(CreateQaWhenNoNaDriver.class.getName());

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

        OnePageCRM.init(Request.APP_US_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        new Action()
                .setText("Shouldn't be added")
                .setStatus(Action.Status.QUEUED)
                .setContactId("5774e13c00d4afe3fb314f58")
                .setAssigneeId("556cb8b61787fa02e000047e")
                .save();
    }
}
