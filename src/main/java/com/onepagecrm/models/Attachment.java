package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.FileRefUtils;
import com.onepagecrm.models.internal.S3FileReference;
import com.onepagecrm.models.serializers.AttachmentSerializer;
import com.onepagecrm.models.serializers.DeleteResultSerializer;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.DeleteRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.PutRequest;
import com.onepagecrm.net.request.Request;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.Serializable;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 31/07/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess", "SpellCheckingInspection"})
public class Attachment extends ApiResource implements Serializable {

    /*
     * Constants.
     */

    private static final String PROVIDER_AMAZON = "amazon";
    private static final String PROVIDER_DRIVE = "google_drive";
    private static final String PROVIDER_DROPBOX = "dropbox";
    private static final String PROVIDER_EVERNOTE = "evernote";
    private static final String PROVIDER_OTHER = "other"; // Catch all.

    private static final String REFERENCE_TYPE_DEAL = "deal";
    private static final String REFERENCE_TYPE_CALL = "call";
    private static final String REFERENCE_TYPE_NOTE = "note";
    private static final String REFERENCE_TYPE_OTHER = "other"; // Catch all.

    /*
     * Member variables.
     */

    public enum Provider {
        AMAZON(PROVIDER_AMAZON),
        DRIVE(PROVIDER_DRIVE),
        DROPBOX(PROVIDER_DROPBOX),
        EVERNOTE(PROVIDER_EVERNOTE),
        OTHER(PROVIDER_OTHER);

        private String provider;

        Provider(String provider) {
            this.provider = provider;
        }

        @Override
        public String toString() {
            return provider;
        }

        public static Provider fromString(String provider) {
            if (provider == null) return null;
            switch (provider) {
                case PROVIDER_AMAZON:
                    return AMAZON;
                case PROVIDER_DRIVE:
                    return DRIVE;
                case PROVIDER_DROPBOX:
                    return DROPBOX;
                case PROVIDER_EVERNOTE:
                    return EVERNOTE;
                default:
                    // Manually set provider so we know what API sent (if error)!
                    OTHER.provider = provider;
                    return OTHER;
            }
        }
    }

    public enum ReferenceType {
        DEAL(REFERENCE_TYPE_DEAL),
        CALL(REFERENCE_TYPE_CALL),
        NOTE(REFERENCE_TYPE_NOTE),
        OTHER(REFERENCE_TYPE_OTHER);

        private String resource;

        ReferenceType(String resource) {
            this.resource = resource;
        }

        public static ReferenceType fromString(String resource) {
            if (resource == null) return null;
            switch (resource) {
                case REFERENCE_TYPE_DEAL:
                    return DEAL;
                case REFERENCE_TYPE_CALL:
                    return CALL;
                case REFERENCE_TYPE_NOTE:
                    return NOTE;
                case REFERENCE_TYPE_OTHER:
                    return OTHER;
                default:
                    OTHER.resource = resource;
                    return OTHER;
            }
        }

        @Override
        public String toString() {
            return resource;
        }
    }

    private String id;
    private String filename;
    private Provider provider;
    private String url;
    private Long size;
    private Instant expiresAt;

    private String referenceId;
    private ReferenceType referenceType;
    private String externalUrl;

    /*
     * API methods.
     */

    public Attachment save(String contactId, S3FileReference fileRef) throws OnePageException {
        return isValid() ? update(contactId, fileRef) : create(contactId, fileRef);
    }

    private Attachment update(String contactId, S3FileReference fileRef) throws OnePageException {
        Request request = new PutRequest(
                withId(ATTACHMENTS_ENDPOINT),
                null,
                AttachmentSerializer.toJsonString(this, contactId, fileRef)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return AttachmentSerializer.fromString(responseBody)
                .setReferenceId(referenceId)
                .setReferenceType(referenceType);
    }

    private Attachment create(String contactId, S3FileReference fileRef) throws OnePageException {
        Request request = new PostRequest(
                ATTACHMENTS_ENDPOINT,
                null,
                AttachmentSerializer.toJsonString(this, contactId, fileRef)
        );
        Response response = request.send();
        String responseBody = response.getResponseBody();
        return AttachmentSerializer.fromString(responseBody)
                .setReferenceId(referenceId)
                .setReferenceType(referenceType);
    }

    public DeleteResult delete() throws OnePageException {
        Request request = new DeleteRequest(withId(ATTACHMENTS_ENDPOINT), null);
        Response response = request.send();
        return DeleteResultSerializer.fromString(this.id, response.getResponseBody());
    }

    /*
     * Utility methods.
     */

    public String getFileExtension() {
        return FileRefUtils.extensionFromName(filename);
    }

    /*
     * Object methods.
     */

    public Attachment() {

    }

    public Attachment(Deal reference) {
        if (reference == null) return;
        referenceId = reference.getId();
        referenceType = ReferenceType.DEAL;
    }

    public Attachment(Note reference) {
        if (reference == null) return;
        referenceId = reference.getId();
        referenceType = ReferenceType.NOTE;
    }

    public Attachment(Call reference) {
        if (reference == null) return;
        referenceId = reference.getId();
        referenceType = ReferenceType.CALL;
    }

    @Override
    public String toString() {
        return AttachmentSerializer.toJsonString(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Attachment setId(String id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public Attachment setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Provider getProvider() {
        return provider;
    }

    public Attachment setProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Attachment setUrl(String url) {
        this.url = url;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public Attachment setSize(Long size) {
        this.size = size;
        return this;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public ZonedDateTime getExpiresAt(ZoneId zoneId) {
        return expiresAt != null ? ZonedDateTime.ofInstant(expiresAt, zoneId) : null;
    }

    public Attachment setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public Attachment setReferenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public Attachment setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        return this;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public Attachment setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
        return this;
    }
}
