package com.onepagecrm.models.fabricators;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.DealList;
import com.onepagecrm.models.serializers.DateSerializer;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 16/02/2016.
 */
public class DealFabricatorTest extends BaseTest {

    public void testRegular_correctValues() {
        Deal deal = DealFabricator.regular();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("56fa813000d4afc98c000001", deal.getId());
        assertEquals(200400d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Deal note for Falcon 9", deal.getText());
        assertEquals("56fa805fefa9dd4d3502ab25", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-30"), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:20:48Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:20:48Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 1);
        assertEquals("Falcon 9", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() != null && deal.getStage() == 75);
        assertEquals("pending", deal.getStatus());
        assertEquals(200400d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && !deal.getHasRelatedNotes());
        assertEquals(null, deal.getCloseDate());
    }

    public void testMultiMonth_correctValues() {
        Deal deal = DealFabricator.multiMonth();
        assertTrue("Deal must be valid.", deal.isValid());
        assertEquals("56fa80f200d4afa0360004b0", deal.getId());
        assertEquals(2000d, deal.getAmount());
        assertEquals("Cillian M.", deal.getAuthor());
        assertEquals("Deal note for Big deal. This is the biggest deal note that you have ever seen in this app. " +
                "I am even going to add some more text to this note right here. Let's see if we can read this or " +
                "if it ellipsizes. Now it must surely ellipsize. Please please please.", deal.getText());
        assertEquals("56fa805fefa9dd4d3502ab2b", deal.getContactId());
        assertEquals(DateSerializer.fromFormattedString("2016-08-02"), deal.getDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29"), deal.getExpectedCloseDate());
        assertEquals(DateSerializer.fromFormattedString("2016-03-29T13:19:46Z"), deal.getCreatedAt());
        assertEquals(DateSerializer.fromFormattedString("2016-08-03T09:34:04Z"), deal.getModifiedAt());
        assertTrue(deal.getMonths() != null && deal.getMonths() == 12);
        assertEquals("Big deal", deal.getName());
        assertEquals("556cb8b61787fa02e000047e", deal.getOwnerId());
        assertTrue(deal.getStage() != null && deal.getStage() == 25);
        assertEquals("pending", deal.getStatus());
        assertEquals(24000d, deal.getTotalAmount());
        assertTrue(deal.getHasRelatedNotes() != null && deal.getHasRelatedNotes());
        assertEquals(null, deal.getCloseDate());
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    public void testList_allDealsValid() {
        DealList deals = DealFabricator.list();
        assertEquals("Should be 14 deals", deals.size(), 14);

        for (int i = 0; i < deals.size(); i++) {
            Deal deal = deals.get(i);
            assertTrue("Deal must be valid.", deal.isValid());
        }
    }
}
