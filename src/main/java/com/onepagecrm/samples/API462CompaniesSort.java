package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Company;
import com.onepagecrm.models.CompanyList;
import com.onepagecrm.models.User;
import com.onepagecrm.models.serializers.InstantSerializer;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;
import static com.onepagecrm.models.internal.Utilities.repeatedString;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 21/11/2017.
 */
public class API462CompaniesSort {

    private static final Logger LOG = Logger.getLogger(Driver.class.getName());

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

        Map<String, Object> params = new HashMap<>();
        params.put("order", "asc");

        CompanyList defaultAPI = Company.list();
        CompanyList defaultManual = new CompanyList(defaultAPI.getList());

        params.put("sort_by", "name");
        CompanyList azAPI = Company.list(params);
        CompanyList azManual = new CompanyList(azAPI.getList());

        params.put("sort_by", "created_at");
        CompanyList createdAtAPI = Company.list(params);
        CompanyList createdAtManual = new CompanyList(createdAtAPI.getList());

        params.put("sort_by", "modified_at");
        CompanyList modifiedAtAPI = Company.list(params);
        CompanyList modifiedAtManual = new CompanyList(modifiedAtAPI.getList());

        // Default sorting (az).
        defaultManual.getList().sort((c1, c2) -> {
            final String cmp1 = notNullOrEmpty(c1.getName()) ? c1.getName() : "";
            final String cmp2 = notNullOrEmpty(c2.getName()) ? c2.getName() : "";
            return cmp1.compareToIgnoreCase(cmp2);
        });

        // Default checking.
        LOG.info(repeatedString("*", 5) + " Default (az) " + repeatedString("*", 5));
        boolean defaultSortOkay = true;
        int i = 0;
        for (Company sorted : defaultManual) {
            String sortedName = sorted.getName();
            String apiName = defaultAPI.get(i++).getName();
            boolean match = sortedName.equalsIgnoreCase(apiName);
            LOG.info("API: \"" + apiName + "\" - SORT: \"" + sortedName + "\" - match: " + match);
            defaultSortOkay &= match;
        }

        // AZ sorting.
        azManual.getList().sort((c1, c2) -> {
            final String cmp1 = notNullOrEmpty(c1.getName()) ? c1.getName() : "";
            final String cmp2 = notNullOrEmpty(c2.getName()) ? c2.getName() : "";
            return cmp1.compareToIgnoreCase(cmp2);
        });

        // AZ checking.
        LOG.info(repeatedString("*", 5) + " AZ " + repeatedString("*", 5));
        boolean azSortOkay = true;
        i = 0;
        for (Company sorted : azManual) {
            String sortedName = sorted.getName();
            String apiName = azAPI.get(i++).getName();
            boolean match = sortedName.equalsIgnoreCase(apiName);
            LOG.info("API: \"" + apiName + "\" - SORT: \"" + sortedName + "\" - match: " + match);
            azSortOkay &= match;
        }

        // created_at sorting.
        createdAtManual.getList().sort((c1, c2) -> {
            final long cmp1 = c1.getCreatedAt() != null ? InstantSerializer.getInstance().seconds(c1.getCreatedAt()) : 0L;
            final long cmp2 = c2.getCreatedAt() != null ? InstantSerializer.getInstance().seconds(c2.getCreatedAt()) : 0L;
            return Long.compare(cmp1, cmp2);
        });

        // created_at checking.
        LOG.info(repeatedString("*", 5) + " created_at " + repeatedString("*", 5));
        boolean createdSortOkay = true;
        i = 0;
        for (Company sorted : createdAtManual) {
            Instant sortedTime = sorted.getCreatedAt();
            Instant apiTime = createdAtAPI.get(i++).getCreatedAt();
            boolean match = ((sortedTime == null && apiTime == null)
                    || sortedTime != null && sortedTime.equals(apiTime));
            LOG.info("API: \"" + apiTime + "\" - SORT: \"" + sortedTime + "\" - match: " + match);
            createdSortOkay &= match;
        }

        // modified_at sorting.
        modifiedAtManual.getList().sort((c1, c2) -> {
            final long cmp1 = c1.getModifiedAt() != null ? InstantSerializer.getInstance().seconds(c1.getModifiedAt()) : 0L;
            final long cmp2 = c2.getModifiedAt() != null ? InstantSerializer.getInstance().seconds(c2.getModifiedAt()) : 0L;
            return Long.compare(cmp1, cmp2);
        });

        // modified_at checking.
        LOG.info(repeatedString("*", 5) + " modified_at " + repeatedString("*", 5));
        boolean modifiedSortOkay = true;
        i = 0;
        for (Company sorted : modifiedAtManual) {
            Instant sortedTime = sorted.getModifiedAt();
            Instant apiTime = modifiedAtAPI.get(i++).getModifiedAt();
            boolean match = ((sortedTime == null && apiTime == null)
                    || sortedTime != null && sortedTime.equals(apiTime));
            LOG.info("API: \"" + apiTime + "\" - SORT: \"" + sortedTime + "\" - match: " + match);
            modifiedSortOkay &= match;
        }

        LOG.info(repeatedString("*", 5) + " results " + repeatedString("*", 5));
        LOG.info("Default sort (az) okay: " + defaultSortOkay);
        LOG.info("AZ sort okay: " + azSortOkay);
        LOG.info("created_at sort okay: " + createdSortOkay);
        LOG.info("modified_at sort okay: " + modifiedSortOkay);
    }
}
