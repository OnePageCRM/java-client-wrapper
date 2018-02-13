package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.FileReference;
import com.onepagecrm.models.internal.S3Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 27/09/2017.
 */
public class S3DataSerializer extends BaseSerializer {

    private static Logger LOG = Logger.getLogger(S3DataSerializer.class.getSimpleName());

    private static S3Data DEFAULT = new S3Data();

//    public static S3Data fromResponse(Response response) throws OnePageException {
//        JSONObject dataObject = (JSONObject) BaseSerializer.fromResponse(response);
//        return fromJsonObject(dataObject);
//    }

    public static S3Data fromString(String responseBody) throws OnePageException {
        String dataString = (String) BaseSerializer.fromString(responseBody);
        try {
            JSONObject dataObject = new JSONObject(dataString);
            return fromJsonObject(dataObject);

        } catch (JSONException e) {
            LOG.severe("Error with JSON");
            LOG.severe(e.toString());
            return DEFAULT;
        }
    }

    public static S3Data fromJsonObject(JSONObject dataObject) {
        if (dataObject == null) {
            return DEFAULT;
        }

        JSONObject fieldsObject = dataObject.optJSONObject(FIELDS_TAG);
        if (fieldsObject == null) fieldsObject = new JSONObject();

        return new S3Data()
                .setQuota(dataObject.optLong(QUOTA_TAG))
                .setRemaining(dataObject.optLong(STORAGE_LEFT_TAG))
                .setDisplayQuota(dataObject.optString(DISPLAY_QUOTA_TAG))
                .setUrl(dataObject.optString(URL_TAG))
                .setKey(fieldsObject.optString(KEY_TAG))
                .setSuccessStatus(fieldsObject.optInt(SUCCESS_ACTION_STATUS_TAG))
                .setAcl(fieldsObject.optString(ACL_TAG))
                .setPolicy(fieldsObject.optString(POLICY_TAG))
                .setxIgnorePattern(fieldsObject.optString(X_IGNORE_PATTERN_TAG))
                .setxAmzAlgorithm(fieldsObject.optString(X_AMZ_ALGORITHM_TAG))
                .setxAmzCredential(fieldsObject.optString(X_AMZ_CREDENTIAL_TAG))
                .setxAmzDate(fieldsObject.optString(X_AMZ_DATE_TAG))
                .setxAmzSignature(fieldsObject.optString(X_AMZ_SIGNATURE_TAG));
    }

    public static JSONObject toJsonObject(S3Data data) {
        JSONObject dataObject = new JSONObject();
        if (data == null) return dataObject;
        addJsonLongValue(data.getQuota(), dataObject, QUOTA_TAG);
        addJsonLongValue(data.getRemaining(), dataObject, STORAGE_LEFT_TAG);
        addJsonStringValue(data.getDisplayQuota(), dataObject, DISPLAY_QUOTA_TAG);
        addJsonStringValue(data.getUrl(), dataObject, URL_TAG);
        JSONObject fieldsObject = new JSONObject();
        addJsonStringValue(data.getKey(), fieldsObject, KEY_TAG);
        addJsonIntegerValue(data.getSuccessStatus(), fieldsObject, SUCCESS_ACTION_STATUS_TAG);
        addJsonStringValue(data.getAcl(), fieldsObject, ACL_TAG);
        addJsonStringValue(data.getPolicy(), fieldsObject, POLICY_TAG);
        addJsonStringValue(data.getxIgnorePattern(), fieldsObject, X_IGNORE_PATTERN_TAG);
        addJsonStringValue(data.getxAmzAlgorithm(), fieldsObject, X_AMZ_ALGORITHM_TAG);
        addJsonStringValue(data.getxAmzCredential(), fieldsObject, X_AMZ_CREDENTIAL_TAG);
        addJsonStringValue(data.getxAmzDate(), fieldsObject, X_AMZ_DATE_TAG);
        addJsonStringValue(data.getxAmzSignature(), fieldsObject, X_AMZ_SIGNATURE_TAG);
        addJsonObject(fieldsObject, dataObject, FIELDS_TAG);
        return dataObject;
    }

    public static String toJsonString(S3Data s3Data) {
        return toJsonObject(s3Data).toString();
    }

    public static Map<String, String> toParamMap(String contactId, S3Data data, FileReference fileReference) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        if (data == null || fileReference == null || contactId == null) {
            return paramMap;
        }
        String key = contactId + "/" + System.currentTimeMillis() + "/" + fileReference.getName();
        paramMap.put(KEY_TAG, key);
        int successStatus = data.getSuccessStatus() != null ? data.getSuccessStatus() : 201;
        paramMap.put(SUCCESS_ACTION_STATUS_TAG, String.valueOf(successStatus));
        paramMap.put(ACL_TAG, data.getAcl());
        paramMap.put(X_IGNORE_PATTERN_TAG, data.getxIgnorePattern());
        paramMap.put(X_AMZ_CREDENTIAL_TAG, data.getxAmzCredential());
        paramMap.put(X_AMZ_ALGORITHM_TAG, data.getxAmzAlgorithm());
        paramMap.put(X_AMZ_DATE_TAG, data.getxAmzDate());
        paramMap.put(POLICY_TAG, data.getPolicy());
        paramMap.put(X_AMZ_SIGNATURE_TAG, data.getxAmzSignature());
        paramMap.put(FILENAME_TAG, fileReference.getName());
        return paramMap;
    }
}
