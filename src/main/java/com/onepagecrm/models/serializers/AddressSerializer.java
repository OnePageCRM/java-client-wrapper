package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Address;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.onepagecrm.models.internal.Utilities.nullChecks;

public class AddressSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(AddressSerializer.class.getName());

    public static List<Address> fromJsonArray(JSONArray addressArray) {
        final List<Address> addresses = new ArrayList<>();
        for (int j = 0; j < addressArray.length(); j++) {
            addresses.add(fromJsonObject(addressArray.optJSONObject(j)));
        }
        return addresses;
    }

    public static Address fromJsonObject(JSONObject addressObject) {
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

    public static JSONObject toJsonObject(Address address) {
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

    public static String toJsonString(Address address) {
        return toJsonObject(address).toString();
    }

    public static JSONArray toJsonArray(List<Address> addresses) {
        final JSONArray addressArray = new JSONArray();
        if (addresses == null || addresses.isEmpty()) {
            return addressArray;
        }
        for (Address address : addresses) {
            addressArray.put(toJsonObject(address));
        }
        return addressArray;
    }

    public static String toJsonString(List<Address> addresses) {
        return toJsonArray(addresses).toString();
    }
}
