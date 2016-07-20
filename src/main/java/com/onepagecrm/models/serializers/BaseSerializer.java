package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BaseSerializer {

    private static final Logger LOG = Logger.getLogger(BaseSerializer.class.getName());

    // LOGIN TAGS
    public static final String LOGIN_TAG = "login";
    public static final String PASSWORD_TAG = "password";
    public static final String DATA_TAG = "data";
    public static final String USER_ID_TAG = "user_id";
    public static final String AUTH_KEY_TAG = "auth_key";
    public static final String ACCOUNT_TYPE_TAG = "account_type";

    // LOGIN RESPONSE / USER TAGS
    public static final String USER_TAG = "user";
    public static final String FIRST_NAME_TAG = "first_name";
    public static final String LAST_NAME_TAG = "last_name";
    public static final String COMPANY_NAME_TAG = "company_name";
    public static final String PHOTO_URL_TAG = "photo_url";
    public static final String BCC_EMAIL_TAG = "bcc_email";
    public static final String SALES_TAG = "sales";
    public static final String ACCOUNT_TAG = "account";
    public static final String SETTINGS_TAG = "settings";

    // CONTACT TAGS
    public static final String CONTACTS_TAG = "contacts";
    public static final String CONTACT_TAG = "contact";
    public static final String ID_TAG = "id";
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
    public static final String CLOSE_SALES_CYCLE_COMMENT_TAG = "comment";

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
    public static final String DATE_COLOR_TAG = "date_color";
    public static final String QUEUED_ACTION_TAG = "queued_action";
    public static final String QUEUED_ACTIONS_TAG = "queued_actions";
    public static final String PREDEFINED_ACTION_TAG = "predefined_action";
    public static final String PREDEFINED_ACTIONS_TAG = "predefined_actions";
    public static final String EXACT_TIME_TAG = "exact_time";
    public static final String EXACT_TIME_INT_TAG = "exact_time_int";

    // CLOSED SALES CYCLE.
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
    public static final String ATTACHMENTS_TAG = "attachment";

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

    // SETTINGS TAGS
    public static final String TIME_ZONE_TAG = "time_zone";
    public static final String DATE_FORMAT_TAG = "date_format";
    public static final String LISTING_SIZE_TAG = "listing_size";
    public static final String POPULAR_COUNTRIES_TAG = "popular_countries";
    public static final String CURRENCY_SYMBOL_TAG = "currency_symbol";
    public static final String DEFAULT_CONTACT_TYPE_TAG = "default_contact_type";
    public static final String SHOW_TIDY_STREAM_TAG = "show_tidy_stream";

    // DEALS TAGS
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

    // NOTES
    public static final String NOTE_TAG = "note";
    public static final String NOTES_TAG = "notes";

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

    /**
     * Method used to parse the base/start of response.
     *
     * @param responseBody
     * @return
     * @throws OnePageException
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
     *
     * @param params
     * @return
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
        if (object.has(key)) {
            try {
                Object valueObject = object.get(key);
                if (valueObject instanceof Number) {
                    // Integers are of type Number in Java.
                    if (valueObject instanceof Integer) {
                        // Convert this to an Integer.
                        valueObject = ((Number) valueObject).intValue();
                    } else {
                        // Floating point numbers are of type Number in Java.
                        // Convert this to a Double (big Float).
                        valueObject = ((Number) valueObject).doubleValue();
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
        Number number = null;
        try {
            number = Integer.valueOf(numberAsString);
        } catch (NumberFormatException e) {
            try {
                number = Double.valueOf(numberAsString);
            } catch (NumberFormatException ex) {
                LOG.severe("Not float value entered. " + e);
                LOG.severe("Not integer value entered. " + ex);
            }
        }
        return number;
    }

    public static JSONArray toJsonStringArray(String[] stringArray) {
        JSONArray stringArrayObject = new JSONArray();
        if (stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; i++) {
                stringArrayObject.put(stringArray[i]);
            }
        }
        return stringArrayObject;
    }

    public static JSONArray toJsonStringArray(List<String> stringList) {
        JSONArray stringArrayObject = new JSONArray();
        if (stringList != null) {
            if (stringList.size() > 0) {
                for (int i = 0; i < stringList.size(); i++) {
                    stringArrayObject.put(stringList.get(i));
                }
            }
        }
        return stringArrayObject;
    }

    public static String[] toArrayOfStrings(JSONArray stringArray) {
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
        if (strings != null) {
            // Convert from list of strings to array of strings.
            String[] array = strings.toArray(new String[strings.size()]);
            // Call the version of the function which takes array.
            return toCommaSeparatedString(array);
        } else {
            return "";
        }
    }

    public static String toCommaSeparatedString(String[] strings) {
        String result = "";
        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                result += strings[i];
                // Do not append comma at the end of last element
                if (i < strings.length - 1) {
                    result += stringSeparator;
                }
            }
        }
        return result;
    }

    public static List<String> toListOfStrings(String string) {
        if (string != null) {
            String[] array = toArrayOfStrings(string);
            return Arrays.asList(array);
        } else {
            return new ArrayList<>();
        }
    }

    public static final String[] EMPTY_STRING_ARRAY = {};

    public static String[] toArrayOfStrings(String string) {
        if (string != null) {
            return string.split(stringSeparator);
        } else {
            return EMPTY_STRING_ARRAY;
        }
    }

    /**
     * Adds a value to a JSONObject with the specified key.
     *
     * @param value
     * @param object
     * @param key
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
     *
     * @param value
     * @param object
     * @param key
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
     *
     * @param value
     * @param object
     * @param key
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
     * Adds an integer value to a JSONObject with the specified key.
     *
     * @param value
     * @param object
     * @param key
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
     * Adds a long value to a JSONObject with the specified key.
     *
     * @param value
     * @param object
     * @param key
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
     *
     * @param value
     * @param object
     * @param key
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
     * Adds a boolean value to a JSONObject with the specified key.
     *
     * @param value
     * @param object
     * @param key
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
     *
     * @param value
     * @param object
     * @param key
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
     * Adds a Float value to a JSONObject with the specified key.
     *
     * @param value
     * @param object
     * @param key
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
     * Adds a nested JSONObject with the specified key if the object has some info (keys > 0).
     *
     * @param input
     * @param object
     * @param key
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
     *
     * @param input
     * @param object
     */
    public static void addJsonObject(JSONObject input, JSONArray object) {
        if (input.length() > 0)
            object.put(input);
    }

    /**
     * Adds a nested JSONArray with the specified key if the array has some info (keys > 0).
     *
     * @param input
     * @param object
     * @param key
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
}
