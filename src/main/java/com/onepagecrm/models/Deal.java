package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Commission;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.models.serializers.DealListSerializer;
import com.onepagecrm.models.serializers.DealSerializer;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PatchRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.onepagecrm.models.internal.Commission.Type.ABSOLUTE;
import static com.onepagecrm.models.internal.Commission.Type.PERCENTAGE;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 22/06/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class Deal extends ApiResource implements Serializable {

    /**
     * Constants
     */

    public static final String STATUS_WON = "won";
    public static final String STATUS_LOST = "lost";
    public static final String STATUS_PENDING = "pending";
    public static final String TYPE_CONTACT = "contact";
    public static final String TYPE_COMPANY = "company";
    public static final String RELATED_NOTES_FIELDS = "?fields=notes(text,author)";

    /**
     * Member variables.
     */

    private String id;
    private Double amount;
    private String author;
    private String text;
    private ContactInfo contactInfo;
    private String contactId;
    private Date createdAt;
    private Date date;
    private Date expectedCloseDate;
    private Integer months;
    private String name;
    private String ownerId;
    private Integer stage;
    private String status;
    private Double totalAmount;
    private Date modifiedAt;
    private Boolean hasRelatedNotes;
    private List<Note> relatedNotes;
    private Date closeDate;
    private List<CustomField> dealFields;
    private Double cost;
    private Double margin;
    private Double totalCost;
    private Double commission;
    private Commission.Base commissionBase;
    private Commission.Type commissionType;
    private Double commissionPercentage;

    /**
     * API methods
     */

    public Deal save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Deal create() throws OnePageException {
        Request request = new PostRequest(
                DEALS_ENDPOINT,
                null,
                DealSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return DealSerializer.fromString(response.getResponseBody());
    }

    private Deal update() throws OnePageException {
        Request request = new PutRequest(
                addDealIdToEndpoint(DEALS_ENDPOINT),
                null,
                DealSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return DealSerializer.fromString(response.getResponseBody());
    }

    public static DealList list(String contactId) throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        return getDeals(params);
    }

    public static DealList list(String contactId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("contact_id", contactId);
        return getDeals(params);
    }

    public static DealList list(String searchType, String entityId, String status) throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        if (searchType.equals(TYPE_CONTACT)) params.put("contact_id", entityId);
        else if (searchType.equals(TYPE_COMPANY)) params.put("company_id", entityId);
        params.put("status", status);
        return getDeals(params);
    }

    public static DealList list(String searchType, String entityId, Paginator paginator, String status) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        if (searchType.equals(TYPE_CONTACT)) params.put("contact_id", entityId);
        else if (searchType.equals(TYPE_COMPANY)) params.put("company_id", entityId);
        params.put("status", status);
        return getDeals(params);
    }

    public static DealList list(Map<String, Object> params) throws OnePageException {
        return getDeals(params);
    }

    public static DealList list(String contactId, Map<String, Object> params) throws OnePageException {
        params.put("contact_id", contactId);
        return getDeals(params);
    }

    public static DealList list(String contactId, Paginator paginator, Map<String, Object> params) throws OnePageException {
        Map<String, Object> tempParams = Query.params(paginator);
        tempParams.put("contact_id", contactId);
        tempParams.putAll(params);
        return getDeals(tempParams);
    }

    private static DealList getDeals(Map<String, Object> params) throws OnePageException {
        GetRequest getRequest = new GetRequest(DEALS_ENDPOINT, Query.fromParams(params));
        Response response = getRequest.send();
        return DealListSerializer.fromString(response.getResponseBody());
    }

    public Deal partial() throws OnePageException {
        Request request = new PatchRequest(
                addDealIdToEndpoint(DEALS_ENDPOINT),
                null,
                DealSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return DealSerializer.fromString(response.getResponseBody());
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(addDealIdToEndpoint(DEALS_ENDPOINT));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    public List<Note> getNotesRelatedToDeal() throws OnePageException {
        List<Note> notes = new ArrayList<>();
        Request request = new GetRequest(addDealIdToEndpoint(DEALS_ENDPOINT), RELATED_NOTES_FIELDS);
        Response response = request.send();
        Deal deal = DealSerializer.fromString(response.getResponseBody());
        if (deal.hasRelatedNotes()) {
            notes = DealSerializer.getNotesFromString(response.getResponseBody());
        }
        return notes;
    }

    private String addDealIdToEndpoint(String endpoint) {
        return endpoint + "/" + this.id;
    }

    /**
     * Utility methods
     */

    public boolean isMulti() {
        return this.months != null && this.months > 1;
    }

    public boolean isPending() {
        return !isClosed();
    }

    public boolean isClosed() {
        return Utilities.notNullOrEmpty(this.status) &&
                (STATUS_LOST.equals(this.status) || STATUS_WON.equals(this.status));
    }

    public boolean hasCommission() {
        return hasCommissionType() &&
                (PERCENTAGE.equals(this.commissionType) || ABSOLUTE.equals(this.commissionType));
    }

    public boolean hasCommissionBase() {
        return this.commissionBase != null;
    }

    public boolean hasCommissionType() {
        return this.commissionType != null;
    }

    /**
     * Object methods
     */

    @Override
    public String toString() {
        return DealSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Deal setId(String id) {
        this.id = id;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public Deal setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Deal setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public Deal setText(String text) {
        this.text = text;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public Deal setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Deal setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Deal setDate(Date date) {
        this.date = date;
        return this;
    }

    public Date getExpectedCloseDate() {
        return expectedCloseDate;
    }

    public Deal setExpectedCloseDate(Date expectedCloseDate) {
        this.expectedCloseDate = expectedCloseDate;
        return this;
    }

    public Integer getMonths() {
        return months;
    }

    public Deal setMonths(Integer months) {
        this.months = months;
        return this;
    }

    public String getName() {
        return name;
    }

    public Deal setName(String name) {
        this.name = name;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Deal setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public Integer getStage() {
        return stage;
    }

    public Deal setStage(Integer stage) {
        this.stage = stage;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Deal setStatus(String status) {
        this.status = status;
        return this;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Deal setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Deal setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Boolean getHasRelatedNotes() {
        return hasRelatedNotes;
    }

    public boolean hasRelatedNotes() {
        return hasRelatedNotes != null && hasRelatedNotes;
    }

    public Deal setHasRelatedNotes(Boolean hasRelatedNotes) {
        this.hasRelatedNotes = hasRelatedNotes;
        return this;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public Deal setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public ContactInfo getContactInfo() {
        return contactInfo == null ? new ContactInfo() : contactInfo;
    }

    public Deal setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        return this;
    }

    public List<Note> getRelatedNotes() {
        return relatedNotes;
    }

    public Deal setRelatedNotes(List<Note> relatedNotes) {
        this.relatedNotes = relatedNotes;
        return this;
    }

    public static class ContactInfo implements Serializable {
        private String contactName;
        private String company;

        public String getContactName() {
            return contactName;
        }

        public ContactInfo setContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public String getCompany() {
            return company;
        }

        public ContactInfo setCompany(String company) {
            this.company = company;
            return this;
        }
    }

    public List<CustomField> getDealFields() {
        return dealFields;
    }

    public Deal setDealFields(List<CustomField> dealFields) {
        this.dealFields = dealFields;
        return this;
    }

    public Double getCost() {
        return cost;
    }

    public Deal setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public Double getMargin() {
        return margin;
    }

    public Deal setMargin(Double margin) {
        this.margin = margin;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Deal setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Double getCommission() {
        return commission;
    }

    public Deal setCommission(Double commission) {
        this.commission = commission;
        return this;
    }

    public Commission.Base getCommissionBase() {
        return commissionBase;
    }

    public Deal setCommissionBase(Commission.Base commissionBase) {
        this.commissionBase = commissionBase;
        return this;
    }

    public Commission.Type getCommissionType() {
        return commissionType;
    }

    public Deal setCommissionType(Commission.Type commissionType) {
        this.commissionType = commissionType;
        return this;
    }

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public Deal setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
        return this;
    }
}
