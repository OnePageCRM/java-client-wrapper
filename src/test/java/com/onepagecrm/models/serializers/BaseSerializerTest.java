package com.onepagecrm.models.serializers;

import com.onepagecrm.BaseTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 07/12/2015.
 */
public class BaseSerializerTest extends BaseTest {

    private List<String> stringList;
    private String[] stringArray;
    private String expectedString;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");

        stringArray = new String[stringList.size()];
        stringArray[0] = "one";
        stringArray[1] = "two";
        stringArray[2] = "three";

        expectedString = "one__,__two__,__three";
    }

    public void testEncodeParams_NullMap() {
        String expected = "";
        String actual = BaseSerializer.encodeParams(null);

        assertEquals("Method mis-calculated query String based off of params",
                expected, actual);
    }

    public void testEncodeParams_Correct() {
        Map<String, Object> params = new HashMap<>();
        params.put("per_page", "10");
        params.put("page", "1");

        String expected = "per_page=10&page=1";
        String actual = BaseSerializer.encodeParams(params);

        assertEquals("Method mis-calculated query String based off of params",
                expected, actual);
    }

    public void testToCommaSeparatedString_FromList_Null() {
        List<String> strings = null;

        String expected = "";
        String actual = BaseSerializer.toCommaSeparatedString(strings);

        assertEquals("List of strings not converted to string correctly",
                expected, actual);
    }

    public void testToCommaSeparatedString_FromList_Correct() {
        String expected = expectedString;
        String actual = BaseSerializer.toCommaSeparatedString(stringList);

        assertEquals("List of strings not converted to string correctly",
                expected, actual);
    }

    public void testToCommaSeparatedString_FromArray_Null() {
        String[] array = null;

        String expected = "";
        String actual = BaseSerializer.toCommaSeparatedString(array);

        assertEquals("Array of strings not converted to string correctly",
                expected, actual);
    }

    public void testToCommaSeparatedString_FromArray_Empty() {
        String[] array = new String[0];

        String expected = "";
        String actual = BaseSerializer.toCommaSeparatedString(array);

        assertEquals("Array of strings not converted to string correctly",
                expected, actual);
    }

    public void testToCommaSeparatedString_FromArray_Correct() {
        String expected = expectedString;
        String actual = BaseSerializer.toCommaSeparatedString(stringArray);

        assertEquals("Array of strings not converted to string correctly",
                expected, actual);
    }

    public void testToListOfStrings_Null() {
        String joinedString = null;

        List<String> expected = new ArrayList<>();
        List<String> actual = BaseSerializer.toListOfStrings(joinedString);

        assertEquals("Strings not converted to list of strings correctly",
                expected, actual);
    }

    public void testToListOfStrings_Empty() {
        String joinedString = "";

        List<String> expected = new ArrayList<>();
        List<String> actual = BaseSerializer.toListOfStrings(joinedString);

        assertEquals("Strings not converted to list of strings correctly",
                expected, actual);
    }

    public void testToListOfStrings_Correct() {
        String joinedString = expectedString;

        List<String> expected = stringList;
        List<String> actual = BaseSerializer.toListOfStrings(joinedString);

        assertEquals("String not converted to list of strings correctly",
                expected, actual);
    }

    @SuppressWarnings({"MismatchedReadAndWriteOfArray", "ConstantConditions"})
    public void testToArrayOfStrings_Null() {
        String joinedString = null;

        String[] expected = {};
        String[] actual = BaseSerializer.toArrayOfStrings(joinedString);

        assertEquals("String not converted to array of strings correctly",
                expected.length, 0);
        assertEquals("String not converted to array of strings correctly",
                actual.length, 0);
    }

    public void testToArrayOfStrings_Correct() {
        String joinedString = expectedString;

        String[] expected = stringArray;
        String[] actual = BaseSerializer.toArrayOfStrings(joinedString);

        assertEquals("String not converted to array of strings correctly",
                expected.length, 3);
        assertEquals("String not converted to array of strings correctly",
                actual.length, 3);
        assertEquals("String not converted to array of strings correctly",
                expected[0], actual[0]);
        assertEquals("String not converted to array of strings correctly",
                expected[1], actual[1]);
        assertEquals("String not converted to array of strings correctly",
                expected[2], actual[2]);
    }

    public void testParseNumber_StringIn_DoubleOut() {
        String pi = "3.14";

        BigDecimal expected = new BigDecimal("3.14");
        Object actual = BaseSerializer.parseNumber(pi);

        assertTrue("Should not be null.", actual != null);
        assertTrue("Should be of type Double.", actual instanceof BigDecimal);
        assertEquals(expected, actual);
    }

    public void testParseNumber_StringIn_LongOut() {
        String luckyNumberThirteen = "13";

        Long expected = 13L;
        Object actual = BaseSerializer.parseNumber(luckyNumberThirteen);

        assertTrue("Should not be null.", actual != null);
        assertTrue("Should be of type Long.", actual instanceof Long);
        assertEquals(expected, actual);
    }
}
