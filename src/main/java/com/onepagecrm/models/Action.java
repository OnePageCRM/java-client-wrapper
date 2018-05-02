package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.helpers.ActionHelper;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.internal.PredefinedAction;
import com.onepagecrm.models.internal.PredefinedActionList;
import com.onepagecrm.models.serializers.ActionSerializer;
import com.onepagecrm.models.serializers.PredefinedActionSerializer;
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
import java.util.Map;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 29/06/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class Action extends ApiResource implements Serializable {

    private static final long serialVersionUID = -7486991046434989805L;

    /*
     * Constants
     */

    private static final String STATUS_ASAP = "asap";
    private static final String STATUS_DATE = "date";
    private static final String STATUS_DATE_TIME = "date_time";
    private static final String STATUS_WAITING = "waiting";
    private static final String STATUS_QUEUED = "queued";
    private static final String STATUS_QUEUED_WITH_DATE = "queued_with_date";
    private static final String STATUS_DONE = "done";
    private static final String STATUS_OTHER = "other"; // Catch all.

    /*
     * Member variables.
     */

    public enum Status {
        ASAP(STATUS_ASAP),
        DATE(STATUS_DATE),
        DATE_TIME(STATUS_DATE_TIME),
        WAITING(STATUS_WAITING),
        QUEUED(STATUS_QUEUED),
        QUEUED_WITH_DATE(STATUS_QUEUED_WITH_DATE),
        DONE(STATUS_DONE),
        OTHER(STATUS_OTHER);

        private String status;

        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }

        public static Status fromString(String status) {
            if (status == null) return null;
            switch (status) {
                case STATUS_ASAP:
                    return ASAP;
                case STATUS_DATE:
                    return DATE;
                case STATUS_DATE_TIME:
                    return DATE_TIME;
                case STATUS_WAITING:
                    return WAITING;
                case STATUS_QUEUED:
                    return QUEUED;
                case STATUS_QUEUED_WITH_DATE:
                    return QUEUED_WITH_DATE;
                case STATUS_DONE:
                    return DONE;
                default:
                    // Manually set status so we know what API sent (if error)!
                    OTHER.status = status;
                    return OTHER;
            }
        }
    }

    private String id;
    private String assigneeId;
    private String contactId;
    private String text;
    private Instant createdAt;
    private Instant modifiedAt;
    private Status status;
    private LocalDate date;
    private Instant exactTime;
    private int flagColor;
    private Integer position;

    /*
     * API methods.
     */

    public Action save() throws OnePageException {
        return isValid() ? update() : create();
    }

    private Action update() throws OnePageException {
        Request request = new PutRequest(
                addIdToEndpoint(ACTIONS_ENDPOINT),
                null,
                ActionSerializer.toJsonObject(this)
        );
        Response response = request.send();
        return ActionSerializer.fromString(response.getResponseBody());
    }

    private Action create() throws OnePageException {
        Request request = new PostRequest(ACTIONS_ENDPOINT, null, ActionSerializer.toJsonObject(this));
        Response response = request.send();
        return ActionSerializer.fromString(response.getResponseBody());
    }

    public void delete() throws OnePageException {
        Request request = new DeleteRequest(addIdToEndpoint(ACTIONS_ENDPOINT));
        Response response = request.send();
    }

    public Action markComplete() throws OnePageException {
        String endpoint = MARK_COMPLETE_ENDPOINT.replace("{id}", this.getId());
        Request request = new PutRequest(endpoint, null, ActionSerializer.toJsonObject(this));
        Response response = request.send();
        return ActionSerializer.fromString(response.getResponseBody());
    }

    public Action undoCompletion() throws OnePageException {
        String endpoint = UNDO_COMPLETION_ENDPOINT.replace("{id}", this.getId());
        Request request = new PutRequest(endpoint, null, ActionSerializer.toJsonObject(this));
        Response response = request.send();
        return ActionSerializer.fromString(response.getResponseBody());
    }

    public static ActionList list(String assigneeId) throws OnePageException {
        Map<String, Object> params = Query.paramsDefault();
        params.put("assignee_id", assigneeId);
        Request request = new GetRequest(ACTIONS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        return new ActionList(ActionSerializer.listFromString(response.getResponseBody()));
    }

    public static ActionList list(String assigneeId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("assignee_id", assigneeId);
        Request request = new GetRequest(ACTIONS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        return new ActionList(ActionSerializer.listFromString(response.getResponseBody()));
    }

    public static PredefinedActionList listPredefined() throws OnePageException {
        Request request = new GetRequest(PREDEFINED_ACTIONS_ENDPOINT, Query.queryDefault());
        Response response = request.send();
        return new PredefinedActionList(PredefinedActionSerializer.fromString(response.getResponseBody()));
    }

    public static PredefinedActionList listPredefined(Paginator paginator) throws OnePageException {
        Request request = new GetRequest(PREDEFINED_ACTIONS_ENDPOINT, Query.query(paginator));
        Response response = request.send();
        return new PredefinedActionList(PredefinedActionSerializer.fromString(response.getResponseBody()));
    }

    /*
     * Utility methods
     */

    public Action promote(ZoneId zoneId, PredefinedAction predefined) {
        return ActionHelper.promote(zoneId, predefined);
    }

    public boolean isQueued() {
        return this.status != null && (this.status == Status.QUEUED || this.status == Status.QUEUED_WITH_DATE);
    }

    public boolean isNext() {
        return this.status != null && (this.status == Status.ASAP || this.status == Status.DATE ||
                this.status == Status.DATE_TIME || this.status == Status.WAITING);
    }

    public String getFriendlyDate() {
        return ActionHelper.formatFriendlyDate(this);
    }

    public String getFriendlyActionText(ZoneId zoneId, boolean is24hr) {
        return ActionHelper.formatFriendlyActionText(zoneId, is24hr, this);
    }

    public String getFriendlyTimeAndDate(ZoneId zoneId, boolean is24hr) {
        return ActionHelper.formatFriendlyTimeAndDate(zoneId, is24hr, this);
    }

    /*
     * Object methods
     */

    @Override
    public String toString() {
        return ActionSerializer.toJsonObject(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Action setId(String id) {
        this.id = id;
        return this;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public Action setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
        return this;
    }

    public String getContactId() {
        return contactId;
    }

    public Action setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getText() {
        return text;
    }

    public Action setText(String text) {
        this.text = text;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getCreatedAt(ZoneId zoneId) {
        return createdAt != null ? ZonedDateTime.ofInstant(createdAt, zoneId) : null;
    }

    public Action setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public ZonedDateTime getModifiedAt(ZoneId zoneId) {
        return modifiedAt != null ? ZonedDateTime.ofInstant(modifiedAt, zoneId) : null;
    }

    public Action setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Action setStatus(Status status) {
        this.status = status;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Action setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Instant getExactTime() {
        return exactTime;
    }

    public ZonedDateTime getExactTime(ZoneId zoneId) {
        return exactTime != null ? ZonedDateTime.ofInstant(exactTime, zoneId) : null;
    }

    public Action setExactTime(Instant exactTime) {
        this.exactTime = exactTime;
        return this;
    }

    public Action setExactTime(ZonedDateTime zonedDateTime) {
        this.exactTime = zonedDateTime != null ? zonedDateTime.toInstant() : null;
        return this;
    }

    public int getFlagColor() {
        return flagColor;
    }

    public Action setFlagColor(int flagColor) {
        this.flagColor = flagColor;
        return this;
    }

    public Integer getPosition() {
        return position;
    }

    public Action setPosition(Integer position) {
        this.position = position;
        return this;
    }
}
