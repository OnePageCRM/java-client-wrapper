package com.onepagecrm.models.internal;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 21/11/2016.
 */
@SuppressWarnings("unused")
public class DeleteResult {

    private final String resourceId;
    private final boolean deleted;

    public DeleteResult(String resourceId, boolean deleted) {
        this.resourceId = resourceId;
        this.deleted = deleted;
    }

    public String getResourceId() {
        return resourceId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "DeleteResult{" +
                "resourceId='" + resourceId + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
