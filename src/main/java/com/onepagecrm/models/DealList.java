package com.onepagecrm.models;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Paginator;
import com.onepagecrm.models.serializers.DealSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Cillian Myles (cillian@onepagecrm.com) on 11/24/15.
 */
public class DealList extends ResourceList<Deal> implements Serializable {

    @Override
    public DealList nextPage(Map<String, Object> params) throws OnePageException {
        this.paginator.getNextPageNo();
        return Account.currentUser.pipeline(params, paginator);
    }

    @Override
    public DealList refresh(Map<String, Object> params) throws OnePageException {
        DealList list = Account.currentUser.pipeline(params, new Paginator());
        this.setList(list);
        return this;
    }

    public DealList() {
        super(null);
    }

    public DealList(List<Deal> deals) {
        super(deals);
    }

    @Override
    public DealList subList(int start, int end) {
        return new DealList(super.subList(start, end));
    }

    public String toString() {
        return DealSerializer.toJsonArray(this);
    }
}
