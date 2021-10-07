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

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

public class CreateContactDriver {

    private static final Logger LOG = Logger.getLogger(CreateContactDriver.class.getName());

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

        List<LeadSource> leadSources = Account.getCurrentUser().getAccount().getLeadSources();
        List<Status> statuses = Account.getCurrentUser().getAccount().getStatuses();

        String desiredLeadSource = leadSources.get(1).getText();
        String desiredLeadSourceId = leadSources.get(1).getId();

        String desiredStatus = statuses.get(1).getText();
        String desiredStatusId = statuses.get(1).getId();

        Contact idWay = new Contact()
                .setFirstName("Bbb1")
                .setLastName("Aaa1")
                .setLeadSourceId(desiredLeadSourceId)
                .setStatusId(desiredStatusId)
                .save();

        Contact textWay = new Contact()
                .setFirstName("Bbb2")
                .setLastName("Aaa2")
                .setLeadSourceId(desiredLeadSource)
                .setStatus(desiredStatus)
                .save();

        if (notNullOrEmpty(idWay.getLeadSourceId()) && notNullOrEmpty(textWay.getLeadSourceId())) {
            if (idWay.getLeadSourceId().equals(textWay.getLeadSourceId())) {
                if (idWay.getLeadSourceId().equals(desiredLeadSourceId)) {
                    LOG.info("SUCCESS!! - LEAD SOURCE - lead sources equal.");
                } else {
                    LOG.info("FAILURE!! - LEAD SOURCE - lead source ids equal but not set to desired value.");
                }
            } else {
                LOG.info("FAILURE!! - LEAD SOURCE - lead sources NOT equal.");
            }
        } else {
            LOG.info("FAILURE!! - LEAD SOURCE - id not set for either id or text way.");
        }

        if (notNullOrEmpty(idWay.getStatusId()) && notNullOrEmpty(textWay.getStatusId())) {
            if (idWay.getStatusId().equals(textWay.getStatusId())) {
                if (idWay.getStatusId().equals(desiredStatusId)) {
                    LOG.info("SUCCESS!! - STATUS_ID - status_id fields equal.");
                } else {
                    LOG.info("FAILURE!! - STATUS_ID - status_id fields equal but not set to desired value.");
                }
            } else {
                LOG.info("FAILURE!! - STATUS_ID - status_id fields NOT equal.");
            }
        } else {
            LOG.info("FAILURE!! - STATUS_ID - id not set for either id or text way.");
        }

        if (notNullOrEmpty(idWay.getStatus()) && notNullOrEmpty(textWay.getStatus())) {
            if (idWay.getStatus().equals(textWay.getStatus())) {
                if (idWay.getStatus().equals(desiredStatus)) {
                    LOG.info("SUCCESS!! - STATUS - status fields equal.");
                } else {
                    LOG.info("FAILURE!! - STATUS - status fields equal but not set to desired value.");
                }
            } else {
                LOG.info("FAILURE!! - STATUS - status fields NOT equal.");
            }
        } else {
            LOG.info("FAILURE!! - STATUS - status field not set for either id or text way.");
        }
    }
}
