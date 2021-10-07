package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.serializers.ContactListSerializer;
import com.onepagecrm.net.request.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 03/12/2015.
 */
public class ContactListSerializerDriver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    public static void main(String[] args) throws OnePageException, JSONException {
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

        String serialized = ContactListSerializer.toJsonObject(stream);

        ContactList serializedAndParsed = ContactListSerializer.fromJsonObject(
                new JSONObject(serialized)
        );

        LOG.info("stream : " + stream);
        LOG.info("stream.getPaginator() : " + stream.getPaginator());
//        LOG.info("serialized.getPaginator() : " + serialized.getPaginator());
        LOG.info("serializedAndParsed.getPaginator() : " + serializedAndParsed.getPaginator());
    }
}
