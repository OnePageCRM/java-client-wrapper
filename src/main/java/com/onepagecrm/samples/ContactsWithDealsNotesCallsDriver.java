package com.onepagecrm.samples;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.serializers.ContactListSerializer;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.Request;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static com.onepagecrm.net.ApiResource.ACTION_STREAM_ENDPOINT;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 03/12/2015.
 */
public class ContactsWithDealsNotesCallsDriver {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

    public static void main(String[] args) throws OnePageException, JSONException {
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

        Request.SERVER = Request.DEV_SERVER;

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User : " + loggedInUser);

//        Map<String, Object> params = new HashMap<>();
//        params.put("fields", "all,deals(all),notes(all),calls(all)");
//        ContactList stream = loggedInUser.actionStream(params);

        // The above did not work since params are URL encoded, changing the , ( ) characters.

        Request request = new GetRequest(ACTION_STREAM_ENDPOINT, "?fields=all,deals(all),notes(all),calls(all)");
        Response response = request.send();
        ContactList stream = ContactListSerializer.fromString(response.getResponseBody());

        for (Contact contact : stream) {
            LOG.info("*** " + contact.getSimpleName() + " *** " +
                    "\nACTIONS : " + (contact.getActions() != null ? contact.getActions().size() : "NONE") + " " + contact.getActions() +
                    "\nDEALS : " + (contact.getDeals() != null ? contact.getDeals().size() : "NONE") + " " + contact.getDeals() +
                    "\nNOTES : " + (contact.getNotes() != null ? contact.getNotes().size() : "NONE") + " " + contact.getNotes() +
                    "\nCALLS : " + (contact.getCalls() != null ? contact.getCalls().size() : "NONE") + " " + contact.getCalls()
            );
        }
    }
}
