package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.CompanyListSerializer;
import com.onepagecrm.models.serializers.CompanySerializer;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.models.serializers.LinkedContactSerializer;
import com.onepagecrm.models.serializers.LinkedContactsSerializer;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 15/12/2016.
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue", "UnusedReturnValue"})
public class Company extends ApiResource implements Serializable {

    private String id;
    private String name;
    private String description;
    private String phone;
    private String url;
    private List<CustomField> companyFields;
    private Address address;
    private Integer wonDealsCount;
    private Double totalWonAmount;
    private Integer pendingDealsCount;
    private Double totalPendingAmount;
    private Integer contactsCount;
    private ContactList contacts;
    private DealList pendingDeals;
    private Date createdAt;
    private Date modifiedAt;
    private Boolean syncingStatus;
    private String syncedStatusId;
    private Boolean syncingTags;
    private List<Tag> syncedTags;

    public Company save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Company create() throws OnePageException {
        Request request = new PostRequest(
                COMPANIES_ENDPOINT,
                null,
                CompanySerializer.toJsonObject(this)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Company company = CompanySerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return company;
    }

    private Company update() throws OnePageException {
        Request request = new PutRequest(
                addIdToEndpoint(COMPANIES_ENDPOINT, this.id),
                null,
                CompanySerializer.toJsonObject(this)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Company company = CompanySerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return company;
    }

    public static Company byId(String companyId) throws OnePageException {
        Request request = new GetRequest(
                addIdToEndpoint(COMPANIES_ENDPOINT, companyId),
                null
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return CompanySerializer.fromString(responseBody);
    }

    public Company partial(Company updateValues) throws OnePageException {
        Request request = new PutRequest(
                addIdToEndpoint(COMPANIES_ENDPOINT, this.id),
                "?" + QUERY_PARTIAL,
                CompanySerializer.toJsonObject(updateValues)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Company company = CompanySerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return company;
    }

    public static CompanyList list() throws OnePageException {
        return getCompanies(COMPANIES_ENDPOINT, null);
    }

    public static CompanyList list(Map<String, Object> params) throws OnePageException {
        return getCompanies(COMPANIES_ENDPOINT, params != null && !params.isEmpty() ? Query.fromParams(params) : null);
    }

    private static CompanyList getCompanies(String endpoint, String query) throws OnePageException {
        Request request = new GetRequest(endpoint, query);
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return CompanyListSerializer.fromString(responseBody);
    }

    public LinkedContactList getLinkedContacts() throws OnePageException {
        return getLinkedContacts(this.id);
    }

    public static LinkedContactList getLinkedContacts(String companyId) throws OnePageException {
        return getLinkedContacts(companyId, new Paginator());
    }

    public static LinkedContactList getLinkedContacts(String companyId, Paginator paginator) throws OnePageException {
        String endpoint = LINKED_CONTACTS_ENDPOINT.replace("{id}", companyId);
        Request request = new GetRequest(endpoint, Query.query(paginator));
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return LinkedContactsSerializer.fromString(responseBody);
    }

    public static LinkedContact linkContact(String companyId, String contactId) throws OnePageException {
        return linkContact(companyId, contactId, null);
    }

    public static LinkedContact linkContact(String companyId, String contactId, String linkedWithId) throws OnePageException {
        String endpoint = LINKED_CONTACTS_ENDPOINT.replace("{id}", companyId);
        Request request = new PostRequest(
                endpoint,
                null,
                CompanySerializer.toJsonObject(contactId, linkedWithId)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        LinkedContact linkedContact = LinkedContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return linkedContact;
    }

    public static DeleteResult unlinkContact(String companyId, String contactId) throws OnePageException {
        String endpoint = LINKED_CONTACTS_ENDPOINT.replace("{id}", companyId);
        Request request = new DeleteRequest(addIdToEndpoint(endpoint, contactId), null);
        Response response = request.send();
        String responseBody = response.getResponseBody();
        DeleteResult deleteResult = DeleteResultSerializer.fromString(contactId, responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return deleteResult;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Company setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return CompanySerializer.toJsonObject(this);
    }

    public boolean dataToLoseWithDeletion() {
        return isValid() && (notNullOrEmpty(description) ||
                notNullOrEmpty(phone) ||
                notNullOrEmpty(url) ||
                companyFields != null && !companyFields.isEmpty() ||
                address != null && address.isValid());
    }

    public boolean multiContact() {
        return contactsCount != null && contactsCount > 1;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Company setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Company setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Company setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<CustomField> getCompanyFields() {
        return companyFields;
    }

    public Company setCompanyFields(List<CustomField> companyFields) {
        this.companyFields = companyFields;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Company setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Integer getWonDealsCount() {
        return wonDealsCount;
    }

    public Company setWonDealsCount(Integer wonDealsCount) {
        this.wonDealsCount = wonDealsCount;
        return this;
    }

    public Double getTotalWonAmount() {
        return totalWonAmount;
    }

    public Company setTotalWonAmount(Double totalWonAmount) {
        this.totalWonAmount = totalWonAmount;
        return this;
    }

    public Integer getPendingDealsCount() {
        return pendingDealsCount;
    }

    public Company setPendingDealsCount(Integer pendingDealsCount) {
        this.pendingDealsCount = pendingDealsCount;
        return this;
    }

    public Double getTotalPendingAmount() {
        return totalPendingAmount;
    }

    public Company setTotalPendingAmount(Double totalPendingAmount) {
        this.totalPendingAmount = totalPendingAmount;
        return this;
    }

    public Integer getContactsCount() {
        return contactsCount;
    }

    public Company setContactsCount(Integer contactsCount) {
        this.contactsCount = contactsCount;
        return this;
    }

    public ContactList getContacts() {
        return contacts;
    }

    public Company setContacts(ContactList contacts) {
        this.contacts = contacts;
        return this;
    }

    public DealList getPendingDeals() {
        return pendingDeals;
    }

    public Company setPendingDeals(DealList pendingDeals) {
        this.pendingDeals = pendingDeals;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Company setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Company setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Boolean getSyncingStatus() {
        return syncingStatus;
    }

    public boolean isSyncingStatus() {
        return syncingStatus != null && syncingStatus;
    }

    public Company setSyncingStatus(Boolean syncingStatus) {
        this.syncingStatus = syncingStatus;
        return this;
    }

    public String getSyncedStatusId() {
        return syncedStatusId;
    }

    public Company setSyncedStatusId(String syncedStatusId) {
        this.syncedStatusId = syncedStatusId;
        return this;
    }

    public Boolean getSyncingTags() {
        return syncingTags;
    }

    public boolean isSyncingTags() {
        return syncingTags != null && syncingTags;
    }

    public Company setSyncingTags(Boolean syncingTags) {
        this.syncingTags = syncingTags;
        return this;
    }

    public List<Tag> getSyncedTags() {
        return syncedTags;
    }

    public Company setSyncedTags(List<Tag> syncedTags) {
        this.syncedTags = syncedTags;
        return this;
    }
}
