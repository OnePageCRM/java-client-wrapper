package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.EmailMessage;
import com.onepagecrm.models.EmailRecipients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 12/10/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class EmailMessageSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(EmailMessageSerializer.class.getName());

    public static EmailMessage singleFromString(String responseBody) throws OnePageException {
        EmailMessage result = new EmailMessage();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject dataObject = new JSONObject(parsedResponse);
            JSONObject emailObject = dataObject.getJSONObject(EMAIL_MESSAGE_TAG);
            return fromJsonObject(emailObject);
        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);
        } catch (Exception e) {
            LOG.severe("Error parsing EmailMessage object from response body");
            LOG.severe(e.toString());
        }
        return result;
    }

    public static List<EmailMessage> listFromString(String responseBody) throws OnePageException {
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
                .setType(EmailMessage.Type.fromString(emailObject.optString(TYPE_TAG)))
                .setContactIds(BaseSerializer.toListOfStrings(emailObject.optJSONArray(CONTACT_IDS_TAG)))
                .setSendTime(InstantSerializer.getInstance().parse(emailObject.optString(SEND_TIME_TAG)))
                .setMessageId(emailObject.optString(MESSAGE_ID_TAG))
                .setSender(emailObject.optString(SENDER_TAG))
                .setRecipients(new EmailRecipients()
                        .setTo(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(TO_TAG)))
                        .setBcc(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(BCC_TAG)))
                        .setCc(BaseSerializer.toListOfStrings(recipientsObject.optJSONArray(CC_TAG))))
                .setUrl(emailObject.optString(URL_TAG))
                .setSubject(emailObject.optString(SUBJECT_TAG))
                .setPlainContent(emailObject.optString(PLAIN_CONTENT_TAG))
                .setHtmlContent(emailObject.optString(HTML_CONTENT_TAG))
                .setStatus(EmailMessage.Status.fromString(emailObject.optString(STATUS_TAG)))
                .setIncoming(emailObject.optBoolean(INCOMING_EMAIL_ATG, false))
                .setAttachments(AttachmentSerializer.fromJsonArray(emailObject.optJSONArray(ATTACHMENTS_TAG)));
    }

    public static JSONObject toJsonObject(EmailMessage email) {
        JSONObject emailObject = new JSONObject();
        if (email == null) return emailObject;
        addJsonStringValue(email.getId(), emailObject, ID_TAG);
        addJsonStringValue(email.getType().toString(), emailObject, TYPE_TAG);
        addJsonArray(BaseSerializer.toJsonStringArray(email.getContactIds()), emailObject, CONTACT_IDS_TAG);
        addJsonStringValue(InstantSerializer.getInstance().format(email.getSendTime()), emailObject, SEND_TIME_TAG);
        addJsonStringValue(email.getMessageId(), emailObject, MESSAGE_ID_TAG);
        addJsonStringValue(email.getSender(), emailObject, SENDER_TAG);
        if (email.hasRecipients()) {
            JSONObject recipientsObject = new JSONObject();
            addJsonArray(BaseSerializer.toJsonStringArray(email.getRecipients().getTo()), recipientsObject, TO_TAG);
            addJsonArray(BaseSerializer.toJsonStringArray(email.getRecipients().getCc()), recipientsObject, CC_TAG);
            addJsonArray(BaseSerializer.toJsonStringArray(email.getRecipients().getBcc()), recipientsObject, BCC_TAG);
            addJsonObject(recipientsObject, emailObject, RECIPIENTS_TAG);
        }
        addJsonStringValue(email.getUrl(), emailObject, URL_TAG);
        addJsonStringValue(email.getSubject(), emailObject, SUBJECT_TAG);
        addJsonStringValue(email.getPlainContent(), emailObject, PLAIN_CONTENT_TAG);
        addJsonStringValue(email.getHtmlContent(), emailObject, HTML_CONTENT_TAG);
        addJsonStringValue(email.getStatus().toString(), emailObject, STATUS_TAG);
        addJsonBooleanValue(email.isIncoming(), emailObject, INCOMING_EMAIL_ATG);
        addJsonArray(AttachmentSerializer.toJsonArray(email.getAttachments()), emailObject, ATTACHMENTS_TAG);
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
