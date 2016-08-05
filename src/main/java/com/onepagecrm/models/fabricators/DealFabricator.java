package com.onepagecrm.models.fabricators;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.internal.Utilities;
import com.onepagecrm.models.serializers.DealListSerializer;

import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 16/02/2016.
 */
public class DealFabricator extends BaseFabricator {

    private static final Logger LOG = Logger.getLogger(DealFabricator.class.getName());

    public static Deal single() {
        return regular();
    }

    public static Deal regular() {
        return list().get(1);
    }

    public static Deal multiMonth() {
        return list().get(0);
    }

    public static DealList list() {
        // Create a list of Deals.
        DealList deals = new DealList();
        String path = OnePageCRM.ASSET_PATH + "deals.json";
        String response = Utilities.getResourceContents(path);
        if (response != null) {
            try {
                deals = DealListSerializer.fromString(response);
            } catch (OnePageException e) {
                LOG.severe("Problem creating user object from JSON file.");
                LOG.severe(e.toString());
            }
        }
        return deals;
    }
}
