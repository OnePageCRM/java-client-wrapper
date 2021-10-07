package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.*;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class AND328DebugFieldsDriver {

    private static final Logger LOG = Logger.getLogger(AND328DebugFieldsDriver.class.getName());

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

        CompanyList companies = Account.getCurrentUser().companies();
        Company company = Company.byId(companies.get(0).getId());

        List<CustomField> customFieldList = CustomField.listContacts();
        List<CustomField> companyFieldList = CustomField.listCompanies();

        LOG.info("Companies : " + companies);
        LOG.info("Company : " + company);
        LOG.info("Custom fields : " + customFieldList);
        LOG.info("Company fields : " + companyFieldList);

        for (CustomField field : customFieldList) {
            LOG.info("CustomField[index: " + customFieldList.indexOf(field)
                    + ", hashCode: " + field.getId().hashCode() + "] : " + field);
        }

        for (CustomField field : companyFieldList) {
            LOG.info("CompanyField[index: " + companyFieldList.indexOf(field)
                    + ", hashCode: " + field.getId().hashCode() + "] : " + field);
        }
    }
}
