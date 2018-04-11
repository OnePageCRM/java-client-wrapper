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
import org.threeten.bp.ZoneOffset;
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

    public static TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    public static TimeZone TIME_ZONE_ET = TimeZone.getTimeZone("America/New_York");

    public static final ZoneId ZONE_ID_UTC = ZoneId.of(TIME_ZONE_UTC.getID());
    public static final ZoneId ZONE_ID_ET = ZoneId.of(TIME_ZONE_ET.getID());

    public static final ZoneOffset ZONE_OFFSET_UTC = ZoneOffset.UTC;
    public static final ZoneOffset ZONE_OFFSET_ET = ZoneOffset.ofHours(-4); // Doesn't account for DST!!

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
        for (Contact result : searchResults) {
            action = result.getNextAction();
            if (action != null && Action.Status.DATE_TIME.equals(action.getStatus())) {
                LOG.info("Contact: " + result);
                LOG.info("Action: " + action);
                LOG.info("Java8: " + String.format(Locale.ENGLISH,
                        "{\"date\":\"%s\",\"exact_time\":%d}",
                        action.getJ8Date().toString(),
                        action.getJ8Instant().toEpochMilli() / 1000
                ));
                LOG.info("Java8: " + String.format(Locale.ENGLISH,
                        "{\"date\":\"%s\",\"exact_time\":\"%s\"}",
                        action.getJ8Date().toString(),
                        action.getJ8Instant().atZone(ZONE_ID_ET).format(DateTimeFormatter.ISO_INSTANT)
                ));
                LOG.info("Java8: " + String.format(Locale.ENGLISH,
                        "{\"date\":\"%s\",\"exact_time\":\"%s\"}",
                        action.getJ8Date().toString(),
                        action.getJ8Instant().atZone(ZONE_ID_ET).toString()
                ));
                LOG.info("Java8: " + String.format(Locale.ENGLISH,
                        "{\"date\":\"%s\",\"exact_time\":\"%s\"}",
                        action.getJ8Date().toString(),
                        action.getJ8Instant().atZone(ZONE_ID_ET).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ));
            }
        }
    }
}
