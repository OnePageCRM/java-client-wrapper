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

public class RegisterDeviceDriver {

    private static final Logger LOG = Logger.getLogger(RegisterDeviceDriver.class.getName());

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

        // Register the device.
        final String firebaseToken = "d7sIgbT9wsg:APA91bFQvHUxKdLWzEKfwZpWzwQ0qQFoDElh-wThQDFVojYXD69WBhvsLoqKOb2KNQj47ZCzQ0qLB5Al34HUmASzYxBkQGtmTBQGHw7tv2lO2hwlUSzRF8HEfZVSGHsXircLhCtSIrq4";
        Device phone = new Device().setDeviceId(firebaseToken).setDeviceType(Device.TYPE_ANDROID).register();
        LOG.info("Registered device : " + phone);

        // List current devices.
        List<Device> devices = Device.list();
        LOG.info("Device list : " + devices);
    }
}
