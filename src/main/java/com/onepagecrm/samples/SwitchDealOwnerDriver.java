package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
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
public class SwitchDealOwnerDriver {

    private static final Logger LOG = Logger.getLogger(SwitchDealOwnerDriver.class.getName());

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

        List<User> team = Account.team;
        ContactList stream = Account.getCurrentUser().actionStream();

        // Create a deal with me as Owner.
        Deal newDeal = new Deal()
                .setOwnerId(team.get(0).getId()) // Owner = Me (when created).
                .setContactId(stream.get(0).getId())
                .setAmount(27.99d)
                .setText("Shouldn't be seen")
                .setName("Wrapper Deal")
                .setExpectedCloseDate(LocalDate.parse("2014-01-01"))
                .save();

        // Update a deal to have different owner than me.
        DealList deals;
        LOG.info("Deals : " + (deals = Account.getCurrentUser().pipeline()));

        Deal updated = deals.get(0)
                .setText("Should be seen!!!")
                .setOwnerId(team.get(1).getId()) // Owner = Second Person (after update).
                .setAmount(99.99d)
                .save();

        if (newDeal.getOwnerId().equalsIgnoreCase(team.get(0).getId())
                && updated.getOwnerId().equalsIgnoreCase(team.get(1).getId())) {
            if (newDeal.getId().equalsIgnoreCase(updated.getId())) {
                newDeal.delete();
                LOG.info("Success");
            }
        }
    }
}
