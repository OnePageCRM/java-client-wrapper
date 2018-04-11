package com.onepagecrm.samples;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.net.API;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 11/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class ThreeTenBPDriver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    private static TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    private static TimeZone TIME_ZONE_ET = TimeZone.getTimeZone("America/New_York");

    private static final ZoneId ZONE_ID_UTC = ZoneId.of(TIME_ZONE_UTC.getID());
    private static final ZoneId ZONE_ID_ET = ZoneId.of(TIME_ZONE_ET.getID());

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

        // Note: no need to set server!!

        StartupData startupData = API.App.startup(
                Request.AUTH_DEV_SERVER,
                prop.getProperty("username"),
                prop.getProperty("password"));

        User loggedInUser = startupData.getUser();
        ContactList stream = startupData.getStream();
        ContactList contacts = startupData.getStream();
        DealList pipeline = startupData.getDeals();

        LOG.info(Utilities.repeatedString("*", 40));
        LOG.info("User: " + loggedInUser);
        LOG.info("Stream: " + stream);
        LOG.info("Contacts: " + contacts);
        LOG.info("Pipeline: " + pipeline);

        LOG.info(Utilities.repeatedString("*", 40));
        ContactList searchResults = loggedInUser.searchContacts("Wrapper");
        Action action;
        for (Contact contact : searchResults) {
            action = contact.getNextAction();
            if (action != null && Action.Status.DATE_TIME.equals(action.getStatus())) {
                LOG.info("Contact: " + contact);
                LOG.info("Action: " + action);
                LOG.info("Java8: {" + String.format(Locale.ENGLISH,
                        "\n\t\"date\":\"%s\"," +
                                "\n\t\"seconds_utc\":%d," +
                                "\n\t\"millis_utc\":%d," +
                                "\n\t\"timestamp_utc\":\"%s\"," +
                                "\n\t\"timestamp_est\":\"%s\"," +
                                "\n\t\"timestamp_est\":\"%s\"," +
                                "\n\t\"formatted_utc\":\"%s\"," +
                                "\n\t\"formatted_est\":\"%s\"" +
                                "\n}",
                        action.getJ8Date().toString(),
                        action.getJ8ExactTime().toEpochMilli() / 1000,
                        action.getJ8ExactTime().toEpochMilli(),
                        action.getJ8ExactTime().atZone(ZONE_ID_UTC).format(DateTimeFormatter.ISO_INSTANT),
                        action.getJ8ExactTime().atZone(ZONE_ID_ET).toString(),
                        action.getJ8ExactTime().atZone(ZONE_ID_ET).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        action.getJ8ExactTime().atZone(ZONE_ID_UTC).format(DateTimeFormatter.ofPattern("hh:mma MMM dd, yyyy")),
                        action.getJ8ExactTime().atZone(ZONE_ID_ET).format(DateTimeFormatter.ofPattern("hh:mma MMM dd, yyyy"))
                ));
            }
        }
    }
}
