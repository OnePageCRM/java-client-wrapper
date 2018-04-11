package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
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

        OnePageCRM.setServer(Request.STAGING_SERVER);

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User : " + loggedInUser);

        LOG.info("User's Team : " + Account.team);
        LOG.info("User's Settings : " + Account.settings);
        LOG.info("User's Statuses : " + loggedInUser.getAccount().statuses);
        LOG.info("User's Lead Sources : " + loggedInUser.getAccount().leadSources);
        LOG.info("User's Custom Fields : " + loggedInUser.getAccount().customFields);
        LOG.info("User's Company Fields : " + loggedInUser.getAccount().companyFields);
        LOG.info("User's Call Results : " + loggedInUser.getAccount().callResults);
        LOG.info("User's Filters : " + loggedInUser.getAccount().filters);
        LOG.info("User's ContactsCounts : " + loggedInUser.getAccount().contactsCount);
        LOG.info("User's StreamCount : " + loggedInUser.getAccount().streamCount);
        LOG.info("User's Predefined Actions : " + loggedInUser.getAccount().predefinedActions);
        LOG.info("User's Contact Titles : " + loggedInUser.getAccount().contactTitles);
        LOG.info("User's Account Rights : " + loggedInUser.getAccountRights());

        //ContactList stream = loggedInUser.actionStream();

        //ContactList contacts = loggedInUser.contacts();

        //DealList pipeline = loggedInUser.pipeline();
    }
}
