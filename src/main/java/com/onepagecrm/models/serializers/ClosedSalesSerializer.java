package com.onepagecrm.models.serializers;

import com.onepagecrm.models.internal.SalesCycleClosure;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/06/2016.
 */
@SuppressWarnings("WeakerAccess")
public class ClosedSalesSerializer extends BaseSerializer {

    public static SalesCycleClosure fromJsonObject(JSONObject closureObject, String contactId) {
        return new SalesCycleClosure()
                .setUserId(closureObject.optString(USER_ID_TAG))
                .setClosedAt(InstantSerializer.getInstance().ofSeconds(closureObject.optLong(CLOSED_AT_TAG)))
                .setComment((closureObject.isNull(COMMENT_TAG) ? null : closureObject.optString(COMMENT_TAG)))
                .setContactId(contactId);
    }

    public static List<SalesCycleClosure> fromJsonArray(JSONArray closureArray, String contactId) {
        List<SalesCycleClosure> closures = new LinkedList<>();
        if (closureArray == null) return closures;
        for (int i = 0; i < closureArray.length(); i++) {
            JSONObject closureObject = closureArray.optJSONObject(i);
            SalesCycleClosure closure = fromJsonObject(closureObject, contactId);
            closures.add(closure);
        }
        return closures;
    }

    public static Map<String, SalesCycleClosure> mapFromJsonArray(JSONArray closureArray, String contactId) {
        Map<String, SalesCycleClosure> closures = new HashMap<>();
        if (closureArray == null) return closures;
        for (int i = 0; i < closureArray.length(); i++) {
            JSONObject closureObject = closureArray.optJSONObject(i);
            SalesCycleClosure closure = fromJsonObject(closureObject, contactId);
            closures.put(closure.getUserId(), closure);
        }
        return closures;
    }
}
