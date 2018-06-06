package com.onepagecrm.models.serializers;

import com.onepagecrm.models.Note;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Anton on 03/05/2018
 */

public class NoteSerializerTest {

    private static final String HARDCODED_ID = "HARDCODED_ID";
    private static final String HARDCODED_AUTHOR = "HARDCODED_AUTHOR";
    private static final String HARDCODED_TEXT = "HARDCODED_TEXT";
    private static final String HARDCODED_CONTACT_ID = "HARDCODED_CONTACT_ID";
    private static final LocalDate HARDCODED_DATE = LocalDate.parse("2018-06-06");
    private static final String HARDCODED_LINKED_ID = "HARDCODED_LINKED_ID";
    private static final Instant HARDCODED_MODIFIED_DATE = Instant.parse("2018-06-06T14:29:00Z");
    private static final Instant HARDCODED_CREATED_DATE = Instant.parse("2018-06-06T14:29:00Z");
    private static final String[] HARDCODED_USER_IDS = {"U_ID_1", "U_ID_2", "U_ID_3"};

    @Test
    public void serialize_test() {
        Note note = new Note()
                .setId(HARDCODED_ID)
                .setAuthor(HARDCODED_AUTHOR)
                .setText(HARDCODED_TEXT)
                .setContactId(HARDCODED_CONTACT_ID)
                .setDate(HARDCODED_DATE)
                .setLinkedDealId(HARDCODED_LINKED_ID)
                .setUserIdsToNotify(Arrays.asList(HARDCODED_USER_IDS))
                .setModifiedAt(HARDCODED_MODIFIED_DATE)
                .setCreatedAt(HARDCODED_CREATED_DATE);

        String string = NoteSerializer.toJsonObject(note);

        try {
            JSONObject json = new JSONObject(string);
            Note parsedNote = NoteSerializer.fromJsonObject(json);

            assertEquals(HARDCODED_ID, parsedNote.getId());
            assertEquals(HARDCODED_AUTHOR, parsedNote.getAuthor());
            assertEquals(HARDCODED_TEXT, parsedNote.getText());
            assertEquals(HARDCODED_CONTACT_ID, parsedNote.getContactId());
            assertEquals(HARDCODED_DATE, parsedNote.getDate());
            assertEquals(HARDCODED_LINKED_ID, parsedNote.getLinkedDealId());

            List<String> parsedUserIds = parsedNote.getUserIdsToNotify();
            List<String> originalUserIds = Arrays.asList(HARDCODED_USER_IDS);
            assertTrue(parsedUserIds.containsAll(originalUserIds) && originalUserIds.containsAll(parsedUserIds));

            assertEquals(HARDCODED_MODIFIED_DATE, parsedNote.getModifiedAt());
            assertEquals(HARDCODED_CREATED_DATE, parsedNote.getCreatedAt());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
