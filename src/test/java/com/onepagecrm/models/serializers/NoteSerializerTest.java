package com.onepagecrm.models.serializers;

import com.onepagecrm.BaseTest;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Note;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 03/05/2018
 */

public class NoteSerializerTest extends BaseTest {

    public static final String HARDCODED_AUTHOR = "AUTHORRRR";
    public static final String HARDCODED_CONTACT_ID = "Contact id id id id id";
    public static final Date HARDCODED_DATE = new Date(12345);
    public static final Date HARDCODED_MODIFY_DATE = new Date(2223);
    public static final Date HARDCODED_CREATE_DATE = new Date(25164);
    public static final String[] HARDCODED_USER_IDS = { "123", "adopjiawpdioajw", "-e9128uiaiowa" };
    public static final String HARDCODED_ID = "dw2130121j20e";
    public static final String HARDCODED_LINKED_ID = "ftgyhujimdkoalpsfghog";

    @Test
    public void serialize_test() {
        Note note = new Note()
                .setAuthor(HARDCODED_AUTHOR)
                .setDate(HARDCODED_DATE)
                .setUserIdsToNotify(Arrays.asList(HARDCODED_USER_IDS))
                .setId(HARDCODED_ID)
                .setContactId(HARDCODED_CONTACT_ID)
                .setLinkedDealId(HARDCODED_LINKED_ID)
                .setModifiedAt(HARDCODED_MODIFY_DATE)
                .setCreatedAt(HARDCODED_CREATE_DATE);

        String json = NoteSerializer.toJsonObject(note);

        try {
            Note parsedNote = NoteSerializer.fromString(json);

            assertEquals(HARDCODED_AUTHOR, parsedNote.getAuthor());
            assertEquals(HARDCODED_CONTACT_ID, parsedNote.getContactId());
            assertEquals(HARDCODED_DATE, parsedNote.getDate());
            assertEquals(HARDCODED_MODIFY_DATE, parsedNote.getModifiedAt());
            assertEquals(HARDCODED_CREATE_DATE, parsedNote.getCreatedAt());
            assertEquals(HARDCODED_ID, parsedNote.getId());

            List<String> parsedUserIds = parsedNote.getUserIdsToNotify();
            List<String> originalUserIds = Arrays.asList(HARDCODED_USER_IDS);

            assertTrue( parsedUserIds.containsAll(originalUserIds) && originalUserIds.containsAll(parsedUserIds));
        } catch (OnePageException e) {
            e.printStackTrace();
        }
    }
}
