package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Account;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 01/08/2017.
 */
@SuppressWarnings("WeakerAccess")
public class UserSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(ContactList.class.getName());

    public static User fromString(String dataString) {
        try {
            JSONObject dataObject = new JSONObject(dataString);
            if (dataObject.has(BOOTSTRAP_TAG)) dataObject = dataObject.getJSONObject(BOOTSTRAP_TAG);
            String userId = dataObject.getString(USER_ID_TAG);
            String authKey = dataObject.getString(AUTH_KEY_TAG);
            String accountType = dataObject.getString(ACCOUNT_TYPE_TAG);

            // Basic details.
            User user = new User()
                    .setId(userId)
                    .setAuthKey(authKey)
                    .setAccountType(accountType);

            // Custom, Company and Deal fields.
            user.setAccount(new Account()
                    .setCustomFields(CustomFieldSerializer.fromJsonArray(
                            dataObject.optJSONArray(CUSTOM_FIELDS_TAG), CustomField.CF_TYPE_CONTACT))
                    .setCompanyFields(CustomFieldSerializer.fromJsonArray(
                            dataObject.optJSONArray(COMPANY_FIELDS_TAG), CustomField.CF_TYPE_COMPANY))
                    .setDealFields(CustomFieldSerializer.fromJsonArray(
                            dataObject.optJSONArray(DEAL_FIELDS_TAG), CustomField.CF_TYPE_DEAL)));

            // Call Results.
            user.getAccount().setCallResults(CallResultSerializer.fromJsonObject(dataObject));

            // More user details.
            JSONObject outsideUserObject = dataObject.getJSONObject(USER_TAG);
            JSONObject userObject = outsideUserObject.getJSONObject(USER_TAG);
            user = fromJsonObject(userObject, user);

            // Add other team members to Account object
            List<User> team = fromJsonArray(dataObject.getJSONArray(TEAM_TAG));
            // Added current User as first in User's list
            team.add(0, user);
            // Set list of Users (team) for this account.
            user.getAccount().setTeam(team);

            return user;

        } catch (JSONException e) {
            LOG.severe("Error parsing user response");
            LOG.severe(e.toString());
            return new User();
        }
    }

    public static User fromJsonObject(JSONObject userObject) {
        return fromJsonObject(userObject, new User());
    }

    public static User fromJsonObject(JSONObject userObject, User user) {
        try {
            if (userObject.has(ID_TAG)) {
                user.setId(userObject.getString(ID_TAG));
            }
            if (userObject.has(FIRST_NAME_TAG)) {
                user.setFirstName(userObject.getString(FIRST_NAME_TAG));
            }
            if (userObject.has(LAST_NAME_TAG)) {
                user.setLastName(userObject.getString(LAST_NAME_TAG));
            }
            if (userObject.has(EMAIL_TAG)) {
                user.setEmail(userObject.getString(EMAIL_TAG));
            }
            if (userObject.has(COMPANY_NAME_TAG)) {
                user.setCompanyName(userObject.getString(COMPANY_NAME_TAG));
            }
            if (userObject.has(PHOTO_URL_TAG)) {
                user.setPhotoUrl(userObject.getString(PHOTO_URL_TAG));
            }
            if (userObject.has(BCC_EMAIL_TAG)) {
                user.setBccEmail(userObject.getString(BCC_EMAIL_TAG));
            }
            if (userObject.has(COUNTRY_CODE_TAG)) {
                user.setCountryCode(userObject.optString(COUNTRY_CODE_TAG));
            }
            if (userObject.has(ACCOUNT_RIGHTS_TAG)) {
                JSONArray accountRightsArray = userObject.getJSONArray(ACCOUNT_RIGHTS_TAG);
                List<String> accountRights = BaseSerializer.toListOfStrings(accountRightsArray);
                user.setAccountRights(accountRights);
            }
            return user;

        } catch (JSONException e) {
            LOG.severe("Error parsing user JSON object");
            LOG.severe(e.toString());
            return new User();
        }
    }

    public static List<User> fromJsonArray(JSONArray teamArray) {
        List<User> users = new ArrayList<>();
        for (int j = 0; j < teamArray.length(); j++) {
            JSONObject userObject;
            try {
                userObject = teamArray.getJSONObject(j);
                userObject = userObject.getJSONObject(USER_TAG);
                users.add(fromJsonObject(userObject));
            } catch (JSONException e) {
                LOG.severe("Error parsing team (user) array");
                LOG.severe(e.toString());
            }
        }
        return users;
    }

    public static String toJsonObject(User user) {
        JSONObject userObject = new JSONObject();

        // TODO : create User object here!!
        addJsonStringValue(user.getId(), userObject, ID_TAG);
        addJsonStringValue(user.getAuthKey(), userObject, AUTH_KEY_TAG);
        addJsonStringValue(user.getAccountType(), userObject, ACCOUNT_TYPE_TAG);
        addJsonStringValue(user.getBccEmail(), userObject, BCC_EMAIL_TAG);
        addJsonStringValue(user.getCompanyName(), userObject, COMPANY_NAME_TAG);
        addJsonStringValue(user.getEmail(), userObject, EMAIL_TAG);
        addJsonStringValue(user.getFirstName(), userObject, FIRST_NAME_TAG);
        addJsonStringValue(user.getLastName(), userObject, LAST_NAME_TAG);
        addJsonStringValue(user.getPhotoUrl(), userObject, PHOTO_URL_TAG);
        addJsonStringValue(user.getCountryCode(), userObject, COUNTRY_CODE_TAG);

        // SALES ??

        // ACCOUNT

        // CALL RESULTS ??

        return userObject.toString();
    }
}
