package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.User;
import com.onepagecrm.models.fabricators.CustomFieldFabricator;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CustomFieldsDriver {

    private static final Logger LOG = Logger.getLogger(CustomFieldsDriver.class.getName());

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

        OnePageCRM.init(Request.STAGING_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        LOG.info("User's Custom Fields: " + Account.getCurrentUser().account.customFields);
        LOG.info("User's Company Fields: " + Account.getCurrentUser().account.companyFields);
        LOG.info("User's Deal Fields: " + Account.getCurrentUser().account.dealFields);

        // Single line text.
        CustomField singleLineText = new CustomField()
                .setType(CustomField.TYPE_SINGLE_LINE_TEXT);

        new CustomField(singleLineText)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestSingleLine")
                .save();
        new CustomField(singleLineText)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanySingleLine")
                .save();
        new CustomField(singleLineText)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealSingleLine")
                .save();

        // Multi line text.
        CustomField multiLineText = new CustomField()
                .setType(CustomField.TYPE_MULTI_LINE_TEXT);

        new CustomField(multiLineText)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestMultiLine")
                .save();
        new CustomField(multiLineText)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanyMultiLine")
                .save();
        new CustomField(multiLineText)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealMultiLine")
                .save();

        // Anniversary.
        CustomField anniversary = new CustomField()
                .setType(CustomField.TYPE_ANNIVERSARY)
                .setReminderDays(2);

        new CustomField(anniversary)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestAnniversary")
                .save();

        // Date.
        CustomField date = new CustomField()
                .setType(CustomField.TYPE_DATE);

        new CustomField(date)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestDate")
                .save();
        new CustomField(date)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanyDate")
                .save();
        new CustomField(date)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealDate")
                .save();

        // Number.
        CustomField number = new CustomField()
                .setType(CustomField.TYPE_NUMBER);

        new CustomField(number)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestNumber")
                .save();
        new CustomField(number)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanyNumber")
                .save();
        new CustomField(number)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealNumber")
                .save();

        // Dropdown.
        CustomField dropdown = new CustomField()
                .setType(CustomField.TYPE_DROPDOWN)
                .setChoices(CustomFieldFabricator.choicesList());

        new CustomField(dropdown)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestDropdown")
                .save();
        new CustomField(dropdown)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanyDropdown")
                .save();
        new CustomField(dropdown)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealDropdown")
                .save();

        // Multiple choice.
        CustomField multipleChoice = new CustomField()
                .setType(CustomField.TYPE_MULTIPLE_CHOICE)
                .setChoices(CustomFieldFabricator.choicesList());

        new CustomField(multipleChoice)
                .setCfType(CustomField.CF_TYPE_CONTACT)
                .setName("TestMultipleChoice")
                .save();
        new CustomField(multipleChoice)
                .setCfType(CustomField.CF_TYPE_COMPANY)
                .setName("CompanyMultipleChoice")
                .save();
        new CustomField(multipleChoice)
                .setCfType(CustomField.CF_TYPE_DEAL)
                .setName("DealMultipleChoice")
                .save();
    }
}
