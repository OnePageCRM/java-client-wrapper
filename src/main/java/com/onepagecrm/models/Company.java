package com.onepagecrm.models;

import com.onepagecrm.net.ApiResource;

import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/12/2016.
 */
public class Company extends ApiResource {

    private String id;
    private String name;
    private String description;
    private String phone;
    private String url;
    private List<CustomField> companyFields;
    private String syncedStatusId;
    private Address address;
    private Integer wonDealsCount;
    private Double totalWonAmount;
    private Integer pendingDealsCount;
    private Double totalPendingAmount;
    private Integer contactsCount;
    private List<Contact> contacts;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Company setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return null; // TODO - call serializer!
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Company setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Company setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Company setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<CustomField> getCompanyFields() {
        return companyFields;
    }

    public Company setCompanyFields(List<CustomField> companyFields) {
        this.companyFields = companyFields;
        return this;
    }

    public String getSyncedStatusId() {
        return syncedStatusId;
    }

    public Company setSyncedStatusId(String syncedStatusId) {
        this.syncedStatusId = syncedStatusId;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Company setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Integer getWonDealsCount() {
        return wonDealsCount;
    }

    public Company setWonDealsCount(Integer wonDealsCount) {
        this.wonDealsCount = wonDealsCount;
        return this;
    }

    public Double getTotalWonAmount() {
        return totalWonAmount;
    }

    public Company setTotalWonAmount(Double totalWonAmount) {
        this.totalWonAmount = totalWonAmount;
        return this;
    }

    public Integer getPendingDealsCount() {
        return pendingDealsCount;
    }

    public Company setPendingDealsCount(Integer pendingDealsCount) {
        this.pendingDealsCount = pendingDealsCount;
        return this;
    }

    public Double getTotalPendingAmount() {
        return totalPendingAmount;
    }

    public Company setTotalPendingAmount(Double totalPendingAmount) {
        this.totalPendingAmount = totalPendingAmount;
        return this;
    }

    public Integer getContactsCount() {
        return contactsCount;
    }

    public Company setContactsCount(Integer contactsCount) {
        this.contactsCount = contactsCount;
        return this;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Company setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }
}
