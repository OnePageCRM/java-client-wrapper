package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.serializers.RequestMetadataSerializer;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 02/12/2015.
 */
public class PaginationDriver {

    private static final Logger LOG = Logger.getLogger(PaginationDriver.class.getName());

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

        OnePageCRM.init(Request.DEV_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        ContactList stream = Account.getCurrentUser().actionStream();
        LOG.info("page 1 : " + stream.size() + " : " + stream.toString());

        ContactList updatedStream = stream.nextPage(Collections.EMPTY_MAP);
        LOG.info("page 1&2 : " + updatedStream.size() + " : " + updatedStream.toString());

        ContactList refreshedList = stream.refresh(Collections.EMPTY_MAP);
        LOG.info("page 1 : " + refreshedList.size() + " : " + refreshedList.toString());

        LOG.info(RequestMetadataSerializer.toJsonKeyValuePair(stream.getPaginator()));
    }
}
