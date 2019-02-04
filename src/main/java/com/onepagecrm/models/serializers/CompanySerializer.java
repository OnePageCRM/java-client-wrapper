package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Company;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.helpers.TagHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.nullChecks;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/12/2016.
 */
public class CompanySerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(CompanySerializer.class.getName());

    public static Company fromString(String responseBody) throws OnePageException {
        Company company = new Company();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject);

        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);

        } catch (JSONException e) {
            LOG.severe("Error parsing Company from JSON.");
            LOG.severe(e.toString());
        }
        return company;
    }

    public static Company fromJsonObject(JSONObject companyObject) {
        // Fix for some objects not having name.
        if (companyObject.has(COMPANY_TAG)) {
            companyObject = companyObject.optJSONObject(COMPANY_TAG);
        }

        Company company = new Company()
                .setId(companyObject.optString(ID_TAG))
                .setName(companyObject.optString(NAME_TAG))
                .setDescription(companyObject.optString(DESCRIPTION_TAG))
                .setPhone(companyObject.optString(PHONE_TAG))
                .setUrl(companyObject.optString(URL_TAG))
                .setCompanyFields(CustomFieldSerializer.fromJsonArray(companyObject.optJSONArray(COMPANY_FIELDS_TAG),
                        CustomField.CF_TYPE_COMPANY))
                .setAddress(AddressSerializer.fromJsonObject(companyObject.optJSONObject(ADDRESS_TAG)))
                .setWonDealsCount(companyObject.optInt(WON_DEALS_COUNT_TAG))
                .setTotalWonAmount(companyObject.optDouble(TOTAL_WON_AMOUNT_TAG, 0d))
                .setPendingDealsCount(companyObject.optInt(PENDING_DEALS_COUNT_TAG))
                .setTotalPendingAmount(companyObject.optDouble(TOTAL_PENDING_AMOUNT_TAG, 0d))
                .setCreatedAt(InstantSerializer.getInstance().parse(companyObject.optString(CREATED_AT_TAG)))
                .setModifiedAt(InstantSerializer.getInstance().parse(companyObject.optString(MODIFIED_AT_TAG)))
                .setContactsCount(companyObject.optInt(CONTACTS_COUNT_TAG))
                .setSyncingStatus(companyObject.optBoolean(SYNCING_STATUS_TAG))
                .setSyncedStatusId(nullChecks(companyObject.optString(SYNCED_STATUS_ID_TAG)))
                .setSyncingTags(companyObject.optBoolean(SYNCING_TAGS_TAG))
                .setSyncedTags(TagHelper.asTags(BaseSerializer.toListOfStrings(companyObject.optJSONArray(SYNCED_TAGS_TAG))));

        try {
            if (companyObject.has(CONTACTS_TAG)) {
                JSONArray contactsObject = companyObject.getJSONArray(CONTACTS_TAG);
                company.setContacts(ContactListSerializer.fromJsonArray(contactsObject));
            }
            if (companyObject.has(PENDING_DEALS_TAG)) {
                JSONArray dealsObject = companyObject.getJSONArray(PENDING_DEALS_TAG);
                company.setPendingDeals(DealListSerializer.fromJsonArray(dealsObject));
            }

        } catch (JSONException e) {
            LOG.severe("Error parsing Company object");
            LOG.severe(e.toString());
            return company;
        }

        return company;
    }

    public static List<Company> fromJsonArray(JSONArray companiesArray) {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < companiesArray.length(); ++i) {
            JSONObject companyObject = companiesArray.optJSONObject(i);
            Company company = fromJsonObject(companyObject);
            if (company != null) {
                companies.add(company);
            }
        }
        return companies;
    }

    public static String toJsonObject(String contactId, String linkedWithId) {
        JSONObject jsonObject = new JSONObject();
        addJsonStringValue(contactId, jsonObject, CONTACT_ID_TAG);
        addJsonStringValue(linkedWithId, jsonObject, LINKED_WITH_TAG);
        return jsonObject.toString();
    }

    public static String toJsonObject(Company company) {
        JSONObject companyObject = new JSONObject();
        if (company != null) {
            addJsonStringValue(company.getId(), companyObject, ID_TAG);
            addJsonStringValue(company.getName(), companyObject, NAME_TAG);
            addJsonStringValue(company.getDescription(), companyObject, DESCRIPTION_TAG);
            addJsonStringValue(company.getPhone(), companyObject, PHONE_TAG);
            addJsonStringValue(company.getUrl(), companyObject, URL_TAG);
            try {
                JSONArray companyFieldsArray = new JSONArray(CustomFieldSerializer.toJsonArray(company.getCompanyFields()));
                addJsonArray(companyFieldsArray, companyObject, COMPANY_FIELDS_TAG);
            } catch (JSONException e) {
                LOG.severe("Error creating Company Fields array while constructing Company object");
                LOG.severe(e.toString());
            }
            JSONObject addressArray = new JSONObject(AddressSerializer.toJsonObject(company.getAddress()));
            addJsonObject(addressArray, companyObject, ADDRESS_TAG);

            addJsonIntegerValue(company.getWonDealsCount(), companyObject, WON_DEALS_COUNT_TAG);
            addJsonDoubleValue(company.getTotalWonAmount(), companyObject, TOTAL_WON_AMOUNT_TAG);
            addJsonIntegerValue(company.getPendingDealsCount(), companyObject, PENDING_DEALS_COUNT_TAG);
            addJsonDoubleValue(company.getTotalPendingAmount(), companyObject, TOTAL_PENDING_AMOUNT_TAG);
            addJsonIntegerValue(company.getContactsCount(), companyObject, CONTACTS_COUNT_TAG);
            addJsonBooleanValue(company.getSyncingStatus(), companyObject, SYNCING_STATUS_TAG);
            addJsonStringValue(company.getSyncedStatusId(), companyObject, SYNCED_STATUS_ID_TAG);
            addJsonBooleanValue(company.getSyncingTags(), companyObject, SYNCING_TAGS_TAG);
            try {
                JSONArray tagsArray = new JSONArray(TagSerializer.toJsonArray(company.getSyncedTags()));
                addJsonArray(tagsArray, companyObject, SYNCED_TAGS_TAG);
            } catch (JSONException e) {
                LOG.severe("Problems generating JSONArray of Tags for this company.");
                LOG.severe(e.toString());
            }
            try {
                JSONArray contactsArray = new JSONArray(ContactSerializer.toJsonArray(company.getContacts()));
                addJsonArray(contactsArray, companyObject, CONTACTS_TAG);
            } catch (JSONException e) {
                LOG.severe("Problems generating JSONArray of Contacts for this company.");
                LOG.severe(e.toString());
            }
        }
        return companyObject.toString();
    }

    public static String toJsonArray(List<Company> companies) {
        JSONArray companiesArray = new JSONArray();
        for (Company company : companies) {
            try {
                companiesArray.put(new JSONObject(toJsonObject(company)));
            } catch (JSONException e) {
                LOG.severe("Error creating JSON out of Company(s).");
                LOG.severe(e.toString());
            }
        }
        return companiesArray.toString();
    }
}
