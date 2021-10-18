package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;
import javafx.scene.control.Accordion;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

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

        OnePageCRM.init(Request.DEV_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

//        Map<String, Object> params = new HashMap<>();
//        params.put("fields", "all,deals(all),notes(all),calls(all)");
//        ContactList stream = Account.getCurrentUser().actionStream(params);

        // The above did not work since params are URL encoded, changing the , ( ) characters.

//        Request request = new GetRequest(ACTION_STREAM_ENDPOINT, "?fields=all,deals(all),notes(all),calls(all)");
//        Response response = request.send();
//        ContactList stream = ContactListSerializer.fromString(response.getResponseBody());

        ContactList stream = Account.getCurrentUser().actionStream();

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
