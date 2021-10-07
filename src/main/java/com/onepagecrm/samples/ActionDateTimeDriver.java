package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.User;
import com.onepagecrm.models.serializers.InstantSerializer;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ActionDateTimeDriver {

    private static final Logger LOG = Logger.getLogger(ActionDateTimeDriver.class.getName());

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

        OnePageCRM.init(Request.APP_US_SERVER, prop.getProperty("user_id"), prop.getProperty("api_key"));

        // Fri, 01 Jul 2016 08:00:00 GMT = 1467360000 = Fri, 01 Jul 2016 9:00:00 AM (IST)
        Instant firstJulyAt9Am = InstantSerializer.getInstance().ofSeconds(1467360000L);
        LocalDate firstJuly = firstJulyAt9Am.atZone(ZoneId.of("Z")).toLocalDate();

        new Action()
                .setText("This one will be added")
                .setStatus(Action.Status.DATE_TIME)
                .setDate(firstJuly)
                .setExactTime(firstJulyAt9Am)
                .setContactId("60d907e12b95af0ff8dc61fa")
                .setAssigneeId(prop.getProperty("user_id"))
                .save();
    }
}
