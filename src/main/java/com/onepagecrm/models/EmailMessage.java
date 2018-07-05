package com.onepagecrm.models;

/**
 * Created by Anton S. on 05/07/2018
 */

public class EmailMessage {

    private String id;
    private String contactId;
    private String sendTime;
    private String messageId;
    private String sender;

    private EmailRecipients recipients;
    private String subject;
    private String plain_content;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public EmailRecipients getRecipients() {
        return recipients;
    }

    public void setRecipients(EmailRecipients recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlain_content() {
        return plain_content;
    }

    public void setPlain_content(String plain_content) {
        this.plain_content = plain_content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
