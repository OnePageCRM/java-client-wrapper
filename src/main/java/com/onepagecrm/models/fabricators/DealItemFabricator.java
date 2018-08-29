package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.DealItem;

/**
 * Created by Anton S. on 28/08/2018
 */
public class DealItemFabricator {

    public static DealItem single() {
        return new DealItem()
                .setName("iPhone")
                .setDescription("Description")
                .setUpdatedAt("123")
                .setCreatedAt("456")
                .setItemGroupId("j2-9-21j2j3j;d")
                .setPredefinedItemId("predef item id")
                .setDealId("-2-92jj;dj;wdajdaw")
                .setAmount(10)
                .setPrice(15000)
                .setId("5947a6749007ba40b8ecbb0djp");
    }
}
