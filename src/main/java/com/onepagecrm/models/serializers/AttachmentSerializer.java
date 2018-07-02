package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Attachment;
import com.onepagecrm.models.internal.S3FileReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 31/07/2017.
 */
@SuppressWarnings("unused")
public class AttachmentSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(AttachmentSerializer.class.getName());

    public static Attachment fromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject);

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;
        } catch (JSONException e) {
            LOG.severe("Could not find attachment object tags in response");
            LOG.severe(e.toString());
            return new Attachment();
        }
    }

    public static Attachment fromJsonObject(JSONObject attachmentObject) {
        if (attachmentObject == null) {
            return null;
        }
        if (attachmentObject.has(ATTACHMENT_TAG)) {
            attachmentObject = attachmentObject.optJSONObject(ATTACHMENT_TAG);
        }
        return new Attachment()
                .setId(attachmentObject.optString(ID_TAG))
                .setFilename(attachmentObject.optString(FILENAME_TAG))
                .setUrl(attachmentObject.optString(URL_TAG))
                .setSize(attachmentObject.optLong(SIZE_TAG))
                .setProvider(Attachment.Provider.fromString(attachmentObject.optString(STORAGE_PROVIDER_TAG)))
                .setExpiresAt(InstantSerializer.getInstance().parse(attachmentObject.optString(URL_EXPIRES_AT_TAG)));
    }

    public static List<Attachment> fromJsonArray(JSONArray attachmentsArray) {
        List<Attachment> attachments = new ArrayList<>();
        if (attachmentsArray == null) return attachments;
        for (int i = 0; i < attachmentsArray.length(); i++) {
            JSONObject attachmentObject = attachmentsArray.optJSONObject(i);
            attachments.add(fromJsonObject(attachmentObject));
        }
        return attachments;
    }

    public static JSONObject toJsonObject(Attachment attachment) {
        JSONObject attachmentObject = new JSONObject();
        if (attachment == null) return attachmentObject;
        addJsonStringValue(attachment.getId(), attachmentObject, ID_TAG);
        addJsonStringValue(attachment.getFilename(), attachmentObject, FILENAME_TAG);
        addJsonStringValue(attachment.getUrl(), attachmentObject, URL_TAG);
        addJsonLongValue(attachment.getSize(), attachmentObject, SIZE_TAG);
        addJsonObjectValue(attachment.getProvider(), attachmentObject, STORAGE_PROVIDER_TAG);
        addJsonStringValue(
                InstantSerializer.getInstance().format(attachment.getExpiresAt()),
                attachmentObject,
                URL_EXPIRES_AT_TAG
        );
        addJsonObjectValue(attachment.getReferenceType(), attachmentObject, REFERENCE_TYPE_TAG);
        addJsonStringValue(attachment.getReferenceId(), attachmentObject, REFERENCE_ID_TAG);
        return attachmentObject;
    }

    public static String toJsonString(Attachment attachment) {
        return toJsonObject(attachment).toString();
    }

    public static JSONObject toJsonObject(Attachment attachment, String contactId, S3FileReference file) {
        JSONObject jsonObject = new JSONObject();
        if (file == null || attachment == null || contactId == null) return jsonObject;
        addJsonStringValue(contactId, jsonObject, CONTACT_ID_TAG);
        addJsonStringValue(attachment.getReferenceId(), jsonObject, REFERENCE_ID_TAG);
        addJsonObjectValue(attachment.getReferenceType(), jsonObject, REFERENCE_TYPE_TAG);
        addJsonStringValue(file.getName(), jsonObject, NAME_TAG);
        addJsonStringValue(file.getKey(), jsonObject, KEY_TAG);
        addJsonLongValue(file.getSize(), jsonObject, SIZE_TAG);
        if (attachment.getProvider() != null) {
            switch (attachment.getProvider()) {
                case DRIVE:
                case DROPBOX:
                case EVERNOTE: {
                    addJsonStringValue(attachment.getExternalUrl(), jsonObject, EXTERNAL_URL_TAG);
                    addJsonStringValue(attachment.getProvider().toString(), jsonObject, LINK_TYPE_TAG);
                    break;
                }
            }
        }
        return jsonObject;
    }

    public static String toJsonString(Attachment attachment, String contactId, S3FileReference file) {
        return toJsonObject(attachment, contactId, file).toString();
    }

    public static JSONArray toJsonArray(List<Attachment> attachments) {
        JSONArray attachmentsArray = new JSONArray();
        if (attachments == null) return attachmentsArray;
        for (Attachment attachment : attachments) {
            attachmentsArray.put(toJsonObject(attachment));
        }
        return attachmentsArray;
    }

    public static String toJsonString(List<Attachment> attachments) {
        return toJsonArray(attachments).toString();
    }
}
