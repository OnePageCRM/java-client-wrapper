package com.onepagecrm.models.serializers;

import com.onepagecrm.models.DealItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Anton S. on 04/08/2018
 */
public class DealItemSerializer extends BaseSerializer {

    public static DealItem fromJsonObject(JSONObject dataObject) {
        return new DealItem()
                .setId(dataObject.optString(ID_TAG))
                .setName(dataObject.optString(NAME_TAG))
                .setDescription(dataObject.optString(DESCRIPTION_TAG))
                .setCost(dataObject.optDouble(COST_TAG))
                .setPrice(dataObject.optDouble(PRICE_TAG))
                .setAmount(dataObject.optInt(AMOUNT_TAG))
                .setQuantity(dataObject.optInt(QUANTITY_TAG))
                .setDealId(dataObject.optString(DEAL_ID_TAG))
                .setPredefinedItemId(dataObject.optString(PREDEFINED_ITEM_ID_TAG))
                .setItemGroupId(dataObject.optString(ITEM_GROUP_ID_TAG))
                .setCreatedAt(dataObject.optString(CREATED_AT_TAG))
                .setModifiedAt(dataObject.optString(UPDATED_AT_TAG));
    }

    public static List<DealItem> fromJsonArray(JSONArray dealItemsArray) {
        List<DealItem> dealItems = new LinkedList<>();
        if (dealItemsArray == null) return dealItems;
        for (int i = 0; i < dealItemsArray.length(); ++i) {
            JSONObject itemObject = dealItemsArray.optJSONObject(i);
            DealItem item = fromJsonObject(itemObject);
            if (item != null) {
                dealItems.add(item);
            }
        }
        return dealItems;
    }

    public static JSONObject toJsonObject(DealItem item) {
        JSONObject itemObject = new JSONObject();
        if (item == null) return itemObject;
        addJsonStringValue(item.getId(), itemObject, ID_TAG);
        addJsonStringValue(item.getName(), itemObject, NAME_TAG);
        addJsonStringValue(item.getDescription(), itemObject, DESCRIPTION_TAG);
        addJsonDoubleValue(item.getCost(), itemObject, COST_TAG);
        addJsonDoubleValue(item.getPrice(), itemObject, PRICE_TAG);
        addJsonIntegerValue(item.getAmount(), itemObject, AMOUNT_TAG);
        addJsonIntegerValue(item.getQuantity(), itemObject, QUANTITY_TAG);
        addJsonStringValue(item.getDealId(), itemObject, DEAL_ID_TAG);
        addJsonStringValue(item.getItemGroupId(), itemObject, ITEM_GROUP_ID_TAG);
        addJsonStringValue(item.getPredefinedItemId(), itemObject, PREDEFINED_ITEM_ID_TAG);
        return itemObject;
    }

    public static String toJsonString(DealItem item) {
        return toJsonObject(item).toString();
    }

    public static JSONArray toJsonArray(List<DealItem> items) {
        JSONArray itemsArray = new JSONArray();
        if (items == null || items.isEmpty()) return itemsArray;
        for (DealItem item : items) {
            JSONObject itemObject = toJsonObject(item);
            itemsArray.put(itemObject);
        }
        return itemsArray;
    }

    public static String toJsonString(List<DealItem> items) {
        return toJsonArray(items).toString();
    }
}
