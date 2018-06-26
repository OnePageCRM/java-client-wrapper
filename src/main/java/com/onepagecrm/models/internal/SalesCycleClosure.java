package com.onepagecrm.models.internal;

import com.onepagecrm.models.helpers.TextHelper;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.Serializable;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 13/06/2016.
 */
@SuppressWarnings("unused")
public class SalesCycleClosure implements Serializable {

    /*
     * Member variables.
     */

    private String contactId;
    private String userId;
    private Instant closedAt;
    private String comment;

    /*
     * Object methods.
     */

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SalesCycleClosure)) {
            return false;
        }
        SalesCycleClosure toCompare = (SalesCycleClosure) object;
        if (TextHelper.isEmpty(userId) || TextHelper.isEmpty(toCompare.getUserId())) {
            return false;
        }
        if (!userId.equals(toCompare.getUserId())) {
            return false;
        }

        boolean lBothNull = contactId == null && toCompare.getContactId() == null;
        boolean lSameNonNull = !TextHelper.isEmpty(contactId)
                && !TextHelper.isEmpty(toCompare.getContactId())
                && contactId.equals(toCompare.getContactId());

        return lBothNull || lSameNonNull;
    }

    public String getContactId() {
        return contactId;
    }

    public SalesCycleClosure setContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public SalesCycleClosure setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public ZonedDateTime getClosedAt(ZoneId zoneId) {
        return closedAt != null ? ZonedDateTime.ofInstant(closedAt, zoneId) : null;
    }

    public SalesCycleClosure setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public SalesCycleClosure setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String toString() {
        return "SalesCycleClosure{" +
                "contactId='" + contactId + '\'' +
                ", userId='" + userId + '\'' +
                ", closedAt=" + closedAt +
                ", comment='" + comment + '\'' +
                '}';
    }
}
