package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.User;
import com.onepagecrm.models.helpers.DateTimeHelper;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AddDealDriver {

    private static final Logger LOG = Logger.getLogger(AddDealDriver.class.getName());

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

        Contact first = Account.getCurrentUser().actionStream().get(0);

        new Deal()
                .setStage(10)
                .setStatus("pending")
                .setContactId(first.getId())
                .setAmount(33.33d)
                .setDate(DateTimeHelper.today())
                .setText("Java Wrapper Deal Text")
                .setName("Java Wrapper Deal Name")
                .save();
    }
}
