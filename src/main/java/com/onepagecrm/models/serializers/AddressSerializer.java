package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Address;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.internal.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.nullChecks;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AddressSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(AddressSerializer.class.getName());

    public static List<Address> fromJsonArray(final JSONArray addressArray) {
        final List<Address> addresses = new ArrayList<>();
        for (int j = 0; j < addressArray.length(); j++) {
            addresses.add(fromJsonObject(addressArray.optJSONObject(j)));
        }
        return addresses;
    }

    public static Address fromJsonObject(final JSONObject addressObject) {
        Address address = new Address();
        try {
            if (addressObject.has(ADDRESS_TAG)) {
                address.setAddress(nullChecks(addressObject.optString(ADDRESS_TAG)));
            }
            if (addressObject.has(CITY_TAG)) {
                address.setCity(nullChecks(addressObject.optString(CITY_TAG)));
            }
            if (addressObject.has(STATE_TAG)) {
                address.setState(nullChecks(addressObject.optString(STATE_TAG)));
            }
            if (addressObject.has(ZIP_CODE_TAG)) {
                address.setZipCode(nullChecks(addressObject.optString(ZIP_CODE_TAG)));
            }
            if (addressObject.has(COUNTRY_CODE_TAG)) {
                address.setCountryCode(nullChecks(addressObject.optString(COUNTRY_CODE_TAG)));
            }
            if (addressObject.has(TYPE_TAG)) {
                address.setType(Address.Type.fromString(addressObject.getString(TYPE_TAG)));
            }
        } catch (Exception e) {
            LOG.severe("Error parsing Address object");
            LOG.severe(e.toString());
        }
        return address;
    }

    public static JSONObject toJsonObject(final Address address) {
        JSONObject addressObject = new JSONObject();
        if (address != null) {
            addJsonStringValue(address.getAddress(), addressObject, ADDRESS_TAG);
            addJsonStringValue(address.getCity(), addressObject, CITY_TAG);
            addJsonStringValue(address.getState(), addressObject, STATE_TAG);
            addJsonStringValue(address.getZipCode(), addressObject, ZIP_CODE_TAG);
            addJsonStringValue(address.getCountryCode(), addressObject, COUNTRY_CODE_TAG);
            if (address.getType() != null) {
                addJsonStringValue(address.getType().toString(), addressObject, TYPE_TAG);
            }
        }
        return addressObject;
    }

    public static String toJsonString(final Address address) {
        return toJsonObject(address).toString();
    }

    public static JSONArray toJsonArray(final List<Address> addresses) {
        final JSONArray addressArray = new JSONArray();
        if (addresses == null || addresses.isEmpty()) {
            return addressArray;
        }
        for (Address address : addresses) {
            addressArray.put(toJsonObject(address));
        }
        return addressArray;
    }

    public static String toJsonString(final List<Address> addresses) {
        return toJsonArray(addresses).toString();
    }

    /*
     * For contacts (since they have strange serialization requirements).
     */

    public static JSONObject contactAddressToJsonObject(final Address address) {
        JSONObject addressObject = new JSONObject();
        final Address input = address != null ? address : new Address();
        try {
            addressObject.put(ADDRESS_TAG, Utilities.nullToEmpty(input.getAddress()));
            addressObject.put(CITY_TAG, Utilities.nullToEmpty(input.getCity()));
            addressObject.put(STATE_TAG, Utilities.nullToEmpty(input.getState()));
            addressObject.put(ZIP_CODE_TAG, Utilities.nullToEmpty(input.getZipCode()));
            addressObject.put(COUNTRY_CODE_TAG, Utilities.nullToEmpty(input.getCountryCode()));
            if (input.getType() != null) {
                addressObject.put(TYPE_TAG, Utilities.nullToEmpty(input.getType().toString()));
            }
        } catch (JSONException e) {
            LOG.severe("Error serializing Address object");
            LOG.severe(e.toString());
        }
        return addressObject;
    }

    public static String contactAddressToJsonString(final Address address) {
        return contactAddressToJsonObject(address).toString();
    }

    public static JSONArray contactAddressesToJsonArray(final List<Address> addresses) {
        final JSONArray addressArray = new JSONArray();
        final List<Address> input = (addresses == null || addresses.isEmpty()) ? new ArrayList<>() : addresses;
        if (input.size() < Contact.MAX_NUM_ADDRESSES) {
            for (int i = input.size(); i < Contact.MAX_NUM_ADDRESSES; i++) {
                input.add(i, new Address());
            }
        }
        for (int i = 0; i < input.size() && i < Contact.MAX_NUM_ADDRESSES; i++) {
            addressArray.put(contactAddressToJsonObject(input.get(i)));
        }
        return addressArray;
    }

    public static String contactAddressesToJsonString(final List<Address> addresses) {
        return contactAddressesToJsonArray(addresses).toString();
    }
}
