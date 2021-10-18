package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class SalesClosedDriver {

    private static final Logger LOG = Logger.getLogger(SalesClosedDriver.class.getName());

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

        OnePageCRM.init(Request.DEIMOS_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        Map<String, Object> params = new HashMap<>();
        ContactList stream = Account.getCurrentUser().teamStream(params, new Paginator());
        for (Contact contact : stream) {
            if (contact.getSalesClosedFor() != null) {
                LOG.info("closed_sales" + contact.getSalesClosedFor());
            }
        }
    }
}
