package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Action;
import com.onepagecrm.models.Address;
import com.onepagecrm.models.Call;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.CustomField;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.Email;
import com.onepagecrm.models.Note;
import com.onepagecrm.models.Phone;
import com.onepagecrm.models.Tag;
import com.onepagecrm.models.Url;
import com.onepagecrm.models.helpers.TagHelper;
import com.onepagecrm.models.internal.SalesCycleClosure;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class ContactSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(ContactSerializer.class.getName());

    public static Contact fromString(String responseBody) throws OnePageException {
        Contact contact = new Contact();
        try {
            String parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject);

        } catch (ClassCastException e) {
            throw (OnePageException) BaseSerializer.fromString(responseBody);

        } catch (JSONException e) {
            LOG.severe("Error parsing Contact object from response body");
            LOG.severe(e.toString());
        }
        return contact;
    }

    public static Contact fromJsonObject(JSONObject contactsElementObject) {
        Contact contact = new Contact();
        List<Action> actions = new LinkedList<>();
        try {
            // Handle nesting/nested objects.
            JSONObject contactObject = new JSONObject();
            if (contactsElementObject.has(LINKED_CONTACT_TAG)) {
                contactsElementObject = contactsElementObject.getJSONObject(LINKED_CONTACT_TAG);
            }
            if (contactsElementObject.has(CONTACT_TAG)) {
                contactObject = contactsElementObject.getJSONObject(CONTACT_TAG);
            }
            // Do actual parsing of contact data.
            if (contactObject.has(ID_TAG)) {
                contact.setId(contactObject.getString(ID_TAG));
            }
            if (contactObject.has(COMPANY_NAME_TAG)) {
                contact.setCompanyName(contactObject.getString(COMPANY_NAME_TAG));
            }
            if (contactObject.has(COMPANY_ID_TAG)) {
                contact.setCompanyId(contactObject.getString(COMPANY_ID_TAG));
            }
            if (contactObject.has(TYPE_TAG)) {
                contact.setType(contactObject.getString(TYPE_TAG));
            }
            if (contactObject.has(TITLE_TAG)) {
                contact.setTitle(contactObject.getString(TITLE_TAG));
            }
            if (contactObject.has(FIRST_NAME_TAG)) {
                contact.setFirstName(contactObject.getString(FIRST_NAME_TAG));
            }
            if (contactObject.has(LAST_NAME_TAG)) {
                contact.setLastName(contactObject.getString(LAST_NAME_TAG));
            }
            if (contactObject.has(LETTER_TAG)) {
                contact.setLetter(contactObject.getString(LETTER_TAG));
            }
            if (contactObject.has(OWNER_ID_TAG)) {
                contact.setOwnerId(contactObject.getString(OWNER_ID_TAG));
            }
            if (contactObject.has(BACKGROUND_TAG)) {
                contact.setBackground(contactObject.getString(BACKGROUND_TAG));
            }
            if (contactObject.has(JOB_TITLE_TAG)) {
                contact.setJobTitle(contactObject.getString(JOB_TITLE_TAG));
            }
            if (contactObject.has(LEAD_SOURCE_ID_TAG)) {
                contact.setLeadSourceId(contactObject.getString(LEAD_SOURCE_ID_TAG));
            }
            if (contactObject.has(PENDING_DEAL_TAG)) {
                contact.setHasPendingDeal(contactObject.getBoolean(PENDING_DEAL_TAG));
            }
            if (contactObject.has(TOTAL_PENDINGS_TAG)) {
                contact.setTotalPending(contactObject.getDouble(TOTAL_PENDINGS_TAG));
            }
            if (contactObject.has(TOTAL_DEALS_COUNT_TAG) && !contactObject.isNull(TOTAL_DEALS_COUNT_TAG)) {
                contact.setTotalDealsCount(contactObject.optInt(TOTAL_DEALS_COUNT_TAG));
            }
            if (contactObject.has(PHOTO_URL_TAG)) {
                contact.setPhotoUrl(contactObject.getString(PHOTO_URL_TAG));
            }
            if (contactObject.has(CLOSED_SALES_TAG)) {
                JSONArray closedArray = contactObject.getJSONArray(CLOSED_SALES_TAG);
                Map<String, SalesCycleClosure> closedCycles =
                        ClosedSalesSerializer.mapFromJsonArray(closedArray, contact.getId());
                contact.setSalesClosedFor(closedCycles.size() == 0 ? null : closedCycles);
            }
            if (contactObject.has(STARRED_TAG)) {
                contact.setStarred(contactObject.getBoolean(STARRED_TAG));
            }
            if (contactObject.has(STATUS_TAG)) {
                contact.setStatus(contactObject.getString(STATUS_TAG));
            }
            if (contactObject.has(STATUS_ID_TAG)) {
                contact.setStatusId(contactObject.getString(STATUS_ID_TAG));
            }
            if (contactObject.has(CREATED_AT_TAG)) {
                contact.setCreatedAt(InstantSerializer.getInstance().parse(contactObject.optString(CREATED_AT_TAG)));
            }
            if (contactObject.has(MODIFIED_AT_TAG)) {
                contact.setModifiedAt(InstantSerializer.getInstance().parse(contactObject.optString(MODIFIED_AT_TAG)));
            }
            // Google Contact data.
            if (contactObject.has(GOOGLE_CONTACTS_DATA_TAG) && !contactObject.isNull(GOOGLE_CONTACTS_DATA_TAG)) {
                JSONObject googleContactsDataObject = contactObject.getJSONObject(GOOGLE_CONTACTS_DATA_TAG);
                contact.setGoogleId(googleContactsDataObject.optString(ID_TAG));
                contact.setGoogleAccountEmail(googleContactsDataObject.optString(ACCOUNT_EMAIL_TAG));
            }
            // Tags.
            if (contactObject.has(TAGS_TAG)) {
                List<String> tagNames = BaseSerializer.toListOfStrings(contactObject.getJSONArray(TAGS_TAG));
                contact.setTags(TagHelper.asTags(tagNames));
            }
            // Custom Fields.
            if (contactObject.has(CUSTOM_FIELDS_TAG)) {
                JSONArray customFieldsArray = contactObject.getJSONArray(CUSTOM_FIELDS_TAG);
                List<CustomField> customFields =
                        CustomFieldSerializer.fromJsonArray(customFieldsArray, CustomField.CF_TYPE_CONTACT);
                if (!customFields.isEmpty()) contact.setCustomFields(customFields);
            }
            // Phones.
            if (contactObject.has(PHONES_TAG)) {
                JSONArray phonesArray = contactObject.getJSONArray(PHONES_TAG);
                List<Phone> phones = PhoneSerializer.fromJsonArray(phonesArray);
                if (!phones.isEmpty()) contact.setPhones(phones);
            }
            // Emails.
            if (contactObject.has(EMAILS_TAG)) {
                JSONArray emailsArray = contactObject.getJSONArray(EMAILS_TAG);
                List<Email> emails = EmailSerializer.fromJsonArray(emailsArray);
                if (!emails.isEmpty()) contact.setEmails(emails);
            }
            // Websites.
            if (contactObject.has(URLS_TAG)) {
                JSONArray urlsArray = contactObject.getJSONArray(URLS_TAG);
                List<Url> urls = UrlSerializer.fromJsonArray(urlsArray);
                if (!urls.isEmpty()) contact.setUrls(urls);
            }
            // Address.
            if (contactObject.has(ADDRESS_LIST_TAG)) {
                JSONArray addressArray = contactObject.getJSONArray(ADDRESS_LIST_TAG);
                List<Address> addresses = AddressSerializer.fromJsonArray(addressArray);
                contact.setAddresses(addresses);
            }
            // Next Actions.
            if (contactsElementObject.has(NEXT_ACTIONS_TAG)) {
                JSONArray nextActionsArray = contactsElementObject.getJSONArray(NEXT_ACTIONS_TAG);
                List<Action> nextActions = ActionSerializer.fromJsonArray(nextActionsArray);
                actions.addAll(nextActions);
            }
            // Next Action.
            if (contactsElementObject.has(NEXT_ACTION_TAG)) {
                JSONObject nextActionObject = contactsElementObject.getJSONObject(NEXT_ACTION_TAG);
                Action nextAction = ActionSerializer.fromJsonObject(nextActionObject);
                contact.setNextAction(nextAction);
            }
            // Queued Actions.
            if (contactsElementObject.has(QUEUED_ACTIONS_TAG)) {
                JSONArray queuedActionsArray = contactsElementObject.getJSONArray(QUEUED_ACTIONS_TAG);
                List<Action> queuedActions = ActionSerializer.fromJsonArray(queuedActionsArray);
                actions.addAll(queuedActions);
            }
            contact.setActions(actions);
            // Deals.
            if (contactsElementObject.has(DEALS_TAG)) {
                JSONArray dealsArray = contactsElementObject.getJSONArray(DEALS_TAG);
                List<Deal> deals = DealListSerializer.fromJsonArray(dealsArray);
                contact.setDeals(deals);
            }
            // Notes.
            if (contactsElementObject.has(NOTES_TAG)) {
                JSONArray notesArray = contactsElementObject.getJSONArray(NOTES_TAG);
                List<Note> notes = NoteSerializer.fromJsonArray(notesArray);
                contact.setNotes(notes);
            }
            // Calls.
            if (contactsElementObject.has(CALLS_TAG)) {
                JSONArray callsArray = contactsElementObject.getJSONArray(CALLS_TAG);
                List<Call> calls = CallSerializer.fromJsonArray(callsArray);
                contact.setCalls(calls);
            }
            // Company.
            if (contactsElementObject.has(COMPANY_TAG)) {
                JSONObject companyObject = contactsElementObject.optJSONObject(COMPANY_TAG);
                contact.setCompany(CompanySerializer.fromJsonObject(companyObject));
            }
            // Linked ids.
            List<String> linkedWith = new ArrayList<>();
            if (contactsElementObject.has(LINKED_WITH_TAG)) {
                JSONArray linkedWithArray = contactsElementObject.getJSONArray(LINKED_WITH_TAG);
                for (int i = 0; i < linkedWithArray.length(); i++) {
                    linkedWith.add(linkedWithArray.getString(i));
                }
            }
            contact.setLinkedWithIds(linkedWith);

            return contact;

        } catch (JSONException e) {
            LOG.severe("Error parsing Contact object");
            LOG.severe(e.toString());
            return new Contact();
        }
    }

    /**
     * Serialize Contact object to JSON.
     */
    public static String toJsonObjectFull(Contact contact) {

        JSONObject contactAndActionsObject = new JSONObject();
        JSONObject contactObject = new JSONObject();
        try {
            contactObject = new JSONObject(toJsonObject(contact));
        } catch (JSONException e) {
            LOG.severe("Error creating inner Contact object while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Actions.
        try {
            JSONArray nextActionsArray = new JSONArray(ActionSerializer.toJsonArray(contact.getActions()));
            addJsonArray(nextActionsArray, contactAndActionsObject, NEXT_ACTIONS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Next Action array while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Next Action.
        try {
            JSONObject nextActionObject = new JSONObject(ActionSerializer.toJsonObject(contact.getNextAction()));
            addJsonObject(nextActionObject, contactAndActionsObject, NEXT_ACTION_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Next Action object while constructing Contact object");
            LOG.severe(e.toString());
        }

        addJsonObject(contactObject, contactAndActionsObject, CONTACT_TAG);

        return contactAndActionsObject.toString();
    }

    /**
     * Serialize Contact object to JSON.
     */
    public static String toJsonObject(Contact contact) {

        JSONObject contactObject = new JSONObject();

        addJsonStringValue(contact.getId(), contactObject, ID_TAG);
        addJsonStringValue(contact.getType(), contactObject, TYPE_TAG);
        addJsonStringValue(contact.getTitle(), contactObject, TITLE_TAG);
        addJsonStringValue(contact.getFirstName(), contactObject, FIRST_NAME_TAG);
        addJsonStringValue(contact.getLastName(), contactObject, LAST_NAME_TAG);
        addJsonStringValue(contact.getCompanyName(), contactObject, COMPANY_NAME_TAG);
        addJsonStringValue(contact.getCompanyId(), contactObject, COMPANY_ID_TAG);
        addJsonStringValue(contact.getJobTitle(), contactObject, JOB_TITLE_TAG);
        addJsonStringValue(contact.getBackground(), contactObject, BACKGROUND_TAG);
        addJsonStringValue(contact.getStatus(), contactObject, STATUS_TAG);
        addJsonStringValue(contact.getStatusId(), contactObject, STATUS_ID_TAG);
        addJsonStringValue(contact.getLeadSourceId(), contactObject, LEAD_SOURCE_ID_TAG);
        addJsonBooleanValue(contact.getHasPendingDeal(), contactObject, PENDING_DEAL_TAG);
        addJsonDoubleValue(contact.getTotalPending(), contactObject, TOTAL_PENDINGS_TAG);
        addJsonStringValue(contact.getOwnerId(), contactObject, OWNER_ID_TAG);
        addJsonStringValue(contact.getPhotoUrl(), contactObject, PHOTO_URL_TAG);
        addJsonBooleanValue(contact.getStarred(), contactObject, STARRED_TAG);

        // Serialize Custom Fields.
        try {
            JSONArray customFieldsArray = new JSONArray(CustomFieldSerializer.toJsonArray(contact.getCustomFields()));
            addJsonArray(customFieldsArray, contactObject, CUSTOM_FIELDS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating CustomField array while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Address.
        JSONArray addressArray = AddressSerializer.contactAddressesToJsonArray(contact.getAddresses());
        addJsonArray(addressArray, contactObject, ADDRESS_LIST_TAG);

        // Serialize Phones.
        try {
            JSONArray phonesArray = new JSONArray(PhoneSerializer.toJsonArray(contact.getPhones()));
            addJsonArray(phonesArray, contactObject, PHONES_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Phone array while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Emails.
        try {
            JSONArray emailsArray = new JSONArray(EmailSerializer.toJsonArray(contact.getEmails()));
            addJsonArray(emailsArray, contactObject, EMAILS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Email array while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Urls.
        try {
            JSONArray urlsArray = new JSONArray(UrlSerializer.toJsonArray(contact.getUrls()));
            addJsonArray(urlsArray, contactObject, URLS_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Url array while constructing Contact object");
            LOG.severe(e.toString());
        }

        // Serialize Tags.
        List<Tag> tags = contact.getTags();
        List<String> tagNames = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            for (int i = 0; i < contact.getTags().size(); i++) {
                tagNames.add(contact.getTags().get(i).getName());
            }
        }
        addJsonArray(BaseSerializer.toJsonStringArray(tagNames), contactObject, TAGS_TAG);

        // Serialize Company.
        try {
            JSONObject companyObject = new JSONObject(CompanySerializer.toJsonObject(contact.getCompany()));
            addJsonObject(companyObject, contactObject, COMPANY_TAG);
        } catch (JSONException e) {
            LOG.severe("Error creating Company object while constructing Contact object");
            LOG.severe(e.toString());
        }

        return contactObject.toString();
    }

    public static String toJsonArray(ContactList contacts) {
        JSONArray contactsArray = new JSONArray();
        if (contacts != null && !contacts.isEmpty()) {
            for (Contact contact : contacts) {
                try {
                    String contactString = toJsonObject(contact);
                    contactsArray.put(new JSONObject(contactString));
                } catch (JSONException e) {
                    LOG.severe("Error serializing Contacts array out of ContactList");
                    LOG.severe(e.toString());
                }
            }
        }
        return contactsArray.toString();
    }
}
