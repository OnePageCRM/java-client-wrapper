package com.onepagecrm.models.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Cillian Myles on 15/02/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Convert2Diamond"})
public class MultipleContactsHelper {

    private static final String DELIMITER = ",";

    public static String toString(List<String> contactIds) {
        return contactIds == null || contactIds.isEmpty() ? "" :
                TextHelper.join(DELIMITER, contactIds);
    }

    public static String toString(Map<String, Object> filterParams) {
        return filterParams == null || filterParams.isEmpty() ? "" :
                TextHelper.join(DELIMITER, strip(filterParams.keySet()));
    }

    public static List<String> toList(String contactIds) {
        return TextHelper.isEmpty(contactIds) ? new ArrayList<String>() :
                Arrays.asList(contactIds.split("\\s*,\\s*"));
    }

    public static List<String> toList(Map<String, Object> filterParams) {
        return filterParams == null ? new ArrayList<String>() :
                toList(TextHelper.join(DELIMITER, strip(filterParams.keySet())));
    }

    public static Set<String> strip(Set<String> params) {
        if (params == null || params.isEmpty()) {
            return params;
        }
        params.remove("page");
        params.remove("per_page");
        return params;
    }
}
