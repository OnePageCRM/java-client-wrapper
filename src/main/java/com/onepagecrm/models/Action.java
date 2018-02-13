package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.internal.PredefinedAction;
import com.onepagecrm.models.internal.PredefinedActionList;
import com.onepagecrm.models.serializers.ActionSerializer;
import com.onepagecrm.models.serializers.DateSerializer;
import com.onepagecrm.models.serializers.PredefinedActionSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 29/06/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class Action extends ApiResource implements Serializable {

    private static final long serialVersionUID = -7486991046434989805L;

    /**
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

    /**
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
    private Date createdAt;
    private Date modifiedAt;
    private Status status;
    private Date date;
    private Date exactTime;
    private int dateColor;
    private Integer position;

    /**
     * API methods
     */

    public Action save() throws OnePageException {
        return isValid() ? update() : create();
    }

    private Action update() throws OnePageException {
        Request request = new PutRequest(
                addActionIdToEndpoint(ACTIONS_ENDPOINT),
                null,
                ActionSerializer.getInstance().toJsonString(this)
        );
        Response response = request.send();
        return ActionSerializer.getInstance().single(response);
    }

    private Action create() throws OnePageException {
        Request request = new PostRequest(
                ACTIONS_ENDPOINT,
                null,
                ActionSerializer.getInstance().toJsonString(this)
        );
        Response response = request.send();
        return ActionSerializer.getInstance().single(response);
    }

    public void delete() throws OnePageException {
        Request request = new DeleteRequest(ACTIONS_ENDPOINT + "/" + this.getId());
        Response response = request.send();
    }

    public Action markComplete() throws OnePageException {
        Request request = new PutRequest(
                MARK_COMPLETE_ENDPOINT.replace("{id}", this.getId()),
                null,
                ActionSerializer.getInstance().toJsonString(this)
        );
        Response response = request.send();
        return ActionSerializer.getInstance().single(response);
    }

    public Action undoCompletion() throws OnePageException {
        Request request = new PutRequest(
                UNDO_COMPLETION_ENDPOINT.replace("{id}", this.getId()),
                null,
                ActionSerializer.getInstance().toJsonString(this)
        );
        Response response = request.send();
        return ActionSerializer.getInstance().single(response);
    }

    public static ActionList list(String assigneeId) throws OnePageException {
        Map<String, Object> params = Query.paramsDefault();
        params.put("assignee_id", assigneeId);
        Request request = new GetRequest(ACTIONS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        return new ActionList(ActionSerializer.getInstance().list(response));
    }

    public static ActionList list(String assigneeId, Paginator paginator) throws OnePageException {
        Map<String, Object> params = Query.params(paginator);
        params.put("assignee_id", assigneeId);
        Request request = new GetRequest(ACTIONS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        return new ActionList(ActionSerializer.getInstance().list(response));
    }

    public static PredefinedActionList listPredefined() throws OnePageException {
        Request request = new GetRequest(PREDEFINED_ACTIONS_ENDPOINT, Query.queryDefault());
        Response response = request.send();
        return new PredefinedActionList(PredefinedActionSerializer.fromResponse(response));
    }

    public static PredefinedActionList listPredefined(Paginator paginator) throws OnePageException {
        Request request = new GetRequest(PREDEFINED_ACTIONS_ENDPOINT, Query.query(paginator));
        Response response = request.send();
        return new PredefinedActionList(PredefinedActionSerializer.fromResponse(response));
    }

    /**
     * Caution - TimeZone is UTC and will not function as expected if using something different.
     *
     * @param predefined - PredefinedAction to be promoted.
     * @return The current action which has taken on the text and date of the PredefinedAction.
     */
    public Action promote(PredefinedAction predefined) {
        this.setText(predefined.getText());
        // Add the extra days to the date of the action.
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(this.date == null ? new Date() : this.date);
        calendar.add(Calendar.DATE, predefined.getDays());
        this.setDate(calendar.getTime());
        // Add the extra days to the exact time for Date & Time.
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        this.setExactTime(calendar.getTime());
        return this;
    }

    private String addActionIdToEndpoint(String endpoint) {
        return endpoint + "/" + this.id;
    }

    /**
     * Utility methods
     */

    public String getFriendlyDateString() {
        if (this.date != null) {
            // Return date in format yyyy-MM-dd (uppercase).
            return DateSerializer.toFriendlyDateString(this.date).toUpperCase();
        } else if (this.status != null) {
            // Return status (uppercase).
            return this.status.toString().toUpperCase();
        } else {
            // This is needed to correctly display contacts w/out NA's in Action Stream.
            return null;
        }
    }

    public boolean isQueued() {
        return this.status != null && (this.status == Status.QUEUED || this.status == Status.QUEUED_WITH_DATE);
    }

    public boolean isNext() {
        return this.status != null && (this.status == Status.ASAP || this.status == Status.DATE ||
                this.status == Status.DATE_TIME || this.status == Status.WAITING);
    }

    /**
     * Object methods
     */

    @Override
    public String toString() {
        return ActionSerializer.getInstance().toJsonString(this);
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Action setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Action setModifiedAt(Date modifiedAt) {
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

    public Date getDate() {
        return date;
    }

    public Action setDate(Date date) {
        this.date = date;
        return this;
    }

    public Date getExactTime() {
        return exactTime;
    }

    public Action setExactTime(Date exactTime) {
        this.exactTime = exactTime;
        return this;
    }

    public int getDateColor() {
        return dateColor;
    }

    public Action setDateColor(int dateColor) {
        this.dateColor = dateColor;
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
