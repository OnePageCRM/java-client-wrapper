package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.models.serializers.NoteListSerializer;
import com.onepagecrm.models.serializers.NoteSerializer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 31/07/2017.
 */
@SuppressWarnings("unused")
public class Note extends ApiResource implements Serializable {

    /*
     * Member variables.
     */

    private String id;
    private String author;
    private String text;
    private String contactId;
    private LocalDate date;
    private String linkedDealId;
    private List<String> userIdsToNotify;
    private List<Attachment> attachments;
    private Instant createdAt;
    private Instant modifiedAt;

    /*
     * API methods.
     */

    public Note save() throws OnePageException {
        return this.isValid() ? update() : create();
    }

    private Note create() throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new PostRequest(
                NOTES_ENDPOINT,
                Query.fromParams(params),
                NoteSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return NoteSerializer.fromString(response.getResponseBody());
    }

    private Note update() throws OnePageException {
        Request request = new PutRequest(
                withId(NOTES_ENDPOINT),
                null,
                NoteSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return NoteSerializer.fromString(response.getResponseBody());
    }

    public static Note byId(String id) throws OnePageException {
        Request request = new GetRequest(withId(NOTES_ENDPOINT, id), null);
        Response response = request.send();
        return NoteSerializer.fromString(response.getResponseBody());
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(withId(NOTES_ENDPOINT));
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    public static NoteList list(String contactId) throws OnePageException {
        Map<String, Object> params = new HashMap<>();
        params.put("contact_id", contactId);
        Request request = new GetRequest(NOTES_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        NoteList notes = NoteListSerializer.fromString(response.getResponseBody());
        notes.setContactId(contactId);
        return notes;
    }

    public static NoteList list(String contactId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("contact_id", contactId);
        Request request = new GetRequest(NOTES_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        NoteList notes = NoteListSerializer.fromString(response.getResponseBody());
        notes.setContactId(contactId);
        return notes;
    }

    public static NoteList list() throws OnePageException {
        Request request = new GetRequest(NOTES_ENDPOINT);
        Response response = request.send();
        return NoteListSerializer.fromString(response.getResponseBody());
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
        return NoteSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Note setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Note setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public Note setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Note setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getLinkedDealId() {
        return linkedDealId;
    }

    public Note setLinkedDealId(String linkedDealId) {
        this.linkedDealId = linkedDealId;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Note setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getCreatedAt(ZoneId zoneId) {
        return createdAt != null ? ZonedDateTime.ofInstant(createdAt, zoneId) : null;
    }

    public Note setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public ZonedDateTime getModifiedAt(ZoneId zoneId) {
        return modifiedAt != null ? ZonedDateTime.ofInstant(modifiedAt, zoneId) : null;
    }

    public Note setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public List<String> getUserIdsToNotify() {
        return userIdsToNotify;
    }

    public Note setUserIdsToNotify(List<String> userIdsToNotify) {
        this.userIdsToNotify = userIdsToNotify;
        return this;
    }
}
