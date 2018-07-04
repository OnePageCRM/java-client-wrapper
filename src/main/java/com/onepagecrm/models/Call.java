package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.CallListSerializer;
import com.onepagecrm.models.serializers.CallSerializer;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 31/07/2017.
 */
@SuppressWarnings("unused")
public class Call extends ApiResource implements Serializable {

    /*
     * Member variables.
     */

    private String id;
    private String author;
    private CallResult callResult;
    private Instant time;
    private String contactId;
    private String phoneNumber;
    private String via;
    private String recordingLink;
    private String text;
    private List<String> userIdsToNotify;
    private List<Attachment> attachments;
    private Instant createdAt;
    private Instant modifiedAt;

    /*
     * API methods.
     */

    public Call save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Call update() throws OnePageException {
        Request request = new PutRequest(
                withId(CALLS_ENDPOINT),
                null,
                CallSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return CallSerializer.fromString(response.getResponseBody());
    }

    private Call create() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new PostRequest(
                CALLS_ENDPOINT,
                Query.fromParams(params),
                CallSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return CallSerializer.fromString(response.getResponseBody());
    }

    public static Call byId(String id) throws OnePageException {
        Request request = new GetRequest(withId(CALLS_ENDPOINT, id), null);
        Response response = request.send();
        return CallSerializer.fromString(response.getResponseBody());
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(withId(CALLS_ENDPOINT));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    public static CallList list(String contactId) throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new GetRequest(CALLS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        CallList calls = CallListSerializer.fromString(response.getResponseBody());
        calls.setContactId(contactId);
        return calls;
    }

    public static CallList list(String contactId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("contact_id", contactId);
        Request request = new GetRequest(CALLS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        CallList calls = CallListSerializer.fromString(response.getResponseBody());
        calls.setContactId(contactId);
        return calls;
    }

    public static CallList list() throws OnePageException {
        Request request = new GetRequest(CALLS_ENDPOINT);
        Response response = request.send();
        return CallListSerializer.fromString(response.getResponseBody());
    }

    /*
     * Utility methods.
     */

    public boolean hasAttachments() {
        return this.attachments != null && !attachments.isEmpty();
    }

    /*
     * Object methods.
     */

    @Override
    public String toString() {
        return CallSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Call setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Call setAuthor(String author) {
        this.author = author;
        return this;
    }

    public CallResult getCallResult() {
        return callResult;
    }

    public Call setCallResult(CallResult callResult) {
        this.callResult = callResult;
        return this;
    }

    public Instant getTime() {
        return time;
    }

    public ZonedDateTime getTime(ZoneId zoneId) {
        return time != null ? ZonedDateTime.ofInstant(time, zoneId) : null;
    }

    public Call setTime(Instant time) {
        this.time = time;
        return this;
    }

    public Call setTime(ZonedDateTime time) {
        this.time = time != null ? time.toInstant() : null;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public Call setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Call setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getVia() {
        return via;
    }

    public Call setVia(String via) {
        this.via = via;
        return this;
    }

    public String getRecordingLink() {
        return recordingLink;
    }

    public Call setRecordingLink(String recordingLink) {
        this.recordingLink = recordingLink;
        return this;
    }

    public String getText() {
        return text;
    }

    public Call setText(String text) {
        this.text = text;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Call setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getCreatedAt(ZoneId zoneId) {
        return createdAt != null ? ZonedDateTime.ofInstant(createdAt, zoneId) : null;
    }

    public Call setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public ZonedDateTime getModifiedAt(ZoneId zoneId) {
        return modifiedAt != null ? ZonedDateTime.ofInstant(modifiedAt, zoneId) : null;
    }

    public Call setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public List<String> getUserIdsToNotify() {
        return userIdsToNotify;
    }

    public Call setUserIdsToNotify(List<String> userIdsToNotify) {
        this.userIdsToNotify = userIdsToNotify;
        return this;
    }
}
