package com.onepagecrm.net.request;

import com.onepagecrm.models.serializers.BaseSerializer;

/**
 * Created by Cillian Myles on 01/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class AppServersRequest extends Request {

    public AppServersRequest() {
        setType();
        setEndpointUrl(BaseSerializer.ENDPOINTS_TAG);
    }

    @Override
    public void setType() {
        this.type = Type.GET;
    }
}
