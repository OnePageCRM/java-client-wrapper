package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.DealItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton S. on 28/08/2018
 */
public class DealItemFabricator {

    public static DealItem single() {
        return new DealItem()
                .setName("iPhone")
                .setDescription("Description")
                .setModifiedAt("123")
                .setCreatedAt("456")
                .setItemGroupId("j2-9-21j2j3j;d")
                .setPredefinedItemId("predef item id")
                .setDealId("-2-92jj;dj;wdajdaw")
                .setAmount(10.0)
                .setPrice(15000.0)
                .setId("5947a6749007ba40b8ecbb0djp");
    }

    public static List<DealItem> list() {
        List<DealItem> list = new ArrayList<>();
        list.add(new DealItem()
                .setName("iPhone")
                .setDescription("Description")
                .setModifiedAt("123")
                .setCreatedAt("456")
                .setItemGroupId("j2-9-21j2j3j;d")
                .setPredefinedItemId("predef item id")
                .setDealId("-2-92jj;dj;wdajdaw")
                .setAmount(10.0)
                .setPrice(15000.0)
                .setId("5947a6749007ba40b8ecbb0djp"));

        list.add(new DealItem()
                .setName("Samsung S8")
                .setDescription("Description 2")
                .setModifiedAt("8654")
                .setCreatedAt("4535676")
                .setItemGroupId("item grp iddd")
                .setPredefinedItemId("predef item id 2")
                .setDealId("fsesesepiqijwdp")
                .setAmount(1130.0)
                .setPrice(1200.0)
                .setId("5947a6744411oooocbb0djp"));

        return list;
    }
}
