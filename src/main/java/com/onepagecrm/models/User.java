package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.internal.PredefinedActionList;
import com.onepagecrm.models.serializers.BaseSerializer;
import com.onepagecrm.models.serializers.CompanyListSerializer;
import com.onepagecrm.models.serializers.ContactListSerializer;
import com.onepagecrm.models.serializers.DealListSerializer;
import com.onepagecrm.models.serializers.UserSerializer;
import com.onepagecrm.net.API;
import com.onepagecrm.net.ApiResource;
import com.onepagecrm.net.Response;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.Request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Cillian Myles on 08/01/2019.
 * Copyright (c) 2019 OnePageCRM. All rights reserved.
 */

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class User extends ApiResource implements Serializable {

    private static final long serialVersionUID = 1383622287570201668L;

    /*
     * Member variables.
     */

    public Account account;
    private String id;
    private String authKey;
    private String accountType;
    private String bccEmail;
    private String companyName;
    private String email;
    private String firstName;
    private String lastName;
    private String photoUrl;
    private String countryCode;
    private boolean newUser;
    private Integer allCount;
    private Integer streamCount;
    private Integer contactsCount;
    private List<String> accountRights;

    /* Auth-related API methods */

    public StartupData startup() throws OnePageException {
        return API.Auth.startup();
    }

    @Deprecated
    public static User googleLogin(String oauth2Code) throws OnePageException {
        return API.GoogleOld.login(oauth2Code);
    }

    @Deprecated
    public static User googleSignup(String oauth2Code) throws OnePageException {
        return API.GoogleOld.signup(oauth2Code);
    }

    public User bootstrap() throws OnePageException {
        return API.Auth.bootstrap();
    }

    /* Other API methods (TO BE MOVED OUT to API.Namespace eventually) */

    public ContactList actionStream() throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.queryDefault());
    }

    public ContactList actionStream(Paginator paginator) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.query(paginator));
    }

    public ContactList actionStream(Map<String, Object> params) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.fromParams(params));
    }

    public ContactList actionStream(Map<String, Object> params, Paginator paginator) throws OnePageException {
        String query = Query.fromMaps(params, Query.params(paginator));
        return getContacts(ACTION_STREAM_ENDPOINT, query);
    }

    public ContactList searchActionStream(String search) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.querySearch(search));
    }

    public ContactList searchActionStream(Paginator paginator, String search) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.querySearch(paginator, search));
    }

    public ContactList actionStreamByLetter(String letter) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.queryLetter(letter));
    }

    public ContactList actionStreamByLetter(Paginator paginator, String letter) throws OnePageException {
        return getContacts(ACTION_STREAM_ENDPOINT, Query.queryLetter(paginator, letter));
    }

    public ContactList contacts() throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.query(true));
    }

    public ContactList contacts(Paginator paginator) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.query(paginator, true));
    }

    public ContactList contacts(Map<String, Object> params) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.fromParams(params));
    }

    public ContactList contacts(Map<String, Object> params, Paginator paginator) throws OnePageException {
        String query = Query.fromMaps(params, Query.params(paginator));
        return getContacts(CONTACTS_ENDPOINT, query);
    }

    public ContactList searchContacts(String search) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.querySearch(search, true));
    }

    public ContactList searchContacts(Paginator paginator, String search) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.querySearch(paginator, search, true));
    }

    public ContactList contactsByCompany(String companyId) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.queryCompany(companyId));
    }

    public ContactList contactsByCompany(Paginator paginator, String companyId) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.queryCompany(paginator, companyId));
    }

    public ContactList contactsByLetter(String letter) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.queryLetter(letter, true));
    }

    public ContactList contactsByLetter(Paginator paginator, String letter) throws OnePageException {
        return getContacts(CONTACTS_ENDPOINT, Query.queryLetter(paginator, letter, true));
    }

    public ContactList teamStream(Map<String, Object> params) throws OnePageException {
        return getContacts(TEAM_STREAM_ENDPOINT, Query.fromParams(params));
    }

    public ContactList teamStream(Map<String, Object> params, Paginator paginator) throws OnePageException {
        String query = Query.fromMaps(params, Query.params(paginator));
        return getContacts(TEAM_STREAM_ENDPOINT, query);
    }

    public DealList pipeline() throws OnePageException {
        Request request = new GetRequest(DEALS_ENDPOINT, Query.query(true));
        Response response = request.send();
        return DealListSerializer.fromString(response.getResponseBody());
    }

    public DealList pipeline(Paginator paginator) throws OnePageException {
        Request request = new GetRequest(DEALS_ENDPOINT, Query.query(paginator, true));
        Response response = request.send();
        return DealListSerializer.fromString(response.getResponseBody());
    }

    public DealList pipeline(Map<String, Object> params) throws OnePageException {
        Request request = new GetRequest(DEALS_ENDPOINT, Query.fromParams(params));
        Response response = request.send();
        return DealListSerializer.fromString(response.getResponseBody());
    }

    public DealList pipeline(Map<String, Object> params, Paginator paginator) throws OnePageException {
        String query = Query.fromMaps(params, Query.params(paginator));
        Request request = new GetRequest(DEALS_ENDPOINT, query);
        Response response = request.send();
        return DealListSerializer.fromString(response.getResponseBody());
    }

    private ContactList getContacts(final String endpoint, String query) throws OnePageException {
        query += "&" + Contact.EXTRA_FIELDS;
        Request request = new GetRequest(endpoint, query);
        Response response = request.send();
        return ContactListSerializer.fromString(response.getResponseBody());
    }

    public CompanyList companies() throws OnePageException {
        return getCompanies(Query.queryDefault());
    }

    public CompanyList companies(Paginator paginator) throws OnePageException {
        return getCompanies(Query.query(paginator));
    }

    public CompanyList companies(Map<String, Object> params) throws OnePageException {
        return getCompanies(Query.fromParams(params));
    }

    public CompanyList companies(Map<String, Object> params, Paginator paginator) throws OnePageException {
        String query = Query.fromMaps(params, Query.params(paginator));
        return getCompanies(query);
    }

    private CompanyList getCompanies(String query) throws OnePageException {
        Request request = new GetRequest(COMPANIES_ENDPOINT, query);
        Response response = request.send();
        return CompanyListSerializer.fromString(response.getResponseBody());
    }

    public ActionList actions(Paginator paginator) throws OnePageException {
        return Action.list(this.id, paginator);
    }

    public ActionList actions() throws OnePageException {
        return Action.list(this.id);
    }

    public PredefinedActionList actionsPredefined(Paginator paginator) throws OnePageException {
        return Action.listPredefined(paginator);
    }

    public PredefinedActionList actionsPredefined() throws OnePageException {
        return Action.listPredefined();
    }

    /*
     * Utility methods.
     */

    public String getSimpleName() {
        if (lastName != null && !lastName.equals("")) {
            if (firstName != null && !firstName.equals("")) {
                return firstName + " " + lastName.substring(0, 1) + ".";
            } else {
                return lastName;
            }
        }
        return null;
    }

    public String getFullName() {
        if (lastName != null && !lastName.equals("")) {
            if (firstName != null && !firstName.equals("")) {
                return firstName + " " + lastName;
            } else {
                return lastName;
            }
        }
        return null;
    }

    public String getFullAlphaName() {
        if (lastName != null && !lastName.equals("")) {
            if (firstName != null && !firstName.equals("")) {
                return lastName + ", " + firstName;
            } else {
                return lastName;
            }
        }
        return null;
    }

    public boolean hasRight(String right) {
        return isAdmin() || isOwner() || accountRights != null && accountRights.contains(right);
    }

    public boolean isAdmin() {
        return accountRights != null && accountRights.contains(BaseSerializer.ACCOUNT_OWNER_TAG);
    }

    public boolean isOwner() {
        return accountRights != null && accountRights.contains(BaseSerializer.ADMIN_TAG);
    }

    /*
     * Object methods.
     */

    @Override
    public String toString() {
        return UserSerializer.toJsonObject(this);
    }

    @Override
    public boolean equals(Object object) {
        boolean idsEqual = super.equals(object);
        boolean authKeysEqual = false;
        if (object instanceof User) {
            User toCompare = (User) object;
            if (this.authKey == null && toCompare.authKey == null) {
                authKeysEqual = true;
            } else if (this.authKey != null && toCompare.authKey != null) {
                authKeysEqual = this.authKey.equals(toCompare.authKey);
            }
        }
        return idsEqual && authKeysEqual;
    }

    @Override
    public boolean isValid() {
        boolean idValid = super.isValid();
        boolean authKeyValid = this.authKey != null && !this.authKey.equals("");
        return idValid && authKeyValid;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthKey() {
        return authKey;
    }

    public User setAuthKey(String authKey) {
        this.authKey = authKey;
        return this;
    }

    public String getAccountType() {
        return accountType;
    }

    public User setAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public User setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public User setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public User setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public Account getAccount() {
        return account;
    }

    public User setAccount(Account account) {
        this.account = account;
        return this;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public User setAllCount(Integer allCount) {
        this.allCount = allCount;
        return this;
    }

    public Integer getStreamCount() {
        return streamCount;
    }

    public User setStreamCount(Integer streamCount) {
        this.streamCount = streamCount;
        return this;
    }

    public Integer getContactsCount() {
        return contactsCount;
    }

    public User setContactsCount(Integer contactsCount) {
        this.contactsCount = contactsCount;
        return this;
    }

    public List<String> getAccountRights() {
        return accountRights;
    }

    public User setAccountRights(List<String> accountRights) {
        this.accountRights = accountRights;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public User setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public User setNewUser(boolean newUser) {
        this.newUser = newUser;
        return this;
    }
}