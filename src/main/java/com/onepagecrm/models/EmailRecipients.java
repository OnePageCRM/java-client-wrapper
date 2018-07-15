package com.onepagecrm.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anton S. on 05/07/2018
 */

public class EmailRecipients implements Serializable {

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;

    public List<String> getTo() {
        return to;
    }

    public EmailRecipients setTo(List<String> to) {
        this.to = to;
        return this;
    }

    public List<String> getCc() {
        return cc;
    }

    public EmailRecipients setCc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public EmailRecipients setBcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }
}
