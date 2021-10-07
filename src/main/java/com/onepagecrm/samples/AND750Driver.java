package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.User;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AND750Driver {

    private static final Logger LOG = Logger.getLogger(AND750Driver.class.getName());

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

        String endpoint = "deals/59d362df9007ba15df5bfe11";
        String body = "{\"id\":\"59d362df9007ba15df5bfe11\",\"amount\":9.99,\"contact_id\":\"59ccce7d9007ba0db5077565\",\"name\":\"AND-724\",\"status\":\"won\",\"close_date\":\"2017-10-09\",\"cost\":0,\"commission_base\":\"margin\",\"commission_type\":\"none\",\"deal_fields\":[{\"deal_field\":{\"id\":\"5947a6279007ba40b8ecbb0a\",\"choices\":[\"\"],\"name\":\"DealSingleLine\",\"position\":0,\"type\":\"single_line_text\"}},{\"deal_field\":{\"id\":\"5947a6479007ba40b8ecbb0b\",\"choices\":[\"\"],\"name\":\"DealMultiLine\",\"position\":1,\"type\":\"multi_line_text\"}},{\"deal_field\":{\"id\":\"5947a6569007ba40b8ecbb0c\",\"choices\":[\"\"],\"name\":\"DealNumber\",\"position\":2,\"type\":\"number\"}},{\"deal_field\":{\"id\":\"5947a6749007ba40b8ecbb0d\",\"choices\":[\"First\",\"Second\",\"Third\"],\"name\":\"DealDropdown\",\"position\":3,\"type\":\"select_box\"}},{\"deal_field\":{\"id\":\"5947a6849007ba40b8ecbb11\",\"choices\":[\"\"],\"name\":\"DealDate\",\"position\":4,\"type\":\"date\"}},{\"deal_field\":{\"id\":\"5947a6aa9007ba40b8ecbb12\",\"choices\":[\"First\",\"Second\",\"Third\"],\"name\":\"DealMultipleChoice\",\"position\":5,\"type\":\"multiple_choice\"}}]}";
        String bodyPending = "{\"id\":\"59d362df9007ba15df5bfe11\",\"amount\":9.99,\"stage\":50,\"contact_id\":\"59ccce7d9007ba0db5077565\",\"name\":\"AND-724\",\"status\":\"pending\",\"close_date\":\"2017-10-09\",\"cost\":0,\"commission_base\":\"margin\",\"commission_type\":\"none\"}";
        String bodyWon = "{\"id\":\"59d362df9007ba15df5bfe11\",\"amount\":9.99,\"contact_id\":\"59ccce7d9007ba0db5077565\",\"name\":\"AND-724\",\"status\":\"won\",\"close_date\":\"2017-10-09\",\"cost\":0,\"commission_base\":\"margin\",\"commission_type\":\"none\"}";

        Request request = new PutRequest(endpoint, "?partial=1", bodyWon);
        request.send();
    }
}
