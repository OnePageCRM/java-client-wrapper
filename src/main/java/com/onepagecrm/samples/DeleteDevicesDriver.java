package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Device;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 14/11/2017.
 */
public class DeleteDevicesDriver {

    private static final Logger LOG = Logger.getLogger(DeleteDevicesDriver.class.getName());

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

        // List all devices.
        List<Device> devices = Device.list();
        int i = 0;
        for (Device device : devices) {
            LOG.info("Device[" + (i++) + "] : " + device);
        }

        // Delete all devices.
        for (Device device : devices) {
            device.delete();
        }

        // List again (should be none).
        devices = Device.list();
        i = 0;
        for (Device device : devices) {
            LOG.info("Device[" + (i++) + "] : " + device);
        }
    }
}
