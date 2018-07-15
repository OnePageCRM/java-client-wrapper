package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.Request;
import java.io.Serializable;



/**
 * Created by Anton S. on 05/07/2018
 */

public class EmailMessage extends ApiResource implements Serializable {

    private String id;
    private String contactId;
    private String sendTime;
    private String messageId;
    private String sender;

    private EmailRecipients recipients;
    private String subject;
    private String plainContent;
    private String status;

    public DeleteResult delete() throws OnePageException {
        // todo fix endpoint
        Request request = new DeleteRequest(withId(CONTACT_EMAILS_ENDPOINT.replace("{id}", contactId)));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    public String getId() {
        return id;
    }

    public EmailMessage setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return null;
    }

    public String getContactId() {
        return contactId;
    }

    public EmailMessage setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getSendTime() {
        return sendTime;
    }

    public EmailMessage setSendTime(String sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public EmailMessage setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public EmailMessage setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public EmailRecipients getRecipients() {
        return recipients;
    }

    public EmailMessage setRecipients(EmailRecipients recipients) {
        this.recipients = recipients;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public EmailMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getPlainContent() {
        return plainContent;
    }

    public EmailMessage setPlainContent(String plainContent) {
        this.plainContent = plainContent;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EmailMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean isValid() {
        return plainContent != null && !plainContent.isEmpty();
    }
}
