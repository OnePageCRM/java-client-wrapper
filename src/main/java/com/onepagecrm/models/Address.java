package com.onepagecrm.models;

import com.onepagecrm.models.serializers.AddressSerializer;

import java.io.Serializable;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

public class Address extends BaseResource implements Serializable {

    /*
     * Constants.
     */

    private static final String TYPE_WORK = "work";
    private static final String TYPE_HOME = "home";
    private static final String TYPE_BILLING = "billing";
    private static final String TYPE_DELIVERY = "delivery";
    private static final String TYPE_OTHER = "other"; // Catch all.

    /*
     * Member variables.
     */

    public enum Type {
        WORK(TYPE_WORK),
        HOME(TYPE_HOME),
        BILLING(TYPE_BILLING),
        DELIVERY(TYPE_DELIVERY),
        OTHER(TYPE_OTHER);

        private String type;

        Type(final String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }

        public static Type fromString(final String type) {
            if (type == null) return null;
            switch (type) {
                case TYPE_WORK:
                    return WORK;
                case TYPE_HOME:
                    return HOME;
                case TYPE_BILLING:
                    return BILLING;
                case TYPE_DELIVERY:
                    return DELIVERY;
                default:
                    OTHER.type = type;
                    return OTHER;
            }
        }
    }

    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String countryCode;
    private Type type;

    /*
     * Object methods.
     */

    public Address() {}

    @Override
    public String toString() {
        return AddressSerializer.toJsonString(this);
    }

    public boolean isValid() {
        return notNullOrEmpty(address) ||
                notNullOrEmpty(city) ||
                notNullOrEmpty(state) ||
                notNullOrEmpty(zipCode) ||
                notNullOrEmpty(countryCode);
    }

    @Override
    public boolean equals(Object object) {
        boolean addressObjectsEqual = false;
        boolean addressesEqual = false;
        boolean citiesEqual = false;
        boolean statesEqual = false;
        boolean zipCodesEqual = false;
        boolean countryCodesEqual = false;
        if (object instanceof Address) {
            Address toCompare = (Address) object;
            if (this.address != null && toCompare.address != null) {
                addressesEqual = this.address.equals(toCompare.address);
            }
            if (this.city != null && toCompare.city != null) {
                citiesEqual = this.city.equals(toCompare.city);
            }
            if (this.state != null && toCompare.state != null) {
                statesEqual = this.state.equals(toCompare.state);
            }
            if (this.zipCode != null && toCompare.zipCode != null) {
                zipCodesEqual = this.zipCode.equals(toCompare.zipCode);
            }
            if (this.countryCode != null && toCompare.countryCode != null) {
                countryCodesEqual = this.countryCode.equals(toCompare.countryCode);
            }
            addressObjectsEqual = addressesEqual
                    && citiesEqual
                    && statesEqual
                    && zipCodesEqual
                    && countryCodesEqual;
        }
        return addressObjectsEqual;
    }

    public String getAddress() {
        return address;
    }

    public Address setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Address setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Address setType(Type type) {
        this.type = type;
        return this;
    }
}
