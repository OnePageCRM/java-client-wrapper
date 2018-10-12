package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 01/08/2017.
 * Copyright (c) 2017 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class BaseSerializer {

    private static final Logger LOG = Logger.getLogger(BaseSerializer.class.getName());

    // LOGIN TAGS
    public static final String LOGIN_TAG = "login";
    public static final String PASSWORD_TAG = "password";
    public static final String FULL_RESPONSE_TAG = "full_response";
    public static final String DATA_TAG = "data";
    public static final String USER_ID_TAG = "user_id";
    public static final String AUTH_KEY_TAG = "auth_key";
    public static final String ACCOUNT_TYPE_TAG = "account_type";

    // LOGIN RESPONSE / USER TAGS
    public static final String BOOTSTRAP_TAG = "bootstrap";
    public static final String ACTION_STREAM_DATA_TAG = "action_stream_data";
    public static final String CONTACT_DATA_TAG = "contact_data";
    public static final String DEAL_DATA_TAG = "deal_data";
    public static final String USER_TAG = "user";
    public static final String FIRST_NAME_TAG = "first_name";
    public static final String LAST_NAME_TAG = "last_name";
    public static final String LETTER_TAG = "letter";
    public static final String COMPANY_NAME_TAG = "company_name";
    public static final String PHOTO_URL_TAG = "photo_url";
    public static final String BCC_EMAIL_TAG = "bcc_email";
    public static final String SALES_TAG = "sales";
    public static final String ACCOUNT_TAG = "account";
    public static final String SETTINGS_TAG = "settings";
    public static final String NEW_USER_TAG = "new_user";

    // CONTACT TAGS
    public static final String CONTACTS_TAG = "contacts";
    public static final String CONTACT_TAG = "contact";
    public static final String LINKED_CONTACTS_TAG = "linked_contacts";
    public static final String LINKED_CONTACT_TAG = "linked_contact";
    public static final String ID_TAG = "id";
    public static final String CONTACT_TITLES_TAG = "contact_titles";
    public static final String CONTACT_TITLE_TAG = "contact_title";
    public static final String TITLE_TAG = "title";
    public static final String COMPANY_ID_TAG = "company_id";
    public static final String JOB_TITLE_TAG = "job_title";
    public static final String OWNER_ID_TAG = "owner_id";
    public static final String SALES_CLOSED_FOR_TAG = "sales_closed_for";
    public static final String STARRED_TAG = "starred";
    public static final String STATUS_ID_TAG = "status_id";
    public static final String BACKGROUND_TAG = "background";
    public static final String ADDRESS_LIST_TAG = "address_list";
    public static final String IMAGE_TAG = "image";
    public static final String PENDING_DEAL_TAG = "pending_deal";
    public static final String TOTAL_PENDINGS_TAG = "total_pendings";
    public static final String TOTAL_DEALS_COUNT_TAG = "total_deals_count";
    public static final String CLOSE_SALES_CYCLE_COMMENT_TAG = "comment";
    public static final String CONTACT_PHOTO_TAG = "contact_photo";
    public static final String STAR_TAG = "star";
    public static final String UNSTAR_TAG = "unstar";
    public static final String LINKED_WITH_TAG = "linked_with";

    // GOOGLE CONTACT TAGS
    public static final String GOOGLE_CONTACTS_DATA_TAG = "google_contacts_data";
    public static final String ACCOUNT_EMAIL_TAG = "account_email";

    // CONTACT POINT TAGS
    public static final String PHONES_TAG = "phones";
    public static final String PHONE_TAG = "phone";
    public static final String EMAILS_TAG = "emails";
    public static final String EMAIL_TAG = "email";
    public static final String URLS_TAG = "urls";
    public static final String URL_TAG = "url";

    // ADDRESS LIST TAGS
    public static final String ADDRESS_TAG = "address";
    public static final String CITY_TAG = "city";
    public static final String STATE_TAG = "state";
    public static final String ZIP_CODE_TAG = "zip_code";
    public static final String COUNTRY_CODE_TAG = "country_code";

    // ACTION TAGS
    public static final String NEXT_ACTIONS_TAG = "next_actions";
    public static final String NEXT_ACTION_TAG = "next_action";
    public static final String NEXT_ACTION_ID_TAG = "next_action_id";
    public static final String CONTACT_ID_TAG = "contact_id";
    public static final String ASSIGNEE_ID_TAG = "assignee_id";
    public static final String DATE_TAG = "date";
    public static final String DAYS_TAG = "days";
    public static final String ACTION_TAG = "action";
    public static final String ACTIONS_TAG = "actions";
    public static final String FLAG_COLOR_TAG = "flag_color";
    public static final String QUEUED_ACTION_TAG = "queued_action";
    public static final String QUEUED_ACTIONS_TAG = "queued_actions";
    public static final String PREDEFINED_ACTION_TAG = "predefined_action";
    public static final String PREDEFINED_ACTIONS_TAG = "predefined_actions";
    public static final String EXACT_TIME_TAG = "exact_time";
    public static final String EXACT_TIME_INT_TAG = "exact_time_int";

    // CLOSED SALES CYCLE
    public static final String CLOSED_SALES_TAG = "closed_sales";
    public static final String CLOSED_AT_TAG = "closed_at";
    public static final String COMMENT_TAG = "comment";

    // CALL TAGS
    public static final String CALL_TAG = "call";
    public static final String CALLS_TAG = "calls";
    public static final String CALL_RESULTS_TAG = "call_results";
    public static final String CALL_RESULT_TAG = "call_result";
    public static final String CALL_TIME_INT_TAG = "call_time_int";
    public static final String TEXT_TAG = "text";
    public static final String VIA_TAG = "via";
    public static final String AUTHOR_TAG = "author";
    public static final String PHONE_NUMBER_TAG = "phone_number";
    public static final String RECORDING_LINK_TAG = "recording_link";
    public static final String CALL_RESULTS_ORDER_TAG = "call_results_order";

    // 201 RESPONSE TAGS
    public static final String STATUS_TAG = "status";
    public static final String STATUSES_TAG = "statuses";
    public static final String MESSAGE_TAG = "message";
    public static final String TIMESTAMP_TAG = "timestamp";
    public static final String CREATED_TAG = "Created";

    // GENERIC TAGS
    public static final String TYPE_TAG = "type";
    public static final String VALUE_TAG = "value";
    public static final String CREATED_AT_TAG = "created_at";
    public static final String MODIFIED_AT_TAG = "modified_at";
    public static final String UPDATED_AT_TAG = "updated_at";
    public static final String OK_TAG = "OK";
    public static final String DISPLAY_TAG = "display";
    public static final String AS_SORT_TAG = "as_sort";
    public static final String AZ_SORT_TAG = "az_sort";

    // CUSTOM FIELDS TAGS
    public static final String CUSTOM_FIELDS_TAG = "custom_fields";
    public static final String CUSTOM_FIELD_TAG = "custom_field";
    public static final String CUSTOM_FIELD_VALUE_TAG = "custom_field_value";
    public static final String CUSTOM_FIELD_ID_TAG = "custom_field_id";
    public static final String CHOICES_TAG = "choices";
    public static final String NAME_TAG = "name";
    public static final String POSITION_TAG = "position";
    public static final String REMINDER_DAYS_TAG = "reminder_days";

    // TAGS TAGS
    public static final String TAGS_TAG = "tags";
    public static final String TAG_TAG = "tag";
    public static final String TAG_NAME_TAG = "tag_name";
    public static final String TAG_VALUE_TAG = "tag_value";
    public static final String SYSTEM_TAGS_TAG = "system_tags";
    public static final String COUNTS_TAG = "counts";
    public static final String TOTAL_COUNT_TAG = "total_count";
    public static final String ACTION_STREAM_COUNT_TAG = "action_stream_count";

    // ERROR TAGS
    public static final String ERROR_NAME_TAG = "error_name";
    public static final String ERROR_MESSAGE_TAG = "error_message";
    public static final String ERRORS_TAG = "errors";

    // TEAM TAGS
    public static final String TEAM_TAG = "team";
    public static final String ACCOUNT_RIGHTS_TAG = "account_rights";

    // STATUS TAGS
    public static final String TEAM_COUNTS_TAG = "team_counts";
    public static final String COLOR_TAG = "color";
    public static final String DESCRIPTION_TAG = "description";

    // LEAD SOURCES TAGS
    public static final String LEAD_SOURCE_ID_TAG = "lead_source_id";
    public static final String LEAD_SOURCES_TAG = "lead_sources";
    public static final String LEAD_SOURCE_TAG = "lead_source";

    // COUNTRIES TAGS
    public static final String COUNTRIES_TAG = "countries";
    public static final String COUNTRY_TAG = "country";
    public static final String CODE_TAG = "code";
    public static final String PHONE_PREFIX_TAG = "phone_prefix";
    public static final String CURRENCY_TAG = "currency";
    public static final String POPULARITY_TAG = "popularity";

    // SETTINGS TAGS
    public static final String TIME_ZONE_TAG = "time_zone";
    public static final String DATE_FORMAT_TAG = "date_format";
    public static final String NOT_WORKING_DAYS_TAG = "not_working_days";
    public static final String LISTING_SIZE_TAG = "listing_size";
    public static final String POPULAR_COUNTRIES_TAG = "popular_countries";
    public static final String CURRENCY_SYMBOL_TAG = "currency_symbol";
    public static final String DEFAULT_CONTACT_TYPE_TAG = "default_contact_type";
    public static final String SEPARATOR_TAG = "separator";
    public static final String DELIMITER_TAG = "delimiter";
    public static final String SHOW_TIDY_STREAM_TAG = "show_tidy_stream";
    public static final String SHOW_COMPANY_FIELDS_WITH_CONTACT_TAG = "show_company_fields_with_contact";
    public static final String SHOW_COMPANY_PHONE_TAG = "company_phone_enabled";
    public static final String SHOW_COMPANY_URL_TAG = "company_url_enabled";
    public static final String SHOW_COMPANY_ADDRESS_TAG = "company_address_enabled";
    public static final String SHOW_COMPANY_DESCRIPTION_TAG = "company_description_enabled";
    public static final String TIME_WITH_AMPM_TAG = "time_with_ampm";

    // DEALS TAGS
    public static final String PIPELINE_SORT_TAG = "pipeline_sort";
    public static final String DEAL_TAG = "deal";
    public static final String DEALS_TAG = "deals";
    public static final String AMOUNT_TAG = "amount";
    public static final String EXPECTED_CLOSE_DATE_TAG = "expected_close_date";
    public static final String MONTHS_TAG = "months";
    public static final String STAGE_TAG = "stage";
    public static final String TOTAL_AMOUNT_TAG = "total_amount";
    public static final String HAS_RELATED_NOTES_TAG = "has_related_notes";
    public static final String RELATED_NOTES_TAG = "related_notes";
    public static final String CLOSE_DATE_TAG = "close_date";
    public static final String DEAL_ID_TAG = "deal_id";
    public static final String DEAL_STAGE_TAG = "deal_stage";
    public static final String DEAL_STAGES_TAG = "deal_stages";
    public static final String LABEL_TAG = "label";
    public static final String CONTACT_INFO_TAG = "contact_info";
    public static final String CONTACT_NAME_TAG = "contact_name";
    public static final String COMPANY_TAG = "company";
    public static final String PENDING_DEALS_TAG = "pending_deals";

    // DEAL ITEMS
    public static final String DEAL_ITEMS_TAG = "deal_items";
    public static final String PRICE_TAG = "price";
    public static final String QUANTITY_TAG = "qty";
    public static final String ITEM_GROUP_ID_TAG = "item_group_id";
    public static final String PREDEFINED_ITEM_ID_TAG = "predefined_item_id";
    public static final String HAS_DEAL_ITEMS_TAG = "has_deal_items";

    // NOTES
    public static final String NOTE_TAG = "note";
    public static final String NOTES_TAG = "notes";
    public static final String LINKED_DEAL_ID_TAG = "linked_deal_id";
    public static final String USER_IDS_TO_NOTIFY_TAG = "user_ids_to_notify";

    // EMAILS
    public static final String EMAIL_MESSAGES_TAG = "email_messages";
    public static final String EMAIL_MESSAGE_TAG = "email_message";
    public static final String SEND_TIME_TAG = "send_time";
    public static final String MESSAGE_ID_TAG = "message_id";
    public static final String SENDER_TAG = "sender";
    public static final String RECIPIENTS_TAG = "recipients";
    public static final String TO_TAG = "to";
    public static final String CC_TAG = "cc";
    public static final String BCC_TAG = "bcc";
    public static final String SUBJECT_TAG = "subject";
    public static final String PLAIN_CONTENT_TAG = "plain_content";
    public static final String HTML_CONTENT_TAG = "html_content";
    public static final String INCOMING_EMAIL_ATG = "incoming_email";

    // FILTERS TAGS
    public static final String FILTER_TAG = "filter";
    public static final String FILTERS_TAG = "filters";

    // METADATA TAGS
    public static final String PAGE_TAG = "page";
    public static final String MAX_PAGE_TAG = "max_page";
    public static final String PER_PAGE_TAG = "per_page";

    // CONTACT COUNTS
    public static final String CONTACTS_COUNT_TAG = "contacts_count";
    public static final String ALL_TAG = "all";
    public static final String USERS_TAG = "users";
    public static final String LETTER_1_TAG = "1";
    public static final String LETTER_A_TAG = "a";
    public static final String LETTER_B_TAG = "b";
    public static final String LETTER_C_TAG = "c";
    public static final String LETTER_D_TAG = "d";
    public static final String LETTER_E_TAG = "e";
    public static final String LETTER_F_TAG = "f";
    public static final String LETTER_G_TAG = "g";
    public static final String LETTER_H_TAG = "h";
    public static final String LETTER_I_TAG = "i";
    public static final String LETTER_J_TAG = "j";
    public static final String LETTER_K_TAG = "k";
    public static final String LETTER_L_TAG = "l";
    public static final String LETTER_M_TAG = "m";
    public static final String LETTER_N_TAG = "n";
    public static final String LETTER_O_TAG = "o";
    public static final String LETTER_P_TAG = "p";
    public static final String LETTER_Q_TAG = "q";
    public static final String LETTER_R_TAG = "r";
    public static final String LETTER_S_TAG = "s";
    public static final String LETTER_T_TAG = "t";
    public static final String LETTER_U_TAG = "u";
    public static final String LETTER_V_TAG = "v";
    public static final String LETTER_W_TAG = "w";
    public static final String LETTER_X_TAG = "x";
    public static final String LETTER_Y_TAG = "y";
    public static final String LETTER_Z_TAG = "z";

    // STREAM COUNTS
    public static final String TEAM_STREAM_TAG = "team_stream";
    public static final String STREAM_COUNT_TAG = "stream_count";
    public static final String COUNT_TAG = "count";
    public static final String ALL_COUNT_TAG = "all_count";

    // DEVICES
    public static final String DEVICE_ID_TAG = "device_id";
    public static final String FCM_DEVICE_TAG = "fcm_device";
    public static final String FCM_DEVICES_TAG = "fcm_devices";
    public static final String ACTION_WITH_TIME_TAG = "action_with_time";
    public static final String SUBSCRIBED_AT_TAG = "subscribed_at";
    public static final String DEVICE_TYPE_TAG = "device_type";

    // ACCOUNT RIGHTS
    public static String ACCOUNT_OWNER_TAG = "account_owner";
    public static String ADMIN_TAG = "admin";
    public static String ACTIVITY_TAG = "activity";
    public static String EDIT_TARGET_TAG = "edit_target";
    public static String DELETE_CONTACTS_TAG = "delete_contacts";
    public static String PIPELINE_TAG = "pipeline";
    public static String EXPORT_TAG = "export";
    public static String PRIVATE_CONTACTS_TAG = "private_contacts";

    // COMPANIES
    public static final String COMPANIES_TAG = "companies";
    public static final String COMPANY_FIELD_TAG = "company_field";
    public static final String COMPANY_FIELDS_TAG = "company_fields";
    public static final String WON_DEALS_COUNT_TAG = "won_deals_count";
    public static final String TOTAL_WON_AMOUNT_TAG = "total_won_amount";
    public static final String PENDING_DEALS_COUNT_TAG = "pending_deals_count";
    public static final String TOTAL_PENDING_AMOUNT_TAG = "total_pending_amount";
    public static final String SYNCING_STATUS_TAG = "syncing_status";
    public static final String SYNCED_STATUS_ID_TAG = "synced_status_id";
    public static final String SYNCING_TAGS_TAG = "syncing_tags";
    public static final String SYNCED_TAGS_TAG = "synced_tags";

    // SPLIT
    public static final String SPLIT_TAG = "split";

    // DEAL FIELDS (CFD)
    public static final String COST_TAG = "cost";
    public static final String MARGIN_TAG = "margin";
    public static final String TOTAL_COST_TAG = "total_cost";
    public static final String DEAL_FIELD_TAG = "deal_field";
    public static final String DEAL_FIELDS_TAG = "deal_fields";
    public static final String COST_SETUP_TAG = "cost_setup";
    public static final String COST_ENABLED_TAG = "cost_enabled";
    public static final String COST_REQUIRED_TAG = "cost_required";
    public static final String COMMISSION_TAG = "commission";
    public static final String COMMISSION_BASE_TAG = "commission_base";
    public static final String COMMISSION_TYPE_TAG = "commission_type";
    public static final String COMMISSION_PERCENTAGE_TAG = "commission_percentage";

    // ATTACHMENTS
    public static final String ATTACHMENT_TAG = "attachment";
    public static final String ATTACHMENTS_TAG = "attachments";
    public static final String STORAGE_PROVIDER_TAG = "storage_provider";
    public static final String FILENAME_TAG = "filename";
    public static final String SIZE_TAG = "size";
    public static final String URL_EXPIRES_AT_TAG = "url_expires_at";
    public static final String CALL_ID_TAG = "call_id";
    public static final String NOTE_ID_TAG = "note_id";

    // S3 / ATTACHMENTS
    public static final String QUOTA_TAG = "quota";
    public static final String STORAGE_LEFT_TAG = "storage_left";
    public static final String DISPLAY_QUOTA_TAG = "display_quota";
    public static final String FIELDS_TAG = "fields";
    public static final String KEY_TAG = "key";
    public static final String SUCCESS_ACTION_STATUS_TAG = "success_action_status";
    public static final String ACL_TAG = "acl";
    public static final String POLICY_TAG = "policy";
    public static final String X_IGNORE_PATTERN_TAG = "x-ignore-pattern";
    public static final String X_AMZ_ALGORITHM_TAG = "x-amz-algorithm";
    public static final String X_AMZ_CREDENTIAL_TAG = "x-amz-credential";
    public static final String X_AMZ_DATE_TAG = "x-amz-date";
    public static final String X_AMZ_SIGNATURE_TAG = "x-amz-signature";
    public static final String FILE_TAG = "file";
    public static final String REFERENCE_TYPE_TAG = "reference_type";
    public static final String REFERENCE_ID_TAG = "reference_id";
    public static final String EXTERNAL_URL_TAG = "external_url";
    public static final String LINK_TYPE_TAG = "link_type";

    // NOTIFICATIONS
    public static final String CONTACT_IDS_TAG = "contact_ids";
    public static final String ACTION_TYPE_TAG = "action_type";
    public static final String OPEN_NOTE_TAG = "open_note";
    public static final String OPEN_CONTACT_TAG = "open_contact";

    // VIDEO LINKS
    public static final String VIDEO_TUTORIALS_TAG = "video_tutorials";
    public static final String LINKS_TAG = "links";
    public static final String LINK_TAG = "link";
    public static final String RESOLUTION_TAG = "resolution";
    public static final String VIDEO_NAME_TAG = "name";

    // MULTI SERVER ENVIRONMENT (MSE)
    public static final String ENDPOINT_URL_TAG = "endpoint_url";
    public static final String LOGIN_PARAMS_TAG = "login_params";
    public static final String SAML_RESPONSE_TAG = "SAMLResponse";
    public static final String RELAY_STATE_TAG = "RelayState";
    public static final String ENDPOINT_TAG = "endpoint";
    public static final String ENDPOINTS_TAG = "endpoints";
    public static final String METADATA_URL_TAG = "metadata_url";

    /**
     * Method used to parse the base/start of response.
     */
    public static Object fromString(String responseBody) throws OnePageException {
        String dataString = "";
        try {
            JSONObject responseObject = new JSONObject(responseBody);
            int status = responseObject.getInt(STATUS_TAG);
            String message = responseObject.getString(MESSAGE_TAG);

            // OK response
            if (status == 0 && message.equalsIgnoreCase(OK_TAG)) {
                JSONObject dataObject = responseObject.getJSONObject(DATA_TAG);
                dataString = dataObject.toString();
            }

            // 201 response
            else if (status == 0 && message.equalsIgnoreCase(CREATED_TAG)) {
                JSONObject dataObject = responseObject.getJSONObject(DATA_TAG);
                dataString = dataObject.toString();
            }

            // Error
            else {
                throw ErrorSerializer.fromString(responseBody);
            }

        } catch (JSONException e) {
            LOG.severe("Error parsing response body");
            LOG.severe(e.toString());
        }
        return dataString;
    }

    /**
     * Encode request parameters.
     */
    public static String encodeParams(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            String encodedString = "";
            int i = 0;
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (i > 0) {
                    encodedString += "&";
                }
                try {
                    encodedString += String.format("%s=%s",
                            URLEncoder.encode(param.getKey(), "UTF-8"),
                            URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LOG.severe("Error encoding url params : " + params.toString());
                    LOG.severe(e.toString());
                } finally {
                    i++;
                }
            }
            return encodedString;
        }
        return "";
    }

    public static Number parseNumber(JSONObject object, String key) {
        if (object != null && object.has(key)) {
            try {
                Object valueObject = object.get(key);
                if (valueObject instanceof Number) {
                    // Longs are of type Number in Java.
                    if (valueObject instanceof Long) {
                        // Convert this to a Long.
                        valueObject = ((Number) valueObject).longValue();
                    } else {
                        // Floating point numbers are of type Number in Java.
                        // Convert this to a BigDecimal.
                        valueObject = new BigDecimal(valueObject.toString());
                    }
                    return (Number) valueObject;
                }
            } catch (JSONException e) {
                LOG.severe("No pair found with the key " + key);
                LOG.severe(e.toString());
            }
        }
        return null;
    }

    public static Number parseNumber(String numberAsString) {
        if (!Utilities.notNullOrEmpty(numberAsString)) {
            return null;
        }
        Number number = null;
        try {
            number = Long.valueOf(numberAsString);
        } catch (NumberFormatException e) {
            try {
                number = new BigDecimal(numberAsString);
            } catch (NumberFormatException ex) {
                LOG.severe("Not float value entered. " + e);
                LOG.severe("Not long value entered. " + ex);
            }
        }
        return number;
    }

    public static JSONArray toJsonStringArray(String[] stringArray) {
        JSONArray stringArrayObject = new JSONArray();
        if (stringArray == null) return stringArrayObject;
        for (String aStringArray : stringArray) {
            stringArrayObject.put(aStringArray);
        }
        return stringArrayObject;
    }

    public static JSONArray toJsonStringArray(List<String> stringList) {
        JSONArray stringArrayObject = new JSONArray();
        if (stringList == null) return stringArrayObject;
        for (String aStringList : stringList) {
            stringArrayObject.put(aStringList);
        }
        return stringArrayObject;
    }

    public static String[] toArrayOfStrings(JSONArray stringArray) {
        if (stringArray == null) return new String[0];
        String[] choices = new String[stringArray.length()];
        for (int i = 0; i < stringArray.length(); i++) {
            try {
                String choice = stringArray.getString(i);
                choices[i] = choice;
            } catch (JSONException e) {
                LOG.severe("Error parsing array of Strings");
                LOG.severe(e.toString());
            }
        }
        return choices;
    }

    public static List<String> toListOfStrings(JSONArray stringArray) {
        List<String> choices = new ArrayList<>();
        if (stringArray == null) return choices;
        for (int i = 0; i < stringArray.length(); i++) {
            try {
                String choice = stringArray.getString(i);
                choices.add(choice);
            } catch (JSONException e) {
                LOG.severe("Error parsing array of Strings");
                LOG.severe(e.toString());
            }
        }
        return choices;
    }

    public static String stringSeparator = "__,__";

    public static String toCommaSeparatedString(List<String> strings) {
        if (strings == null) return "";
        // Convert from list of strings to array of strings.
        String[] array = strings.toArray(new String[strings.size()]);
        // Call the version of the function which takes array.
        return toCommaSeparatedString(array);
    }

    public static String toCommaSeparatedString(String[] strings) {
        String result = "";
        if (strings == null) return result;
        for (int i = 0; i < strings.length; i++) {
            result += strings[i];
            // Do not append comma at the end of last element
            if (i < strings.length - 1) {
                result += stringSeparator;
            }
        }
        return result;
    }

    public static List<String> toListOfStrings(String string) {
        if (string == null) return new ArrayList<>();
        String[] array = toArrayOfStrings(string);
        return Arrays.asList(array);
    }

    public static final String[] EMPTY_STRING_ARRAY = {};

    public static String[] toArrayOfStrings(String string) {
        if (string == null) return EMPTY_STRING_ARRAY;
        return string.split(stringSeparator);
    }

    /**
     * Adds a value to a JSONObject with the specified key.
     */
    public static void addJsonValue(Object value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing object value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a value to a JSONObject with the specified key.
     */
    public static void addJsonStringValue(String value, JSONObject object, String key) {
        if ((value != null) && (!value.equals(""))) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing string value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds an int value to a JSONObject with the specified key.
     */
    public static void addJsonIntValue(int value, JSONObject object, String key) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            LOG.severe("Error serializing integer value : " + value);
            LOG.severe(e.toString());
        }
    }

    /**
     * Adds an Integer value to a JSONObject with the specified key.
     */
    public static void addJsonIntegerValue(Integer value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing integer value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a Long value to a JSONObject with the specified key.
     */
    public static void addJsonLongValue(Long value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing long value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a boolean value to a JSONObject with the specified key.
     */
    public static void addJsonBooleanValue(boolean value, JSONObject object, String key) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            LOG.severe("Error serializing boolean value : " + value);
            LOG.severe(e.toString());
        }
    }

    /**
     * Adds a Boolean value to a JSONObject with the specified key.
     */
    public static void addJsonBooleanValue(Boolean value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing boolean value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a Float value to a JSONObject with the specified key.
     */
    public static void addJsonFloatValue(Float value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing float value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a Double value to a JSONObject with the specified key.
     */
    public static void addJsonDoubleValue(Double value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing double value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a BigDecimal value to a JSONObject with the specified key.
     */
    public static void addJsonBigDecimalValue(BigDecimal value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                LOG.severe("Error serializing BigDecimal value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds an Object value to a JSONObject with the specified key.
     */
    public static void addJsonObjectValue(Object value, JSONObject object, String key) {
        if (value != null) {
            try {
                object.put(key, value.toString());
            } catch (JSONException e) {
                LOG.severe("Error serializing Object value : " + value);
                LOG.severe(e.toString());
            }
        }
    }

    /**
     * Adds a nested JSONObject with the specified key if the object has some info (keys > 0).
     */
    public static void addJsonObject(JSONObject input, JSONObject object, String key) {
        try {
            if (input.length() > 0)
                object.put(key, input);
        } catch (JSONException e) {
            LOG.severe("Error serializing JSON object : " + input);
            LOG.severe(e.toString());
        }
    }

    /**
     * Adds a nested JSONObject with the specified key if the object has some info (keys > 0).
     */
    public static void addJsonObject(JSONObject input, JSONArray object) {
        if (input.length() > 0)
            object.put(input);
    }

    /**
     * Adds a nested JSONArray with the specified key if the array has some info (keys > 0).
     */
    public static void addJsonArray(JSONArray input, JSONObject object, String key) {
        try {
            if (input.length() > 0) {
                object.put(key, input);
            }
        } catch (JSONException e) {
            LOG.severe("Error serializing JSON array : " + input);
            LOG.severe(e.toString());
        }
    }

    /**
     * Adds a NULL value to a JSONObject with the specified key.
     */
    public static void addNullValue(JSONObject object, String key) {
        try {
            object.put(key, JSONObject.NULL);
        } catch (JSONException e) {
            LOG.severe("Error adding NULL value");
            LOG.severe(e.toString());
        }
    }
}
