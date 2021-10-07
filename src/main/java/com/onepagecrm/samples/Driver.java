package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

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

        OnePageCRM.init(Request.STAGING_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        User currentUser = Account.getCurrentUser();
        LOG.info("User's Team : " + Account.team);
        LOG.info("User's Settings : " + Account.settings);
        LOG.info("User's Statuses : " + currentUser.getAccount().statuses);
        LOG.info("User's Lead Sources : " + currentUser.getAccount().leadSources);
        LOG.info("User's Custom Fields : " + currentUser.getAccount().customFields);
        LOG.info("User's Company Fields : " + currentUser.getAccount().companyFields);
        LOG.info("User's Call Results : " + currentUser.getAccount().callResults);
        LOG.info("User's Filters : " + currentUser.getAccount().filters);
        LOG.info("User's ContactsCounts : " + currentUser.getAccount().contactsCount);
        LOG.info("User's StreamCount : " + currentUser.getAccount().streamCount);
        LOG.info("User's Predefined Actions : " + currentUser.getAccount().predefinedActions);
        LOG.info("User's Contact Titles : " + currentUser.getAccount().contactTitles);
        LOG.info("User's Account Rights : " + currentUser.getAccountRights());

        final ContactList stream = currentUser.actionStream();

        //final ContactList contacts = currentUser.contacts();

        //final DealList pipeline = currentUser.pipeline();
    }
}
