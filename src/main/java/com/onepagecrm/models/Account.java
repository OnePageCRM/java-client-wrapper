package com.onepagecrm.models;

import com.onepagecrm.models.internal.ContactsCount;
import com.onepagecrm.models.internal.PredefinedActionList;
import com.onepagecrm.models.internal.Settings;
import com.onepagecrm.models.internal.StreamCount;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

    public static final String USER_ID = "account_id";

    public static List<User> team;
    public static User currentUser;
    public static Settings settings;
    public Countries countries;

    public List<CustomField> customFields;
    public List<CustomField> companyFields;
    public List<CustomField> dealFields;

    public List<Filter> filters;
    public List<CallResult> callResults;
    public List<Tag> tags;
    public List<Status> statuses;
    public List<LeadSource> leadSources;

    public StreamCount streamCount;
    public ContactsCount contactsCount;

    public PredefinedActionList predefinedActions;
    public List<String> contactTitles;

    public Account() {

    }

    public static List<User> getTeam() {
        return team;
    }

    public Account setTeam(List<User> team) {
        Account.team = team;
        return this;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public Account setCurrentUser(User currentUser) {
        Account.currentUser = currentUser;
        return this;
    }

    public static Settings getSettings() {
        return settings;
    }

    public Account setSettings(Settings settings) {
        Account.settings = settings;
        return this;
    }

    public Countries getCountries() {
        return countries;
    }

    public Account setCountries(Countries countries) {
        this.countries = countries;
        return this;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public Account setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
        return this;
    }

    public List<CallResult> getCallResults() {
        return callResults;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public Account setFilters(List<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public Account setCallResults(List<CallResult> callResults) {
        this.callResults = callResults;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Account setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public Account setStatuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public List<LeadSource> getLeadSources() {
        return leadSources;
    }

    public Account setLeadSources(List<LeadSource> leadSources) {
        this.leadSources = leadSources;
        return this;
    }

    public StreamCount getStreamCount() {
        return streamCount;
    }

    public Account setStreamCount(StreamCount streamCount) {
        this.streamCount = streamCount;
        return this;
    }

    public ContactsCount getContactsCount() {
        return contactsCount;
    }

    public Account setContactsCount(ContactsCount contactsCount) {
        this.contactsCount = contactsCount;
        return this;
    }

    public PredefinedActionList getPredefinedActions() {
        return predefinedActions;
    }

    public Account setPredefinedActions(PredefinedActionList predefinedActions) {
        this.predefinedActions = predefinedActions;
        return this;
    }

    public List<String> getContactTitles() {
        return contactTitles;
    }

    public Account setContactTitles(List<String> contactTitles) {
        this.contactTitles = contactTitles;
        return this;
    }

    public List<CustomField> getCompanyFields() {
        return companyFields;
    }

    public Account setCompanyFields(List<CustomField> companyFields) {
        this.companyFields = companyFields;
        return this;
    }

    public List<CustomField> getDealFields() {
        return dealFields;
    }

    public Account setDealFields(List<CustomField> dealFields) {
        this.dealFields = dealFields;
        return this;
    }
}
