package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 02/12/2015.
 */
public class EqualsMethodDriver {

    private static final Logger LOG = Logger.getLogger(EqualsMethodDriver.class.getName());

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

        User currentUser = Account.getCurrentUser();

        ContactList stream = currentUser.actionStream();

        User newUser = new User()
                .setId(currentUser.getId())
                .setAuthKey(currentUser.getAuthKey());

        LOG.info(currentUser.toString());
        LOG.info(newUser.toString());
        LOG.info("Users equal : " + currentUser.equals(newUser));

        Contact contact = stream.get(0);
        Contact newContact = new Contact()
                .setId(contact.getId());

        LOG.info(contact.toString());
        LOG.info(newContact.toString());
        LOG.info("Contacts equal : " + contact.equals(newContact));
    }
}
