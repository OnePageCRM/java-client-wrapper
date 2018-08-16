package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealItem;
import com.onepagecrm.models.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Anton S. on 04/08/2018
 */
public class DealItemSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(DealSerializer.class.getName());

    public static DealItem fromJsonObject(JSONObject dataObject) {
        DealItem dealItem = new DealItem();

        dealItem.setId(dataObject.optString(ID_TAG))
                .setName(dataObject.optString(NAME_TAG))
                .setDescription(dataObject.optString(DESCRIPTION_TAG))
                .setCost(dataObject.optInt(COST_TAG))
                .setPrice(dataObject.optInt(PRICE_TAG))
                .setAmount(dataObject.optInt(AMOUNT_TAG))
                .setQuantity(dataObject.optString(QUANTITY_TAG))
                .setDealId(dataObject.optString(DEAL_ID_TAG))
                .setPredefinedItemId(dataObject.optString(PREDEFINED_ITEM_ID_TAG))
                .setItemGroupId(dataObject.optString(ITEM_GROUP_ID_TAG))
                .setCreatedAt(dataObject.optString(CREATED_AT_TAG))
                .setUpdatedAt(dataObject.optString(UPDATED_AT_TAG));

        return dealItem;
    }

    public static List<DealItem> fromJsonArray(JSONArray dealItemsArray) {
        List<DealItem> dealItems = new LinkedList<>();
        if (dealItemsArray == null) return dealItems;
        for (int i = 0; i < dealItemsArray.length(); ++i) {
            JSONObject noteObject = dealItemsArray.optJSONObject(i);
            DealItem note = fromJsonObject(noteObject);
            if (note != null) {
                dealItems.add(note);
            }
        }
        return dealItems;
    }
}
