package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.Attachment;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.Commission;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class EnumsDriver {

    private static final Logger LOG = Logger.getLogger(EnumsDriver.class.getName());

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

        Action.Status status = Action.Status.fromString("test");
        Attachment.Provider provider = Attachment.Provider.fromString("test");
        Commission.Base base = Commission.Base.fromString("test");
        Commission.Type type = Commission.Type.fromString("test");

        switch (status) {
            case ASAP: {
                LOG.info("ASAP : " + status.toString());
                break;
            }
            case DATE: {
                LOG.info("DATE : " + status.toString());
                break;
            }
            case DATE_TIME: {
                LOG.info("DATE_TIME : " + status.toString());
                break;
            }
            case WAITING: {
                LOG.info("WAITING : " + status.toString());
                break;
            }
            case QUEUED: {
                LOG.info("QUEUED : " + status.toString());
                break;
            }
            case QUEUED_WITH_DATE: {
                LOG.info("QUEUED_WITH_DATE : " + status.toString());
                break;
            }
            case DONE: {
                LOG.info("DONE : " + status.toString());
                break;
            }
            case OTHER: {
                LOG.info("OTHER : " + status.toString());
                break;
            }
        }

        switch (provider) {
            case AMAZON: {
                LOG.info("AMAZON : " + provider.toString());
                break;
            }
            case DRIVE: {
                LOG.info("DRIVE : " + provider.toString());
                break;
            }
            case DROPBOX: {
                LOG.info("DROPBOX : " + provider.toString());
                break;
            }
            case EVERNOTE: {
                LOG.info("EVERNOTE : " + provider.toString());
                break;
            }
            case OTHER: {
                LOG.info("OTHER : " + provider.toString());
                break;
            }
        }

        switch (base) {
            case MARGIN: {
                LOG.info("MARGIN : " + base.toString());
                break;
            }
            case AMOUNT: {
                LOG.info("AMOUNT : " + base.toString());
                break;
            }
            case OTHER: {
                LOG.info("OTHER : " + base.toString());
                break;
            }
        }

        switch (type) {
            case PERCENTAGE: {
                LOG.info("PERCENTAGE : " + type.toString());
                break;
            }
            case ABSOLUTE: {
                LOG.info("ABSOLUTE : " + type.toString());
                break;
            }
            case NONE: {
                LOG.info("NONE : " + type.toString());
                break;
            }
            case OTHER: {
                LOG.info("OTHER : " + type.toString());
                break;
            }
        }
    }
}
