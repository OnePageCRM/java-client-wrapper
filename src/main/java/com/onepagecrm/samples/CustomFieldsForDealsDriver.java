package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class CustomFieldsForDealsDriver {

    private static final Logger LOG = Logger.getLogger(CustomFieldsForDealsDriver.class.getName());

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

        List<CustomField> customFieldList = CustomField.listContacts();
        List<CustomField> companyFieldList = CustomField.listCompanies();
        List<CustomField> dealFieldList = CustomField.listDeals();

        LOG.info("Custom fields (1) : " + Account.getCurrentUser().getAccount().getCustomFields());
        LOG.info("Company fields (1) : " + Account.getCurrentUser().getAccount().getCompanyFields());
        LOG.info("Deal fields (1) : " + Account.getCurrentUser().getAccount().getDealFields());

        LOG.info("Custom fields (2) : " + customFieldList);
        LOG.info("Company fields (2) : " + companyFieldList);
        LOG.info("Deal fields (2) : " + dealFieldList);
    }
}
