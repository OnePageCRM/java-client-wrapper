package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.EmailMessage;
import com.onepagecrm.models.EmailRecipients;
import org.json.JSONArray;
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
            JSONArray emailsArray = responseObject.getJSONArray(EMAIL_MESSAGES_TAG);
            return fromJsonArray(emailsArray);
        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);
        } catch (Exception e) {
            LOG.severe("Error parsing EmailMessages array from response body");
            LOG.severe(e.toString());
        }
        return result;
    }

    public static List<EmailMessage> fromJsonArray(JSONArray emailsArray) {
        List<EmailMessage> emails = new ArrayList<>();
        if (emailsArray == null) return emails;
        for (int i = 0; i < emailsArray.length(); i++) {
            JSONObject emailObject = emailsArray.optJSONObject(i);
            EmailMessage email = fromJsonObject(emailObject);
            emails.add(email);
        }
        return emails;
    }

    public static EmailMessage fromJsonObject(JSONObject emailObject) {
        if (emailObject == null) {
            return new EmailMessage();
        }

        if (emailObject.has(EMAIL_MESSAGE_TAG)) {
            emailObject = emailObject.optJSONObject(EMAIL_MESSAGE_TAG);
        }

        JSONObject recipientsObject = emailObject.optJSONObject(RECIPIENTS_TAG);

        return new EmailMessage()
                .setId(emailObject.optString(ID_TAG))
                .setContactId(emailObject.optString(CONTACT_ID_TAG))
                .setSendTime(emailObject.optString(SEND_TIME_TAG))
                .setMessageId(emailObject.optString(MESSAGE_ID_TAG))
                .setSender(emailObject.optString(SENDER_TAG))
                .setSubject(emailObject.optString(SUBJECT_TAG))
                .setPlainContent(emailObject.optString(PLAIN_CONTENT_TAG))
                .setHtmlContent(emailObject.optString(HTML_CONTENT_TAG))
                .setStatus(emailObject.optString(STATUS_TAG))
                .setRecipients(new EmailRecipients()
                        .setTo(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(TO_TAG)))
                        .setBcc(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(BCC_TAG)))
                        .setCc(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(CC_TAG))))
                .setAttachments(AttachmentSerializer.fromJsonArray(emailObject.optJSONArray(ATTACHMENTS_TAG)));
    }

    public static JSONObject toJsonObject(EmailMessage email) {
        JSONObject emailObject = new JSONObject();
        if (email == null) return emailObject;
        // TODO: when needed!!
        return emailObject;
    }

    public static String toJsonString(EmailMessage email) {
        return toJsonObject(email).toString();
    }

    public static JSONArray toJsonArray(List<EmailMessage> emails) {
        JSONArray emailsArray = new JSONArray();
        if (emails == null) return emailsArray;
        for (EmailMessage email : emails) {
            JSONObject emailObject = toJsonObject(email);
            if (email.getId() != null) {
                emailsArray.put(emailObject);
            }
        }
        return emailsArray;
    }

    public static String toJsonString(List<EmailMessage> emails) {
        return toJsonArray(emails).toString();
    }
}
