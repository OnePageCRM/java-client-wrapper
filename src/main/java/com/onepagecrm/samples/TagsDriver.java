package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.*;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class TagsDriver {

    private static final Logger LOG = Logger.getLogger(TagsDriver.class.getName());

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

        List<Tag> accountTags = copyList(Account.getCurrentUser().getAccount().getTags());
        LOG.info("accountTags *0 : " + copyListNames(accountTags));

        ContactList stream = Account.getCurrentUser().actionStream();

        for (int i = 0; i < accountTags.size(); i++) {
            LOG.info("[" + i + "] : " + accountTags.get(i).delete());
        }

        Tag androidTagTest = new Tag().setName("AndroidTagTest");
        LOG.info("AndroidTagTest : " + androidTagTest.save());

        Tag iOSTagTest = new Tag().setName("iOSTagTest");
        LOG.info("iOSTagTest : " + iOSTagTest.save());

        Tag vip = new Tag().setName("VIP");
        LOG.info("VIP : " + vip.save());

        List<Tag> tags = Tag.list();
        LOG.info("Tags.list() : " + tags);

        List<Tag> tempTags = tags;

        for (int i = 0; i < stream.size(); i++) {
            Contact tempContact = stream.get(i);
            LOG.info("tempContact [" + i + "] : " + tempContact);
            if (i == 0) {
                tempContact.setTags(tempTags);
                tempContact.save();
            } else if (i == 1) {
                tempTags.remove(tempTags.size() - 1);
                tempContact.setTags(tempTags);
                tempContact.save();
            } else if (i == 2) {
                tempTags.remove(tempTags.size() - 1);
                tempContact.setTags(tempTags);
                tempContact.save();
            }
        }
    }

    private static List<Tag> copyList(List<Tag> tags) {
        List<Tag> copy = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                copy.add(new Tag(tags.get(i)));
            }
        }
        return copy;
    }

    private static List<String> copyListNames(List<Tag> tags) {
        List<String> copy = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                copy.add(new Tag(tags.get(i)).getName());
            }
        }
        return copy;
    }
}