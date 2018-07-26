package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Email;
import com.onepagecrm.models.EmailMessage;
import com.onepagecrm.models.EmailRecipients;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmailMessageSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(EmailMessageSerializer.class.getName());

    public static List<EmailMessage> fromString(String responseBody) throws OnePageException {
        List<EmailMessage> result = new ArrayList<>();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonArray(responseObject.getJSONArray("email_messages"));
        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);
        } catch (Exception e) {
            LOG.severe("Error parsing Contact object from response body");
            LOG.severe(e.toString());
        }
        return result;
    }

    public static List<EmailMessage> fromJsonArray(JSONArray emailsArray) {
        List<EmailMessage> emails = new ArrayList<>();
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

    public static EmailMessage fromJsonObject(JSONObject emailObject) {
        EmailMessage email = new EmailMessage();
        try {
            emailObject = emailObject.optJSONObject(EMAIL_MESSAGE_TAG);
            String id = emailObject.optString(ID_TAG);
            String contactId = emailObject.optString(CONTACT_ID_TAG);
            String sendTime = emailObject.optString(SEND_TIME_TAG);
            String sender = emailObject.optString(SENDER_TAG);
            String subject = emailObject.optString(SUBJECT_TAG);
            String plainContent = emailObject.optString(PLAIN_CONTENT_TAG);

            EmailRecipients recipients = new EmailRecipients();
            JSONObject recipientsObject = emailObject.optJSONObject(RECIPIENTS_TAG);

            List<String> to = BaseSerializer.toListOfStrings(recipientsObject.getJSONArray(TO_TAG));
            List<String> bcc = BaseSerializer.toListOfStrings(recipientsObject.getJSONArray(BCC_TAG));
            List<String> cc = BaseSerializer.toListOfStrings(recipientsObject.getJSONArray(CC_TAG));

            recipients.setTo(to)
                    .setBcc(bcc)
                    .setCc(cc);

            email.setRecipients(recipients);

            return email
                    .setId(id)
                    .setContactId(contactId)
                    .setSendTime(sendTime)
                    .setSender(sender)
                    .setSubject(subject)
                    .setPlainContent(plainContent);
        } catch (JSONException e) {
            LOG.severe("Error parsing email object");
            LOG.severe(e.toString());
        }
        return email;
    }

    public static String toJsonObject(EmailMessage email) {
        JSONObject emailObject = new JSONObject();
        // todo
        return emailObject.toString();
    }

    public static String toJsonArray(List<EmailMessage> emails) {
        JSONArray emailsArray = new JSONArray();
        if (emails != null && !emails.isEmpty()) {
            for (int i = 0; i < emails.size(); i++) {
                try {
                    if (emails.get(i).getId() != null) {
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
