package com.onepagecrm.models;

import java.io.Serializable;
import java.util.Objects;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * Created by Nichollas on 27/10/17.
 */
public class StartupData implements Serializable {

    private final boolean fullResponse;
    private final String endpointUrl;
    private final User user;
    private final ContactList stream;
    private final ContactList contacts;
    private final DealList deals;

    public StartupData(String endpointUrl, User user) {
        this.fullResponse = false;
        this.endpointUrl = endpointUrl;
        this.user = user;
        this.stream = null;
        this.contacts = null;
        this.deals = null;
    }

    public StartupData(String endpointUrl, User user, ContactList stream, ContactList contacts, DealList deals) {
        this.fullResponse = true;
        this.endpointUrl = endpointUrl;
        this.user = user;
        this.stream = stream;
        this.contacts = contacts;
        this.deals = deals;
    }

    @Override
    public String toString() {
        return "StartupData{" +
                "fullResponse=" + fullResponse +
                ", endpointUrl='" + endpointUrl + '\'' +
                ", user=" + user +
                ", stream=" + stream +
                ", contacts=" + contacts +
                ", deals=" + deals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartupData that = (StartupData) o;
        return fullResponse == that.fullResponse &&
                Objects.equals(endpointUrl, that.endpointUrl) &&
                Objects.equals(user, that.user) &&
                Objects.equals(stream, that.stream) &&
                Objects.equals(contacts, that.contacts) &&
                Objects.equals(deals, that.deals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullResponse, endpointUrl, user, stream, contacts, deals);
    }

    public boolean isValid() {
        return notNullOrEmpty(endpointUrl) && user != null;
    }

    public boolean isFullResponse() {
        return fullResponse;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public User getUser() {
        return user;
    }

    public ContactList getStream() {
        return stream;
    }

    public ContactList getContacts() {
        return contacts;
    }

    public DealList getDeals() {
        return deals;
    }
}
