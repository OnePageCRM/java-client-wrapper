package com.onepagecrm.models.serializers.impl;

import com.onepagecrm.models.BaseResource;
import com.onepagecrm.models.serializers.BaseSerializer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 04/09/2017.
 */
@SuppressWarnings("WeakerAccess")
public abstract class SerializableResource<T extends BaseResource> extends BaseSerializer implements Serializable<T> {

    protected abstract T singleResource();

    protected abstract String singleTag();

    protected List<T> multipleResources() {
        return new ArrayList<>();
    }

    protected abstract String multipleTag();

    @Override
    public final T fromJsonObject(JSONObject resourceObject) {
        if (resourceObject == null) return singleResource();
        return fromJsonObjectImpl(resourceObject);
    }

    protected abstract T fromJsonObjectImpl(JSONObject resourceObject);

    @Override
    public final List<T> fromJsonArray(JSONArray resourceArray) {
        if (resourceArray == null) return multipleResources();
        return fromJsonArrayImpl(resourceArray);
    }

    protected List<T> fromJsonArrayImpl(JSONArray resourceArray) {
        List<T> list = multipleResources();
        for (int i = 0; i < resourceArray.length(); i++) {
            list.add(fromJsonObject(resourceArray.optJSONObject(i)));
        }
        return list;
    }

    @Override
    public final JSONObject toJsonObject(T resource) {
        if (resource == null) return EMPTY_JSON_OBJECT;
        return toJsonObjectImpl(resource);
    }

    protected abstract JSONObject toJsonObjectImpl(T resource);

    @Override
    public final JSONArray toJsonArray(List<T> resourceList) {
        if (resourceList == null || resourceList.isEmpty()) {
            return EMPTY_JSON_ARRAY;
        }
        return toJsonArrayImpl(resourceList);
    }

    protected JSONArray toJsonArrayImpl(List<T> resourceList) {
        JSONArray resourceArray = new JSONArray();
        for (T item : resourceList) {
            resourceArray.put(toJsonObject(item));
        }
        return resourceArray;
    }

    @Override
    public final JSONArray toJsonArray(List<T> resourceList, boolean includeObjectKey) {
        if (resourceList == null || resourceList.isEmpty()) {
            return EMPTY_JSON_ARRAY;
        }
        return toJsonArrayImpl(resourceList, includeObjectKey);
    }

    protected JSONArray toJsonArrayImpl(List<T> resourceList, boolean includeObjectKey) {
        JSONArray resourceArray = new JSONArray();
        for (T item : resourceList) {
            JSONObject outerObject = new JSONObject();
            JSONObject innerObject = toJsonObject(item);
            addJsonObject(innerObject, outerObject, singleTag());
            resourceArray.put(includeObjectKey ? outerObject : innerObject);
        }
        return resourceArray;
    }

    @Override
    public final String toJsonString(T resource) {
        return toJsonObject(resource).toString();
    }

    @Override
    public final String toJsonString(List<T> resourceList) {
        return toJsonArray(resourceList).toString();
    }

    @Override
    public final String toJsonString(List<T> resourceList, boolean includeObjectKey) {
        return toJsonArray(resourceList, includeObjectKey).toString();
    }
}
