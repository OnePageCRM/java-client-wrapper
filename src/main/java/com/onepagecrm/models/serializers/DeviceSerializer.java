package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.Device;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 17/05/2016.
 */
public class DeviceSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(DeviceSerializer.class.getSimpleName());

    public static Device fromString(String responseBody) throws OnePageException {
        String parsedResponse;
        OnePageException exception;
        try {
            parsedResponse = (String) BaseSerializer.fromString(responseBody);
            JSONObject responseObject = new JSONObject(parsedResponse);
            return fromJsonObject(responseObject.optJSONObject(FCM_DEVICE_TAG));

        } catch (ClassCastException e) {
            exception = (OnePageException) BaseSerializer.fromString(responseBody);
            throw exception;
        } catch (JSONException e) {
            LOG.severe("Error parsing JSON" + e);
            return new Device();
        }
    }

    public static Device fromJsonObject(JSONObject deviceObject) {
        return new Device()
                .setId(deviceObject.optString(ID_TAG))
                .setDeviceId(deviceObject.optString(DEVICE_ID_TAG))
                .setDeviceType(deviceObject.optString(DEVICE_TYPE_TAG))
                .setActionWithTime(deviceObject.optBoolean(ACTION_WITH_TIME_TAG))
                .setSubscribedAt(InstantSerializer.getInstance().parse(deviceObject.optString(SUBSCRIBED_AT_TAG)));
    }

    public static List<Device> fromJsonArray(JSONArray devicesArray) {
        List<Device> devices = new LinkedList<>();
        if (devicesArray != null) {
            for (int i = 0; i < devicesArray.length(); ++i) {
                devices.add(fromJsonObject(devicesArray.optJSONObject(i).optJSONObject(FCM_DEVICE_TAG)));
            }
        }
        return devices;
    }

    public static String toJsonObject(Device device) {
        JSONObject deviceObject = new JSONObject();
        if (device != null) {
            addJsonStringValue(device.getId(), deviceObject, ID_TAG);
            addJsonStringValue(device.getDeviceId(), deviceObject, DEVICE_ID_TAG);
            addJsonStringValue(device.getDeviceType(), deviceObject, DEVICE_TYPE_TAG);
            addJsonBooleanValue(device.getActionWithTime(), deviceObject, ACTION_WITH_TIME_TAG);
            addJsonStringValue(
                    InstantSerializer.getInstance().format(device.getSubscribedAt()),
                    deviceObject,
                    SUBSCRIBED_AT_TAG
            );
        }
        return deviceObject.toString();
    }

    public static String toJsonArray(List<Device> devices) {
        JSONArray devicesArray = new JSONArray();
        if (devices != null && !devices.isEmpty()) {
            for (Device device : devices) {
                try {
                    devicesArray.put(new JSONObject(toJsonObject(device)));
                } catch (JSONException e) {
                    LOG.severe("Error creating JSONArray out of list of Devices.");
                    LOG.severe(e.toString());
                }
            }
        }
        return devicesArray.toString();
    }
}
