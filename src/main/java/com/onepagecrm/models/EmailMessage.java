package com.onepagecrm.models;

import java.io.Serializable;

/**
 * Created by Anton S. on 05/07/2018
 */

public class EmailMessage implements Serializable {

    private String id;
    private String contactId;
    private String sendTime;
    private String messageId;
    private String sender;

    private EmailRecipients recipients;
    private String subject;
    private String plainContent;
    private String status;

    public String getId() {
        return id;
    }

    public EmailMessage setId(String id) {
        this.id = id;
        return this;
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
