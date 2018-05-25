package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.Deal;
import com.onepagecrm.models.Note;
import com.onepagecrm.models.serializers.InstantSerializer;
import com.onepagecrm.models.serializers.LocalDateSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 29/08/2016.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class NoteFabricator extends BaseFabricator {

    public static Note single() {
        return new Note()
                .setId("57446f339007ba70dc20c13e")
                .setAuthor("Cillian M.")
                .setText("I rang Java to confirm his order. He confirmed an order for 16 solar panels for his store " +
                        "in Burlington. He amended his order for the store in Salem from 12 to 24 due to an " +
                        "unprecedented demand for eco products.")
                .setContactId("56fa81eb9007ba07fc000080")
                .setDate(LocalDateSerializer.getInstance().parse("2016-05-24"))
                .setLinkedDealId("")
                .setCreatedAt(InstantSerializer.getInstance().parse("2016-05-24T15:11:47Z"))
                .setModifiedAt(InstantSerializer.getInstance().parse("2016-05-24T15:11:47Z"));
    }

    public static Note linked() {
        return linked(DealFabricator.single());
    }

    public static Note linked(Deal linkedDeal) {
        return new Note()
                .setId("571e036b9007ba4566062065")
                .setAuthor("Cillian M.")
                .setText(linkedDeal.getText() + "... NOTE")
                .setContactId(linkedDeal.getContactId())
                .setDate(linkedDeal.getExpectedCloseDate())
                .setLinkedDealId(linkedDeal.getId())
                .setCreatedAt(linkedDeal.getCreatedAt())
                .setModifiedAt(linkedDeal.getModifiedAt());
    }

    public static List<Note> list() {
        List<Note> notes = new ArrayList<>();
        notes.add(single());
        notes.add(linked());
        notes.add(new Note()
                .setId("57446f5d9007ba70dc20c142")
                .setAuthor("Cillian M.")
                .setText("Just got our first order from Java!! Details of the order to be confirmed. Reason we " +
                        "got it.. cause we were cheaper than the rest!!! I added in this to see if it gets " +
                        "updated and wrapped correctly.")
                .setContactId("56fa81eb9007ba07fc000080")
                .setDate(LocalDateSerializer.getInstance().parse("2016-05-24"))
                .setLinkedDealId("")
                .setCreatedAt(InstantSerializer.getInstance().parse("2016-05-24T15:12:29Z"))
                .setModifiedAt(InstantSerializer.getInstance().parse("2016-05-24T15:12:29Z"))
        );
        return notes;
    }
}
