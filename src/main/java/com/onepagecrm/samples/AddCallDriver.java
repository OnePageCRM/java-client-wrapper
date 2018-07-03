package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Call;
import com.onepagecrm.models.CallResult;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.User;
import com.onepagecrm.models.helpers.DateTimeHelper;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AddCallDriver {

    private static final Logger LOG = Logger.getLogger(AddCallDriver.class.getName());

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

        Contact first = loggedInUser.actionStream().get(0);

        new Call()
                .setText("Java Wrapper call")
                .setContactId(first.getId())
                .setTime(DateTimeHelper.nowUTC())
                .setCallResult(new CallResult().setId("interested"))
                .save();
    }
}
