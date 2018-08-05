package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.internal.Commission;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Cillian Myles (cillian@onepagecrm.com) on 24/11/2015.
 */
public class DealSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(DealSerializer.class.getName());

    public static Deal fromString(String responseBody) throws OnePageException {
        Deal deal = new Deal();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject);

        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);

        } catch (JSONException e) {
            LOG.severe("Error parsing Deal object from response body");
            LOG.severe(e.toString());
        }
        return deal;
    }

    public static Deal fromJsonObject(JSONObject dataObject) {
        Deal deal = new Deal();
        JSONObject dealObject;
        try {
            // Fix for some objects not having name.
            if (dataObject.has(DEAL_TAG)) {
                dealObject = dataObject.getJSONObject(DEAL_TAG);
            } else {
                dealObject = dataObject;
            }
            // Now parse info.
            if (dealObject.has(ID_TAG)) {
                deal.setId(dealObject.getString(ID_TAG));
            }
            if (dealObject.has(AMOUNT_TAG)) {
                deal.setAmount(dealObject.getDouble(AMOUNT_TAG));
            }
            if (dealObject.has(AUTHOR_TAG)) {
                deal.setAuthor(dealObject.getString(AUTHOR_TAG));
            }
            if (dealObject.has(TEXT_TAG)) {
                deal.setText(dealObject.getString(TEXT_TAG));
            }
            if (dealObject.has(CONTACT_ID_TAG)) {
                deal.setContactId(dealObject.getString(CONTACT_ID_TAG));
            }
            if (dealObject.has(CREATED_AT_TAG)) {
                String createdAtStr = dealObject.getString(CREATED_AT_TAG);
                Date createdAt = DateSerializer.fromFormattedString(createdAtStr);
                deal.setCreatedAt(createdAt);
            }
            if (dealObject.has(DATE_TAG)) {
                String dateStr = dealObject.getString(DATE_TAG);
                Date date = DateSerializer.fromFormattedString(dateStr);
                deal.setDate(date);
            }
            if (dealObject.has(EXPECTED_CLOSE_DATE_TAG)) {
                String expectedCloseDateStr = dealObject.getString(EXPECTED_CLOSE_DATE_TAG);
                Date expectedCloseDate = DateSerializer.fromFormattedString(expectedCloseDateStr);
                deal.setExpectedCloseDate(expectedCloseDate);
            }
            if (dealObject.has(MONTHS_TAG)) {
                deal.setMonths(dealObject.getInt(MONTHS_TAG));
            }
            if (dealObject.has(NAME_TAG)) {
                deal.setName(dealObject.getString(NAME_TAG));
            }
            if (dealObject.has(OWNER_ID_TAG)) {
                deal.setOwnerId(dealObject.getString(OWNER_ID_TAG));
            }
            if (dealObject.has(STAGE_TAG)) {
                deal.setStage(dealObject.getInt(STAGE_TAG));
            }
            if (dealObject.has(STATUS_TAG)) {
                deal.setStatus(dealObject.getString(STATUS_TAG));
            }
            if (dealObject.has(TOTAL_AMOUNT_TAG)) {
                deal.setTotalAmount(dealObject.getDouble(TOTAL_AMOUNT_TAG));
            }
            if (dealObject.has(MODIFIED_AT_TAG)) {
                String modifiedAtStr = dealObject.getString(MODIFIED_AT_TAG);
                Date modifiedAt = DateSerializer.fromFormattedString(modifiedAtStr);
                deal.setModifiedAt(modifiedAt);
            }
            if (dealObject.has(HAS_RELATED_NOTES_TAG)) {
                deal.setHasRelatedNotes(dealObject.getBoolean(HAS_RELATED_NOTES_TAG));
            }
            if (dealObject.has(CLOSE_DATE_TAG) && !dealObject.isNull(CLOSE_DATE_TAG)) {
                String closeDateStr = dealObject.getString(CLOSE_DATE_TAG);
                Date closeDate = DateSerializer.fromFormattedString(closeDateStr);
                deal.setCloseDate(closeDate);
            }
            if (dealObject.has(CONTACT_INFO_TAG)) {
                JSONObject contactInfoObj = dealObject.getJSONObject(CONTACT_INFO_TAG);
                Deal.ContactInfo contactInfo = new Deal.ContactInfo();
                if (contactInfoObj.has(CONTACT_NAME_TAG)) {
                    contactInfo.setContactName(contactInfoObj.getString(CONTACT_NAME_TAG));
                }
                if (contactInfoObj.has(COMPANY_TAG)) {
                    contactInfo.setCompany(contactInfoObj.getString(COMPANY_TAG));
                }
                deal.setContactInfo(contactInfo);
            }
            // CFD fields.
            if (dealObject.has(DEAL_FIELDS_TAG)) {
                deal.setDealFields(CustomFieldSerializer.fromJsonArray(
                        dealObject.optJSONArray(DEAL_FIELDS_TAG), CustomField.CF_TYPE_DEAL));
            }
            if (dealObject.has(COST_TAG) && !dealObject.isNull(COST_TAG)) {
                deal.setCost(dealObject.getDouble(COST_TAG));
            }
            if (dealObject.has(MARGIN_TAG) && !dealObject.isNull(MARGIN_TAG)) {
                deal.setMargin(dealObject.getDouble(MARGIN_TAG));
            }
            if (dealObject.has(TOTAL_COST_TAG) && !dealObject.isNull(TOTAL_COST_TAG)) {
                deal.setTotalCost(dealObject.getDouble(TOTAL_COST_TAG));
            }
            if (dealObject.has(COMMISSION_TAG) && !dealObject.isNull(COMMISSION_TAG)) {
                deal.setCommission(dealObject.getDouble(COMMISSION_TAG));
            }
            if (dealObject.has(COMMISSION_BASE_TAG)) {
                deal.setCommissionBase(Commission.Base.fromString(dealObject.getString(COMMISSION_BASE_TAG)));
            }
            if (dealObject.has(COMMISSION_TYPE_TAG)) {
                deal.setCommissionType(Commission.Type.fromString(dealObject.getString(COMMISSION_TYPE_TAG)));
            }
            if (dealObject.has(COMMISSION_PERCENTAGE_TAG) && !dealObject.isNull(COMMISSION_PERCENTAGE_TAG)) {
                deal.setCommissionPercentage(dealObject.getDouble(COMMISSION_PERCENTAGE_TAG));
            }
            if (dealObject.has(ATTACHMENTS_TAG)) {
                deal.setAttachments(AttachmentSerializer.fromJsonArray(dealObject.optJSONArray(ATTACHMENTS_TAG)));
            }
            // Related notes (outer object).
            if (dataObject.has(RELATED_NOTES_TAG)) {
                deal.setRelatedNotes(NoteSerializer.fromJsonArray(dataObject.optJSONArray(RELATED_NOTES_TAG)));
            }
            if (dataObject.has(DEAL_ITEMS_TAG)) {
                deal.setDealItems(DealItemSerializer.fromJsonArray(dataObject.optJSONArray(DEAL_ITEMS_TAG)));
            }
        } catch (JSONException e) {
            LOG.severe("Error parsing Deal object");
            LOG.severe(e.toString());
        }
        return deal;
    }

    public static String toJsonObject(Deal deal) {
        JSONObject dealObject = new JSONObject();
        addJsonStringValue(deal.getId(), dealObject, ID_TAG);
        addJsonDoubleValue(deal.getAmount(), dealObject, AMOUNT_TAG);
        addJsonStringValue(deal.getAuthor(), dealObject, AUTHOR_TAG);
        addJsonStringValue(deal.getText(), dealObject, TEXT_TAG);
        addJsonStringValue(deal.getContactId(), dealObject, CONTACT_ID_TAG);
        addJsonStringValue(
                DateSerializer.toFormattedDateTimeString(deal.getCreatedAt()),
                dealObject,
                CREATED_AT_TAG
        );
        addJsonStringValue(
                DateSerializer.toFormattedDateString(deal.getDate()),
                dealObject,
                DATE_TAG
        );
        addJsonStringValue(
                DateSerializer.toFormattedDateString(deal.getExpectedCloseDate()),
                dealObject,
                EXPECTED_CLOSE_DATE_TAG
        );
        addJsonIntegerValue(deal.getMonths(), dealObject, MONTHS_TAG);
        addJsonStringValue(deal.getName(), dealObject, NAME_TAG);
        addJsonStringValue(deal.getOwnerId(), dealObject, OWNER_ID_TAG);
        addJsonIntegerValue(deal.getStage(), dealObject, STAGE_TAG);
        addJsonStringValue(deal.getStatus(), dealObject, STATUS_TAG);
        addJsonDoubleValue(deal.getTotalAmount(), dealObject, TOTAL_AMOUNT_TAG);
        addJsonStringValue(
                DateSerializer.toFormattedDateTimeString(deal.getModifiedAt()),
                dealObject,
                MODIFIED_AT_TAG
        );
        addJsonBooleanValue(deal.getHasRelatedNotes(), dealObject, HAS_RELATED_NOTES_TAG);
        addJsonStringValue(
                DateSerializer.toFormattedDateString(deal.getCloseDate()),
                dealObject,
                CLOSE_DATE_TAG
        );
        addJsonDoubleValue(deal.getCost(), dealObject, COST_TAG);
        addJsonDoubleValue(deal.getMargin(), dealObject, MARGIN_TAG);
        addJsonDoubleValue(deal.getTotalCost(), dealObject, TOTAL_COST_TAG);
        addJsonDoubleValue(deal.getCommission(), dealObject, COMMISSION_TAG);
        addJsonObjectValue(deal.getCommissionBase(), dealObject, COMMISSION_BASE_TAG);
        addJsonObjectValue(deal.getCommissionType(), dealObject, COMMISSION_TYPE_TAG);
        addJsonDoubleValue(deal.getCommissionPercentage(), dealObject, COMMISSION_PERCENTAGE_TAG);
        try {
            JSONArray dealFieldsArray = new JSONArray(CustomFieldSerializer.toJsonArray(deal.getDealFields()));
            addJsonArray(dealFieldsArray, dealObject, DEAL_FIELDS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Deal Fields array while constructing Deal object");
            LOG.severe(e.toString());
        }
        return dealObject.toString();
    }

    public static String toJsonArray(DealList deals) {
        JSONArray dealsArray = new JSONArray();
        if (deals != null && !deals.isEmpty()) {
            for (int i = 0; i < deals.size(); i++) {
                try {
                    dealsArray.put(new JSONObject(toJsonObject(deals.get(i))));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of Deals");
                    LOG.severe(e.toString());
                }
            }
        }
        return dealsArray.toString();
    }

    // TODO: tidy up below methods

    public static JSONObject toJsonObjectForPartial(Deal deal) {
        JSONObject dealObject = new JSONObject();
        if (deal == null) return dealObject;
        addJsonStringValue(stringValue(deal.getId()), dealObject, ID_TAG);
        addJsonStringValue(stringValue(deal.getContactId()), dealObject, CONTACT_ID_TAG);
        addJsonStringValue(stringValue(deal.getName()), dealObject, NAME_TAG);
        addJsonStringValue(stringValue(deal.getOwnerId()), dealObject, OWNER_ID_TAG);
        addJsonStringValue(stringValue(deal.getStage()), dealObject, STAGE_TAG);
        addJsonStringValue(stringValue(deal.getStatus()), dealObject, STATUS_TAG);
        addJsonStringValue(stringValue(deal.getAuthor()), dealObject, AUTHOR_TAG);
        addJsonStringValue(stringValue(deal.getText()), dealObject, TEXT_TAG);
        addJsonStringValue(
                stringValue(DateSerializer.toFormattedDateString(deal.getDate())),
                dealObject,
                DATE_TAG
        );
        addJsonStringValue(
                stringValue(DateSerializer.toFormattedDateString(deal.getExpectedCloseDate())),
                dealObject,
                EXPECTED_CLOSE_DATE_TAG
        );

        addJsonStringValue(
                DateSerializer.toFormattedDateString(deal.getCloseDate()),
                dealObject,
                CLOSE_DATE_TAG
        );
        addJsonStringValue(stringValue(deal.getAmount(), "0"), dealObject, AMOUNT_TAG);
        addJsonStringValue(stringValue(deal.getMonths(), "1"), dealObject, MONTHS_TAG);
        addJsonStringValue(stringValue(deal.getTotalAmount(), "0"), dealObject, TOTAL_AMOUNT_TAG);
        addJsonStringValue(stringValue(deal.getCost(), "0"), dealObject, COST_TAG);
        addJsonStringValue(stringValue(deal.getMargin(), "0"), dealObject, MARGIN_TAG);
        addJsonStringValue(stringValue(deal.getTotalCost(), "0"), dealObject, TOTAL_COST_TAG);
        addJsonStringValue(stringValue(deal.getCommission(), "0"), dealObject, COMMISSION_TAG);
        addJsonStringValue(stringValue(deal.getCommissionPercentage(), "0"), dealObject, COMMISSION_PERCENTAGE_TAG);
        addJsonObjectValue(deal.getCommissionBase(), dealObject, COMMISSION_BASE_TAG);
        addJsonObjectValue(deal.getCommissionType(), dealObject, COMMISSION_TYPE_TAG);
        try {
            JSONArray dealFieldsArray = new JSONArray(CustomFieldSerializer.toJsonArray(deal.getDealFields()));
            addJsonArray(dealFieldsArray, dealObject, DEAL_FIELDS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Deal Fields array while constructing Deal object");
            LOG.severe(e.toString());
        }
        return dealObject;
    }

    private static final String NULL = "null";

    private static String stringValue(Object value) {
        return stringValue(value, "");
    }

    private static String stringValue(Object value, String defaultValue) {
        String toString = String.valueOf(value);
        boolean validToString = value != null && !toString.equalsIgnoreCase(NULL) && !toString.endsWith("@" + value.hashCode());
        return validToString ? toString : defaultValue;
    }
}
