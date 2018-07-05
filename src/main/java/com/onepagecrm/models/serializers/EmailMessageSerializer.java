package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Email;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmailMessageSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(EmailMessageSerializer.class.getName());

    public static List<Email> fromJsonArray(JSONArray emailsArray) {
        List<Email> emails = new ArrayList<>();
        for (int j = 0; j < emailsArray.length(); j++) {
            JSONObject emailObject;
            try {
                emailObject = emailsArray.getJSONObject(j);
                emails.add(fromJsonObject(emailObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing email array");
                LOG.severe(e.toString());
            }
        }
        return emails;
    }

    public static Email fromJsonObject(JSONObject emailObject) {
        Email email = new Email();
        try {
            String type = emailObject.getString(TYPE_TAG);
            String value = emailObject.getString(VALUE_TAG);
            return email
                    .setType(type)
                    .setValue(value);
        } catch (JSONException e) {
            LOG.severe("Error parsing email object");
            LOG.severe(e.toString());
        }
        return email;
    }

    public static String toJsonObject(Email email) {
        if (email.getValue() != null) {
            JSONObject emailObject = new JSONObject();
            addJsonStringValue(email.getType().toLowerCase(), emailObject, TYPE_TAG);
            addJsonStringValue(email.getValue(), emailObject, VALUE_TAG);
            return emailObject.toString();
        } else {
            return null;
        }
    }

    public static String toJsonArray(List<Email> emails) {
        JSONArray emailsArray = new JSONArray();
        if (emails != null && !emails.isEmpty()) {
            for (int i = 0; i < emails.size(); i++) {
                try {
                    if (emails.get(i).getValue() != null) {
                        emailsArray.put(new JSONObject(toJsonObject(emails.get(i))));
                    }
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of Emails");
                    LOG.severe(e.toString());
                }
            }
        }
        return emailsArray.toString();
    }
}
