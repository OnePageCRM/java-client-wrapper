package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.CloseSalesCycle;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.SalesCycleClosure;
import com.onepagecrm.models.serializers.CloseSalesCycleSerializer;
import com.onepagecrm.models.serializers.ContactListSerializer;
import com.onepagecrm.models.serializers.ContactPhotoSerializer;
import com.onepagecrm.models.serializers.ContactSerializer;
import com.onepagecrm.models.serializers.ContactSplitSerializer;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class Contact extends ApiResource implements Serializable {

    private static final Logger LOG = Logger.getLogger(Contact.class.getSimpleName());

    private static final long serialVersionUID = -6073805195226829625L;

    /*
     * Constants.
     */

    public static final String TYPE_INDIVIDUAL = "individual";
    public static final String TYPE_COMPANY = "company";

    public static final String EXTRA_FIELDS = "fields=all,deals(all),notes(all),calls(all)";
    public static final String UNDO = "undo=1";

    /*
     * Member variables.
     */

    private int intId;
    public static int nextIntId = 1;

    private String id;
    private String ownerId;
    private String title;
    private String firstName;
    private String lastName;
    private String letter;
    private String photoUrl;
    private String jobTitle;
    private String background;
    private List<Url> urls;
    private List<Phone> phones;
    private List<Email> emails;
    private String status;
    private String statusId;
    private Boolean starred;
    private String leadSourceId;
    private Boolean hasPendingDeal;
    private Double totalPending;
    private int totalDealsCount;
    private String type;
    private String companyName;
    private String companyId;
    private Map<String, SalesCycleClosure> salesClosedFor;
    private List<Tag> tags;
    private List<CustomField> customFields;
    private Instant createdAt;
    private Instant modifiedAt;
    private Address address;
    private List<Action> actions;
    private Action nextAction;
    private List<Deal> deals;
    private List<Note> notes;
    private List<Call> calls;
    private Company company;
    private List<String> linkedWithIds;
    private String linkedWithName;

    /*
     * API methods.
     */

    public Contact save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Contact create() throws OnePageException {
        Request request = new PostRequest(
                CONTACTS_ENDPOINT,
                null,
                ContactSerializer.toJsonObject(this)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Contact contact = ContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return contact;
    }

    private Contact update() throws OnePageException {
        Request request = new PutRequest(
                withId(CONTACTS_ENDPOINT),
                "?" + EXTRA_FIELDS,
                ContactSerializer.toJsonObject(this)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Contact contact = ContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return contact;
    }

    public static Contact byId(String id) throws OnePageException {
        Request request = new GetRequest(
                withId(CONTACTS_ENDPOINT, id),
                "?" + EXTRA_FIELDS
        );
        Response response = request.send();
        return ContactSerializer.fromString(response.getResponseBody());
    }

    public static ContactList byIds(String ids) throws OnePageException {
        Request request = new GetRequest(
                withId(MULTIPLE_CONTACTS_ENDPOINT, ids),
                "?" + EXTRA_FIELDS
        );
        Response response = request.send();
        return ContactListSerializer.fromString(response.getResponseBody());
    }

    public Contact partial(Contact updateValues) throws OnePageException {
        Request request = new PutRequest(
                withId(CONTACTS_ENDPOINT),
                "?" + QUERY_PARTIAL + "&" + EXTRA_FIELDS,
                ContactSerializer.toJsonObject(updateValues)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Contact contact = ContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return contact;
    }

    public Contact addPhoto(String base64EncodedImageString) throws OnePageException {
        Request request = new PutRequest(
                CONTACT_PHOTO_ENDPOINT.replace("{id}", this.getId()),
                null,
                ContactPhotoSerializer.toJsonObject(base64EncodedImageString)
        );
        Response response = request.send();
        Contact photoContact = ContactPhotoSerializer.fromString(response.getResponseBody());
        if (this.isValid() && photoContact.photoUrl != null && !photoContact.photoUrl.equals("")) {
            this.photoUrl = photoContact.photoUrl;
        }
        return this;
    }

    public Contact closeSalesCycle(CloseSalesCycle closeSalesCycle) throws OnePageException {
        Request request = new PutRequest(
                CLOSE_SALES_CYCLE_ENDPOINT.replace("{id}", this.getId()),
                null,
                CloseSalesCycleSerializer.toJsonObject(closeSalesCycle)
        );
        Response response = request.send();
        return ContactSerializer.fromString(response.getResponseBody());
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(withId(CONTACTS_ENDPOINT), null);
        Response response = request.send();
        String responseBody = response.getResponseBody();
        DeleteResult deleteResult = DeleteResultSerializer.fromString(this.id, responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return deleteResult;
    }

    public Contact undoDeletion() throws OnePageException {
        Request request = new DeleteRequest(withId(CONTACTS_ENDPOINT), "?" + UNDO);
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Contact contact = ContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return contact;
    }

    public Contact starContact() throws OnePageException {
        Request request = new PutRequest(STAR_CONTACT_ENDPOINT.replace("{id}", this.getId()));
        Response response = request.send();
        return ContactSerializer.fromString(response.getResponseBody());
    }

    public Contact unStarContact() throws OnePageException {
        Request request = new PutRequest(UNSTAR_CONTACT_ENDPOINT.replace("{id}", this.getId()));
        Response response = request.send();
        return ContactSerializer.fromString(response.getResponseBody());
    }

    public Contact split(String newCompanyName) throws OnePageException {
        Request request = new PutRequest(
                SPLIT_CONTACT_ENDPOINT.replace("{id}", this.getId()),
                null,
                ContactSplitSerializer.toJsonObject(newCompanyName)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        Contact contact = ContactSerializer.fromString(responseBody);
        LoginSerializer.updateDynamicResources(responseBody);
        return contact;
    }

    public Contact getEmailMessages() throws OnePageException {
        Request request = new PutRequest(CONTACT_EMAILS_ENDPOINT.replace("{id}", this.getId()));
        Response response = request.send();
        return ContactSerializer.fromString(response.getResponseBody());
    }

    /*
     * Utility methods.
     */

    public String getSimpleName() {
        String simple = "";
        if (notNullOrEmpty(firstName)) {
            simple += firstName;
        }
        if (notNullOrEmpty(lastName)) {
            simple += " ";
            simple += lastName.substring(0, 1) + ".";
        }
        if (notNullOrEmpty(simple)) {
            return simple;
        }
        return null;
    }

    public String getFullName() {
        String full = "";
        if (notNullOrEmpty(firstName)) {
            full += firstName;
        }
        if (notNullOrEmpty(lastName)) {
            full += " ";
            full += lastName;
        }
        if (notNullOrEmpty(full)) {
            return full;
        }
        return null;
    }

    public String getFullAlphaName() {
        String fullAlpha = "";
        if (notNullOrEmpty(lastName)) {
            fullAlpha += lastName;
        }
        if (notNullOrEmpty(firstName)) {
            fullAlpha += " ";
            fullAlpha += firstName;
        }
        if (notNullOrEmpty(fullAlpha)) {
            return fullAlpha;
        }
        return null;
    }

    /*
     * Object methods.
     */

    public Contact() {
        this.intId = nextIntId;
        nextIntId++;
    }

    @Override
    public String toString() {
        return ContactSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Contact setId(String id) {
        this.id = id;
        return this;
    }

    public Contact setIntId(int intId) {
        this.intId = intId;
        return this;
    }

    public int getIntId() {
        return intId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Contact setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Contact setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLetter() {
        return letter;
    }

    public Contact setLetter(String letter) {
        this.letter = letter;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Contact setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Contact setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getBackground() {
        return background;
    }

    public Contact setBackground(String background) {
        this.background = background;
        return this;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public Contact setUrls(List<Url> urls) {
        this.urls = urls;
        return this;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public Contact setPhones(List<Phone> phones) {
        this.phones = phones;
        return this;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public Contact setEmails(List<Email> emails) {
        this.emails = emails;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Contact setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusId() {
        return statusId;
    }

    public Contact setStatusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public Boolean getStarred() {
        return starred;
    }

    public boolean isStarred() {
        return starred != null && starred;
    }

    public Contact setStarred(Boolean starred) {
        this.starred = starred;
        return this;
    }

    public String getLeadSourceId() {
        return leadSourceId;
    }

    public Contact setLeadSourceId(String leadSourceId) {
        this.leadSourceId = leadSourceId;
        return this;
    }

    public boolean hasPendingDeal() {
        return hasPendingDeal != null && hasPendingDeal;
    }

    public Boolean getHasPendingDeal() {
        return hasPendingDeal;
    }

    public Contact setHasPendingDeal(Boolean hasPendingDeal) {
        this.hasPendingDeal = hasPendingDeal;
        return this;
    }

    public Double getTotalPending() {
        return totalPending;
    }

    public Contact setTotalPending(Double totalPending) {
        this.totalPending = totalPending;
        return this;
    }

    public int getTotalDealsCount() {
        return totalDealsCount;
    }

    public Contact setTotalDealsCount(int totalDealsCount) {
        this.totalDealsCount = totalDealsCount;
        return this;
    }

    public String getType() {
        return type;
    }

    public Contact setType(String type) {
        this.type = type;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Contact setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public Contact setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public Map<String, SalesCycleClosure> getSalesClosedFor() {
        return salesClosedFor;
    }

    public Contact setSalesClosedFor(Map<String, SalesCycleClosure> salesClosedFor) {
        this.salesClosedFor = salesClosedFor;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Contact setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public Contact setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getCreatedAt(ZoneId zoneId) {
        return createdAt != null ? ZonedDateTime.ofInstant(createdAt, zoneId) : null;
    }

    public Contact setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public ZonedDateTime getModifiedAt(ZoneId zoneId) {
        return modifiedAt != null ? ZonedDateTime.ofInstant(modifiedAt, zoneId) : null;
    }

    public Contact setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Contact setAddress(Address address) {
        this.address = address;
        return this;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Contact setActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public Contact setNextAction(Action nextAction) {
        this.nextAction = nextAction;
        return this;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public Contact setDeals(List<Deal> deals) {
        this.deals = deals;
        return this;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Contact setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public Contact setCalls(List<Call> calls) {
        this.calls = calls;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public Contact setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Contact setLinkedWithName(String linkedWithName) {
        this.linkedWithName = linkedWithName;
        return this;
    }

    public String getLinkedWithName() {
        return linkedWithName;
    }

    public List<String> getLinkedWithIds() {
        return linkedWithIds;
    }

    public Contact setLinkedWithIds(List<String> linkedWithIds) {
        this.linkedWithIds = linkedWithIds;
        return this;
    }

    public Contact setLinkedWithId(String linkedWithId) {
        if (this.linkedWithIds == null) this.linkedWithIds = new ArrayList<>();
        this.linkedWithIds.add(linkedWithId);
        return this;
    }
}
