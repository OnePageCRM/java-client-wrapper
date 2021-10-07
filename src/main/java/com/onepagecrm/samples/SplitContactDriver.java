package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.*;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/02/2017.
 */
public class SplitContactDriver {

    private static final Logger LOG = Logger.getLogger(SplitContactDriver.class.getName());

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

        ContactList searchResults = Account.getCurrentUser().searchActionStream("Java");
        Contact contact = searchResults.get(0);
        Company company = Company.byId(contact.getCompanyId());

        LOG.info("Search Results : " + searchResults);
        LOG.info("First Contact : " + contact);
        LOG.info("Contact's Company : " + company);

        String oldCompany = contact.getCompanyName();
        String newCompany = "OnePageCRM";

        LOG.info("Old Company : " + oldCompany);
        LOG.info("New Company : " + newCompany);

        if (company.getContactsCount() > 1) {
            contact.split(newCompany);
            LOG.info("Contact split (since was more than one contact in old company).");
        } else {
            // For some reason have to save contact with no company.
            contact.setCompanyName(null); // TODO - remove me when API-380 fixed!!
            contact.save(); // TODO - remove me when API-380 fixed!!
            // Then save with new company.
            contact.setCompanyName(newCompany);
            contact.save();
            LOG.info("Simple contact update (PUT) sufficient since was last contact in old company.");
            // This appears to be the only way to split a contact from a 1-contact company.
            // ... calling split doesn't work.
            // ... changing from company name to another existing company in single update does not work. [method not allowed].
        }
    }
}
