package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.models.serializers.EmailMessageSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cillian Myles on 12/10/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class EmailMessage extends ApiResource implements Serializable {

    /*
     * Constants.
     */

    private static final String TYPE_BCC_EMAIL = "bcc_email";
    private static final String TYPE_EMAIL_SEND = "email_send";
    private static final String TYPE_OTHER = "other"; // Catch all.

    private static final String STATUS_WAITING = "waiting";
    private static final String STATUS_SENT = "sent";
    private static final String STATUS_FAILED = "failed";
    private static final String STATUS_OTHER = "other"; // Catch all.

    /*
     * Member variables.
     */

    public enum Type {
        BCC_EMAIL(TYPE_BCC_EMAIL),
        EMAIL_SEND(TYPE_EMAIL_SEND),
        OTHER(TYPE_OTHER);

        private String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static Type fromString(final String type) {
            if (type == null) return null;
            switch (type) {
                case TYPE_BCC_EMAIL:
                    return BCC_EMAIL;
                case TYPE_EMAIL_SEND:
                    return EMAIL_SEND;
                default:
                    // Manually set status so we know what API sent (if error)!
                    OTHER.type = type;
                    return OTHER;
            }
        }
    }

    public enum Status {
        WAITING(STATUS_WAITING),
        SENT(STATUS_SENT),
        FAILED(STATUS_FAILED),
        OTHER(STATUS_OTHER);

        private String status;

        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }

        public static Status fromString(final String status) {
            if (status == null) return null;
            switch (status) {
                case STATUS_WAITING:
                    return WAITING;
                case STATUS_SENT:
                    return SENT;
                case STATUS_FAILED:
                    return FAILED;
                default:
                    // Manually set status so we know what API sent (if error)!
                    OTHER.status = status;
                    return OTHER;
            }
        }
    }

    private String id;
    private Type type;
    private List<String> contactIds;
    private Instant sendTime;
    private String messageId;
    private String sender;
    private EmailRecipients recipients;
    private String url;
    private String subject;
    private String plainContent;
    private String htmlContent;
    private Status status;
    private Boolean incoming;
    private List<Attachment> attachments;

    /*
     * API methods.
     */

    public static List<EmailMessage> list(String contactId) throws OnePageException {
        Request request = new GetRequest(CONTACT_EMAILS_ENDPOINT.replace("{id}", contactId));
        Response response = request.send();
        return EmailMessageSerializer.listFromString(response.getResponseBody());
    }

    public static EmailMessage byId(String contactId, String emailId) throws OnePageException {
        Request request = new GetRequest(withId(CONTACT_EMAILS_ENDPOINT.replace("{id}", contactId), emailId));
        Response response = request.send();
        return EmailMessageSerializer.singleFromString(response.getResponseBody());
    }

    public DeleteResult delete(String contactId) throws OnePageException {
        Request request = new DeleteRequest(withId(CONTACT_EMAILS_ENDPOINT.replace("{id}", contactId)));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    /*
     * Utility methods.
     */

    public boolean isIncoming() {
        return incoming != null && incoming;
    }

    public boolean hasCC() {
        return hasRecipients() && recipients.getCc() != null && !recipients.getCc().isEmpty();
    }

    public boolean hasBCC() {
        return hasRecipients() && recipients.getBcc() != null && !recipients.getBcc().isEmpty();
    }

    public boolean shouldGetHtmlContent() {
        return needsHtmlContent() && canGetHtmlContent();
    }

    public boolean needsHtmlContent() {
        return !hasHtmlContent();
    }

    public boolean canGetHtmlContent() {
        return url != null && !url.isEmpty();
    }

    public boolean hasRecipients() {
        return recipients != null;
    }

    public boolean hasContent() {
        return hasPlainContent() || hasHtmlContent();
    }

    public boolean hasPlainContent() {
        return plainContent != null && !plainContent.isEmpty();
    }

    public boolean hasHtmlContent() {
        return htmlContent != null && !htmlContent.isEmpty();
    }

    /*
     * Object methods.
     */

    @Override
    public String toString() {
        return EmailMessageSerializer.toJsonString(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EmailMessage setId(String id) {
        this.id = id;
        return this;
    }

    public Type getType() {
        return type;
    }

    public EmailMessage setType(Type type) {
        this.type = type;
        return this;
    }

    public List<String> getContactIds() {
        return contactIds;
    }

    public EmailMessage setContactIds(List<String> contactIds) {
        this.contactIds = contactIds;
        return this;
    }

    public Instant getSendTime() {
        return sendTime;
    }

    public EmailMessage setSendTime(Instant sendTime) {
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

    public String getUrl() {
        return url;
    }

    public EmailMessage setUrl(String url) {
        this.url = url;
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

    public String getHtmlContent() {
        return htmlContent;
    }

    public EmailMessage setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public EmailMessage setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Boolean getIncoming() {
        return incoming;
    }

    public EmailMessage setIncoming(Boolean incoming) {
        this.incoming = incoming;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public EmailMessage setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }
}
