package com.onepagecrm.samples;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ActionsEndpoint {

    private static final Logger LOG = Logger.getLogger(ActionsEndpoint.class.getName());

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

        Request.SERVER = Request.DEIMOS_SERVER;

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User : " + loggedInUser);
        LOG.info("Actions : " + loggedInUser.actions());
        LOG.info("Predefined : " + loggedInUser.actionsPredefined());
    }
}
