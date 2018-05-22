package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.CustomFieldValue;
import com.onepagecrm.models.serializers.CustomFieldSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomField extends ApiResource implements Serializable {

    /*
     * Constants.
     */

    public static final String CF_TYPE_CONTACT = "contact";
    public static final String CF_TYPE_COMPANY = "company";
    public static final String CF_TYPE_DEAL = "deal";

    public static final String TYPE_ANNIVERSARY = "anniversary";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_DROPDOWN = "select_box";
    public static final String TYPE_MULTI_LINE_TEXT = "multi_line_text";
    public static final String TYPE_MULTIPLE_CHOICE = "multiple_choice";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_SINGLE_LINE_TEXT = "single_line_text";

    /*
     * Member variables.
     */

    private String id;
    private String name;
    private Integer position;
    private String type;
    private String cfType;
    private List<String> choices;
    private Integer reminderDays;
    private CustomFieldValue value;

    /*
     * API methods.
     */

    public String save() throws OnePageException {
        Request request = new PostRequest(
                CUSTOM_FIELDS_ENDPOINT,
                null,
                CustomFieldSerializer.toJsonObjectNew(this)
        );
        Response response = request.send();
        return response.getResponseBody();
    }

    // TODO: add create and update methods

    // TODO: save method to call create or update as needed

    public static List<CustomField> listContacts() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("per_page", 100);
        Request request = new GetRequest(
                CUSTOM_FIELDS_ENDPOINT,
                Query.fromParams(params)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return CustomFieldSerializer.fromString(responseBody, CF_TYPE_CONTACT);
    }

    public static List<CustomField> listCompanies() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("per_page", 100);
        Request request = new GetRequest(
                COMPANY_FIELDS_ENDPOINT,
                Query.fromParams(params)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return CustomFieldSerializer.fromString(responseBody, CF_TYPE_COMPANY);
    }

    public static List<CustomField> listDeals() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("per_page", 100);
        Request request = new GetRequest(
                DEAL_FIELDS_ENDPOINT,
                Query.fromParams(params)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return CustomFieldSerializer.fromString(responseBody, CF_TYPE_DEAL);
    }

    /*
     * Object methods.
     */

    @SuppressWarnings("CopyConstructorMissesField")
    public CustomField(CustomField customField) {
        this.id = customField.getId();
        this.name = customField.getName();
        this.position = customField.getPosition();
        this.type = customField.getType();
        this.choices = customField.getChoices();
        this.reminderDays = customField.getReminderDays();
        this.value = customField.getValue();
    }

    public CustomField() {

    }

    @Override
    public String toString() {
        return CustomFieldSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public CustomField setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomField setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public CustomField setPosition(Integer position) {
        this.position = position;
        return this;
    }

    public String getType() {
        return type;
    }

    public CustomField setType(String type) {
        this.type = type;
        return this;
    }

    public String getCfType() {
        return cfType;
    }

    public CustomField setCfType(String cfType) {
        this.cfType = cfType;
        return this;
    }

    public List<String> getChoices() {
        return choices;
    }

    public CustomField setChoices(List<String> choices) {
        this.choices = choices;
        return this;
    }

    public Integer getReminderDays() {
        return reminderDays;
    }

    public CustomField setReminderDays(Integer reminderDays) {
        this.reminderDays = reminderDays;
        return this;
    }

    public CustomFieldValue getValue() {
        return value;
    }

    public CustomField setValue(CustomFieldValue value) {
        this.value = value;
        return this;
    }
}
