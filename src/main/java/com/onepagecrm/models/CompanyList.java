package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.CompanyListSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/12/2016.
 */
public class CompanyList extends ResourceList<Company> implements Serializable {

    private static final Logger LOG = Logger.getLogger(CompanyList.class.getName());

    @Override
    public CompanyList nextPage(Map<String, Object> params) throws OnePageException {
        this.paginator.getNextPageNo();
        return Account.currentUser.companies(params, paginator);
    }

    @Override
    public CompanyList refresh(Map<String, Object> params) throws OnePageException {
        CompanyList list = Account.currentUser.companies(params, (paginator = new Paginator()));
        this.setList(list);
        return this;
    }

    public CompanyList(List<Company> companies) {
        super(companies);
    }

    public CompanyList() {
        this(null);
    }

    @Override
    public CompanyList addNextPage(List<Company> companies) {
        super.addNextPage(companies);
        return this;
    }

    public CompanyList addNextPage(CompanyList companies) {
        if (companies != null && !companies.isEmpty()) {
            List<Company> contacts = companies.getList();
            addNextPage(contacts);
        }
        return this;
    }

    public String toString() {
        return CompanyListSerializer.toJsonArray(this);
    }
}
