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
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.Serializable;
import java.util.ArrayList;
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

    /*
     * Constants.
     */

    public static final String STATUS_WON = "won";
    public static final String STATUS_LOST = "lost";
    public static final String STATUS_PENDING = "pending";
    public static final String TYPE_CONTACT = "contact";
    public static final String TYPE_COMPANY = "company";
    public static final String RELATED_NOTES_FIELDS = "fields=notes(all)";

    /*
     * Member variables.
     */

    private String id;
    private String contactId;
    private String ownerId;
    private String name;
    private Double amount;
    private Integer months;
    private Double cost;
    private Double totalAmount;
    private Double totalCost;
    private Double margin;
    private Commission.Base commissionBase;
    private Commission.Type commissionType;
    private Double commission;
    private Double commissionPercentage;
    private LocalDate expectedCloseDate;
    private LocalDate closeDate;
    private LocalDate date;
    private Integer stage;
    private String status;
    private String text;
    private List<CustomField> dealFields;

    // Read-only members.
    private String author;
    private Instant createdAt;
    private Instant modifiedAt;
    private Boolean hasRelatedNotes;
    private ContactInfo contactInfo;
    private List<Note> relatedNotes;
    private List<Attachment> attachments;

    /*
     * API methods.
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
                withId(DEALS_ENDPOINT, this.id),
                null,
                DealSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return DealSerializer.fromString(response.getResponseBody());
    }

    public static Deal byId(String dealId) throws OnePageException {
        Request request = new GetRequest(
                withId(DEALS_ENDPOINT, dealId),
                "?" + RELATED_NOTES_FIELDS
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
        Request request = new PutRequest(
                withId(DEALS_ENDPOINT, this.id),
                "?" + QUERY_PARTIAL,
                DealSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return DealSerializer.fromString(response.getResponseBody());
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(withId(DEALS_ENDPOINT, this.id));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    public List<Note> relatedNotes() throws OnePageException {
        Request request = new GetRequest(withId(DEALS_ENDPOINT, this.id), "?" + RELATED_NOTES_FIELDS);
        Response response = request.send();
        Deal deal = DealSerializer.fromString(response.getResponseBody());
        return deal.hasRelatedNotes() ? deal.getRelatedNotes() : new ArrayList<>();
    }

    /*
     * Utility methods.
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

    public boolean hasAttachments() {
        return this.attachments != null && !attachments.isEmpty();
    }

    /*
     * Object methods.
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

    public String getContactId() {
        return contactId;
    }

    public Deal setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Deal setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Deal setName(String name) {
        this.name = name;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public Deal setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Integer getMonths() {
        return months;
    }

    public Deal setMonths(Integer months) {
        this.months = months;
        return this;
    }

    public Double getCost() {
        return cost;
    }

    public Deal setCost(Double cost) {
        this.cost = cost;
        return this;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Deal setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Deal setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public Double getMargin() {
        return margin;
    }

    public Deal setMargin(Double margin) {
        this.margin = margin;
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

    public Double getCommission() {
        return commission;
    }

    public Deal setCommission(Double commission) {
        this.commission = commission;
        return this;
    }

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public Deal setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
        return this;
    }

    public LocalDate getExpectedCloseDate() {
        return expectedCloseDate;
    }

    public Deal setExpectedCloseDate(LocalDate expectedCloseDate) {
        this.expectedCloseDate = expectedCloseDate;
        return this;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public Deal setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Deal setDate(LocalDate date) {
        this.date = date;
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

    public String getText() {
        return text;
    }

    public Deal setText(String text) {
        this.text = text;
        return this;
    }

    public List<CustomField> getDealFields() {
        return dealFields;
    }

    public Deal setDealFields(List<CustomField> dealFields) {
        this.dealFields = dealFields;
        return this;
    }

    // Read-only accessors.

    public String getAuthor() {
        return author;
    }

    public Deal setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getCreatedAt(ZoneId zoneId) {
        return createdAt != null ? ZonedDateTime.ofInstant(createdAt, zoneId) : null;
    }

    public Deal setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public ZonedDateTime getModifiedAt(ZoneId zoneId) {
        return modifiedAt != null ? ZonedDateTime.ofInstant(modifiedAt, zoneId) : null;
    }

    public Deal setModifiedAt(Instant modifiedAt) {
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Deal setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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
}
