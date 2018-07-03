package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Address;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.Email;
import com.onepagecrm.models.Phone;
import com.onepagecrm.models.Url;
import com.onepagecrm.models.User;
import com.onepagecrm.models.helpers.DateTimeHelper;
import com.onepagecrm.models.internal.CustomFieldValue;
import com.onepagecrm.models.serializers.LocalDateSerializer;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class EditContactDriver {

    private static final Logger LOG = Logger.getLogger(EditContactDriver.class.getName());

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

        LOG.info("Logged in User : " + loggedInUser);
        LOG.info("User's Custom Fields : " + loggedInUser.getAccount().customFields);

        ContactList stream = loggedInUser.actionStream();

        Contact contact = stream.get(0);
        LOG.info("Contact : " + contact);

        if (contact.isValid()) {

            contact.setFirstName("Java");
            contact.setLastName("Wrapper");
            contact.setJobTitle("Software Developer");
            contact.setCompanyName("OnePageCRM");
            contact.setBackground("Java is very verbose, we met in college.");
            contact.setOwnerId(loggedInUser.getId());
            contact.setStatusId(loggedInUser.getAccount().statuses.get(0).getId());
            contact.setLeadSourceId(loggedInUser.getAccount().leadSources.get(0).getId());
            contact.setStarred(true);

            Address address = new Address();
            address.setAddress("Unit 5, Business Innovation Center, NUIG");
            address.setCity("Galway");
            address.setState("Connaught");
            address.setZipCode("HJ12WE3");
            address.setCountryCode("IE");
            contact.setAddress(address);

            // Create a list for phone numbers.
            List<Phone> phones = new ArrayList<>();

            Phone work = new Phone();
            work.setType("Work");
            work.setValue("091123456");
            phones.add(work);

            Phone mobile = new Phone();
            mobile.setType("Mobile");
            mobile.setValue("0877734242");
            phones.add(mobile);

            Phone home = new Phone();
            home.setType("Home");
            home.setValue("091654321");
            phones.add(home);

            Phone direct = new Phone();
            direct.setType("Direct");
            direct.setValue("0868825552");
            phones.add(direct);

            Phone fax = new Phone();
            fax.setType("Fax");
            fax.setValue("091754409");
            phones.add(fax);

            Phone skype = new Phone();
            skype.setType("Skype");
            skype.setValue("+35391998877");
            phones.add(skype);

            Phone other = new Phone();
            other.setType("Other");
            other.setValue("+353868825552");
            phones.add(other);

            // Add list of phones to our Contact.
            contact.setPhones(phones);

            // Create a list for Email addresses.
            List<Email> emails = new ArrayList<>();

            Email workEmail = new Email();
            workEmail.setType("Work");
            workEmail.setValue("work@domain.com");
            emails.add(workEmail);

            Email homeEmail = new Email();
            homeEmail.setType("Home");
            homeEmail.setValue("home@domain.com");
            emails.add(homeEmail);

            Email otherEmail = new Email();
            otherEmail.setType("Other");
            otherEmail.setValue("other@domain.com");
            emails.add(otherEmail);

            // Add list of emails to our Contact.
            contact.setEmails(emails);

            // Create a list for Web addresses.
            List<Url> urls = new ArrayList<>();

            Url website = new Url();
            website.setType("Website");
            website.setValue("www.website.com");
            urls.add(website);

            Url blog = new Url();
            blog.setType("Blog");
            blog.setValue("www.blog.com");
            urls.add(blog);

            Url twitter = new Url();
            twitter.setType("Twitter");
            twitter.setValue("www.twitter.com");
            urls.add(twitter);

            Url linkedIn = new Url();
            linkedIn.setType("LinkedIn");
            linkedIn.setValue("www.linkedin.com");
            urls.add(linkedIn);

            Url facebook = new Url();
            facebook.setType("Facebook");
            facebook.setValue("www.facebook.com");
            urls.add(facebook);

            Url googlePlus = new Url();
            googlePlus.setType("Google+");
            googlePlus.setValue("www.googleplus.com");
            urls.add(googlePlus);

            Url otherUrl = new Url();
            otherUrl.setType("Other");
            otherUrl.setValue("www.other.com");
            urls.add(otherUrl);

            Url xing = new Url();
            xing.setType("Xing");
            xing.setValue("www.xing.com");
            urls.add(xing);

            // Add list of urls to our Contact.
            contact.setUrls(urls);

            List<CustomField> customFields = loggedInUser.getAccount().customFields;

            for (CustomField customField : customFields) {

                if (customField.getType().equalsIgnoreCase(CustomField.TYPE_DATE) ||
                        customField.getType().equalsIgnoreCase(CustomField.TYPE_ANNIVERSARY)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    newValue.setValue(LocalDateSerializer.getInstance().format(DateTimeHelper.today()));
                    customField.setValue(newValue);

                } else if (customField.getType().equalsIgnoreCase(CustomField.TYPE_DROPDOWN)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    newValue.setValue(customField.getChoices().get(0)); // Use first choice.
                    customField.setValue(newValue);

                } else if (customField.getType().equals(CustomField.TYPE_MULTI_LINE_TEXT)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    newValue.setValue("Multi line text ... ");
                    customField.setValue(newValue);

                } else if (customField.getType().equals(CustomField.TYPE_MULTIPLE_CHOICE)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    int numOptions = customField.getChoices().size();
                    String[] selected = new String[numOptions];
                    for (int j = 0; j < numOptions; j++) {
                        selected[j] = customField.getChoices().get(j);
                    }
                    newValue.setValue(selected);
                    customField.setValue(newValue);

                } else if (customField.getType().equals(CustomField.TYPE_NUMBER)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    newValue.setValue(3.14f);
                    customField.setValue(newValue);

                } else if (customField.getType().equalsIgnoreCase(CustomField.TYPE_SINGLE_LINE_TEXT)) {

                    CustomFieldValue newValue = new CustomFieldValue();
                    newValue.setValue("Single line text ... ");
                    customField.setValue(newValue);
                }
            }

            contact.setCustomFields(customFields);

            contact = contact.save();
            LOG.info("Updated Contact : " + contact);
        }
    }
}
