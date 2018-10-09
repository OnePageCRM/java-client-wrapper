package com.onepagecrm.models;

import com.onepagecrm.exceptions.InvalidListingTypeException;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.helpers.MultipleContactsHelper;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.ContactListSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 15/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ContactList extends ResourceList<Contact> implements Serializable {

    private static final Logger LOG = Logger.getLogger(ContactList.class.getName());

    private static final long serialVersionUID = 8185938052776557364L;

    public static final int AS_LISTING = 1219;
    public static final int AZ_LISTING = 8662;
    public static final int AS_TEAM_LISTING = 2986;
    public static final int AS_MULTIPLE_LISTING = 6252;
    public static final int AZ_MULTIPLE_LISTING = 6253;
    public static final int MULTIPLE_CONTACTS_BY_IDS_LISTING = 6254;

    private int type;

    @Override
    public ContactList nextPage(Map<String, Object> params) throws OnePageException {
        this.paginator.getNextPageNo();
        switch (type) {
            case AS_LISTING:
            case AS_MULTIPLE_LISTING: {
                return Account.loggedInUser.actionStream(params, paginator);
            }
            case AZ_LISTING:
            case AZ_MULTIPLE_LISTING: {
                return Account.loggedInUser.contacts(params, paginator);
            }
            case AS_TEAM_LISTING: {
                return Account.loggedInUser.teamStream(params, paginator);
            }
            default: {
                throw new InvalidListingTypeException("Not a supported contact listing type.");
            }
        }
    }

    @Override
    public ContactList refresh(Map<String, Object> params) throws OnePageException {
        ContactList list = new ContactList();
        switch (type) {
            case AS_LISTING:
            case AS_MULTIPLE_LISTING: {
                list = Account.loggedInUser.actionStream(params, (paginator = new Paginator()));
                break;
            }
            case AZ_LISTING:
            case AZ_MULTIPLE_LISTING: {
                list = Account.loggedInUser.contacts(params, (paginator = new Paginator()));
                break;
            }
            case AS_TEAM_LISTING: {
                list = Account.loggedInUser.teamStream(params, (paginator = new Paginator()));
                break;
            }
            case MULTIPLE_CONTACTS_BY_IDS_LISTING: {
                list = Contact.byIds(MultipleContactsHelper.toString(params));
                break;
            }
        }
        this.setList(list);
        return this;
    }

    public ContactList(List<Contact> contacts, int type) {
        super(contacts);
        this.type = type;
    }

    public ContactList(List<Contact> contacts) {
        super(contacts);
    }

    public ContactList(int type) {
        this(null, type);
    }

    public ContactList() {
        this(null);
    }

    public ContactList setType(int type) {
        this.type = type;
        return this;
    }

    public int getType() {
        return type;
    }

    @Override
    public ContactList subList(int start, int end) {
        return new ContactList(super.subList(start, end));
    }

    @Override
    public ContactList addNextPage(List<Contact> contacts) {
        super.addNextPage(contacts);
        return this;
    }

    public ContactList addNextPage(ContactList contactsList) {
        if (contactsList != null && !contactsList.isEmpty()) {
            List<Contact> contacts = contactsList.getList();
            addNextPage(contacts);
        }
        return this;
    }

    public String toString() {
        return ContactListSerializer.toJsonObject(this);
    }

    /**
     * Determines {@link Contact}s which are phone-able i.e. have at least 1 {@link Phone} number.
     *
     * @return - List of Contacts which have at least 1 {@link Phone} number.
     */
    public ContactList getPhoneableContacts() {
        List<Contact> phoneableContacts = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            int counter = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getPhones() != null && !list.get(i).getPhones().isEmpty()) {
                    list.get(i).setIntId(counter + 1);
                    phoneableContacts.add(list.get(i));
                    counter++;
                }
            }
        }
        return new ContactList(phoneableContacts);
    }

    /**
     * Determines {@link Contact}s which STARRED contacts are phone-able i.e. have at least 1 {@link Phone} number.
     *
     * @return - List of Contacts which are STARRED and have at least 1 {@link Phone} number.
     */
    public ContactList getStarredPhoneableContacts() {
        List<Contact> phoneableContacts = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            int counter = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isStarred()) {
                    if (list.get(i).getPhones() != null && !list.get(i).getPhones().isEmpty()) {
                        list.get(i).setIntId(counter + 1);
                        phoneableContacts.add(list.get(i));
                        counter++;
                    }
                }
            }
        }
        return new ContactList(phoneableContacts);
    }

    /**
     * Checks if a {@link Phone} number exists in action stream.
     *
     * @param incomingNumber - {@link Phone} number to be matched.
     * @return - The {@link Contact} object whose number matches, if it exists.
     */
    public Contact inActionStream(String incomingNumber) {
        for (int i = 0; i < list.size(); i++) {
            List<Phone> phones = list.get(i).getPhones();
            if (phones != null && !phones.isEmpty()) {
                for (int j = 0; j < list.get(i).getPhones().size(); j++) {
                    if (incomingNumber.equals(list.get(i).getPhones().get(j).getValue())) {
                        return list.get(i);
                    }
                }
            }
        }
        return new Contact();
    }

    /**
     * Simply gets the next {@link Contact} in the list.
     * <p/>
     * If at the end, jumps back to the start.
     *
     * @param currentPosition - Current position in the list.
     * @return - {@link Contact} at the next position in the list.
     */
    public Contact getNextContact(int currentPosition) {
        return getNext(currentPosition);
    }

    /**
     * Simply gets the previous {@link Contact} in the list.
     * <p/>
     * If at the start, jumps back to the end.
     *
     * @param currentPosition - Current position in list.
     * @return - {@link Contact} in the previous position.
     */
    public Contact getPreviousContact(int currentPosition) {
        return getPrevious(currentPosition);
    }

    /**
     * Get the list index of the given {@link Contact}.
     *
     * @param contact - {@link Contact} whose index is being sought.
     * @return - Index of the given item, -1 if not found in list.
     */
    public int getArrayPosition(Contact contact) {
        return getPosition(contact);
    }
}