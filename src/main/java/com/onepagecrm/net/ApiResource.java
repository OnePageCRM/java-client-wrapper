package com.onepagecrm.net;

import com.onepagecrm.models.BaseResource;

import java.io.Serializable;

/**
 * Created by Cillian Myles on 31/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "unused", "SpellCheckingInspection"})
public abstract class ApiResource extends BaseResource implements Serializable {

    public static final String CHARSET = "UTF-8";

    public static final String ACTION_STREAM_ENDPOINT = "action_stream";
    public static final String CONTACTS_ENDPOINT = "contacts";
    public static final String MULTIPLE_CONTACTS_ENDPOINT = "contacts/show_multiple";
    public static final String CLOSE_SALES_CYCLE_ENDPOINT = "contacts/{id}/close_sales_cycle";
    public static final String CALLS_ENDPOINT = "calls";
    public static final String CUSTOM_FIELDS_ENDPOINT = "custom_fields";
    public static final String COUNTRIES_ENDPOINT = "countries";
    public static final String TAGS_ENDPOINT = "tags";
    public static final String DEALS_ENDPOINT = "deals";
    public static final String TEAM_STREAM_ENDPOINT = "team_stream";
    public static final String ACTIONS_ENDPOINT = "actions";
    public static final String MARK_COMPLETE_ENDPOINT = "actions/{id}/mark_as_done";
    public static final String UNDO_COMPLETION_ENDPOINT = "actions/{id}/undo_completion";
    public static final String PREDEFINED_ACTIONS_ENDPOINT = "predefined_actions";
    public static final String NOTES_ENDPOINT = "notes";
    public static final String BOOTSTRAP_ENDPOINT = "bootstrap";
    public static final String STARTUP_ENDPOINT = "mobile/startup";
    public static final String DEVICE_ENDPOINT = "firebase";
    public static final String COMPANIES_ENDPOINT = "companies";
    public static final String LINKED_CONTACTS_ENDPOINT = "companies/{id}/linked_contacts";
    public static final String COMPANY_FIELDS_ENDPOINT = "company_fields";
    public static final String DEAL_FIELDS_ENDPOINT = "deal_fields";
    public static final String ATTACHMENTS_ENDPOINT = "attachments";
    public static final String GOOGLE_LOGIN_ENDPOINT = "google_plus/login";
    public static final String GOOGLE_SIGNUP_ENDPOINT = "google_plus/signup";

    public static final String QUERY_PARTIAL = "partial=1";

    public abstract String getId();

    public abstract ApiResource setId(String id);

    @Override
    public abstract String toString();

    /**
     * Method to compare ApiResource's to one another based off of their id.
     */
    public boolean equals(Object object) {
        if (object instanceof ApiResource) {
            ApiResource toCompare = (ApiResource) object;
            if (this.getId() != null && toCompare.getId() != null) {
                return this.getId().equals(toCompare.getId());
            }
        }
        return false;
    }

    public boolean isValid() {
        return this.getId() != null && !this.getId().equals("");
    }

    protected String addIdToEndpoint(String endpoint) {
        return addIdToEndpoint(endpoint, this.getId());
    }

    protected static String addIdToEndpoint(String endpoint, String id) {
        return endpoint + "/" + id;
    }
}
