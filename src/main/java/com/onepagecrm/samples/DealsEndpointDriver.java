package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.LocalDate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles (cillian@onepagecrm.com) on 11/24/15.
 */
public class DealsEndpointDriver {

    private static final Logger LOG = Logger.getLogger(DealsEndpointDriver.class.getName());

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

        OnePageCRM.setServer(Request.DEV_SERVER);

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User : " + loggedInUser);
        List<User> team = Account.team;
        ContactList stream = loggedInUser.actionStream();

        Deal regDeal = new Deal()
                .setOwnerId(team.get(0).getId())        // = Me.
                .setContactId(stream.get(0).getId())    // = First in Stream.
                .setAmount(50d)
                .setText("This is the regular deal text right here!")
                .setName("Regular wrapper deal")
                .setStage(50)
                .setExpectedCloseDate(LocalDate.parse("2016-01-01"))
                .save();

        Deal multiMonth = new Deal()
                .setOwnerId(team.get(0).getId())        // = Me.
                .setContactId(stream.get(1).getId())    // = Second in Stream.
                .setAmount(25d)
                .setMonths(3)
                .setText("This is the multi-month deal text right here!")
                .setName("Multi-month wrapper deal")
                .setStage(25)
                .setExpectedCloseDate(LocalDate.parse("2016-01-01"))
                .save();

        if (regDeal.isValid() && multiMonth.isValid()) {
            regDeal.delete();
            multiMonth.delete();
            LOG.info("Success");
        }
    }
}
