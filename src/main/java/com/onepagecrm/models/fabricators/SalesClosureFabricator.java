package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.internal.SalesCycleClosure;
import com.onepagecrm.models.serializers.InstantSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 29/08/2016.
 */
@SuppressWarnings("unused")
public class SalesClosureFabricator extends BaseFabricator {

    public static SalesCycleClosure single() {
        return new SalesCycleClosure()
                .setUserId("559cd1866f6e656707000001")
                .setComment("My closing comment")
                .setContactId("56fa81ea9007ba07fc00003e")
                .setClosedAt(InstantSerializer.getInstance().ofSeconds(1462261876L));
    }

    public static List<SalesCycleClosure> list() {
        List<SalesCycleClosure> calls = new ArrayList<>();
        calls.add(single());
        calls.add(new SalesCycleClosure()
                .setUserId("561b937f9007ba4cc200004e")
                .setComment("John's comment")
                .setContactId("56fa81ea9007ba07fc00003e")
                .setClosedAt(InstantSerializer.getInstance().ofSeconds(1462261859L))
        );
        return calls;
    }
}
