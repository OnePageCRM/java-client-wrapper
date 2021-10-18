package com.onepagecrm.net;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.User;

public class OnePageAuthDataTest extends BaseTest {

    private User currentUser;

    /**
     * Method is used to set up a fabricated User object.
     * <p/>
     * The fabricated User's details are my real details. These exact details
     * (x-uid, x-ts, x-auth) and particular requests have been used before and
     * worked. This is how the method Authentication.calculateSignature() is
     * verified, against previously correct data.
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Set up fabricated User.
        currentUser = new User()
                .setId("559cd1866f6e656707000001")
                .setAuthKey("xbigujk/IWwGwz0ojZAXnl/jOiQ3yKnBAFOWM6F9c88=")
                .setFirstName("Cillian")
                .setLastName("Myles");
    }

    /**
     * Should be no Authentication Signature generated for authenticated request.
     */
    public void testCalculateSignature_Authenticate() {
        OnePageAuthData authSig = new OnePageAuthData("POST", null, "");
        assertNull("Authentication signature incorrectly constructed", authSig.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for
     * Action Stream.
     */
    public void testCalculateSignature_ActionStream() {
        String actionStreamUrl = "https://app.onepagecrm.com/api/v3/action_stream.json";

        OnePageAuthData actionStreamAuthSig = new OnePageAuthData(currentUser, 1435850641, "GET",
                actionStreamUrl, "");

        assertEquals("35f66dd6cb0d5246541590a222647c644e6925f0874c0e45688708fe2cf2e900",
                actionStreamAuthSig.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for Calls.
     */
    public void testCalculateSignature_AddCall() {
        String postCallUrl = "https://app.onepagecrm.com/api/v3/calls.json?contact_id=55804f6b1787fa72b400002e";
        String postCallBody = "text=&call_result=interested&contact_id=55804f6b1787fa72b400002e";

        OnePageAuthData callsAuthSig = new OnePageAuthData(currentUser, 1435851404, "POST",
                postCallUrl, postCallBody);

        assertEquals("5f5f91e6954eb5e08aad92ca4de0ad16284e0efa51bf76cf062578e19e7dbf67",
                callsAuthSig.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for Calls.
     */
    public void testCalculateSignature_AddCall_JsonBody() {
        String postCallUrl = "https://app.onepagecrm.com/api/v3/calls.json?contact_id=55d5d973e57c393e8a00009c";
        String postCallBody = "{\"text\":\"JAVA\",\"call_result\":\"interested\"}";

        OnePageAuthData callsAuthSig = new OnePageAuthData(currentUser, 1441288055, "POST",
                postCallUrl, postCallBody);

        assertEquals("bab25ae744e70b0e51b670e40954a3403560163ecfcf07326d30bba897c734c0",
                callsAuthSig.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for Calls.
     */
    public void testCalculateSignature_AddContact() {
        String postCallUrl = "https://app.onepagecrm.com/api/v3/contacts.json";
        String postCallBody = "{\"first_name\":\"Cillian\",\"company_name\":\"Myles Inc.\",\"last_name\":\"Myles\"}";

        OnePageAuthData callsAuthSig = new OnePageAuthData(currentUser, 1441288055, "POST",
                postCallUrl, postCallBody);

        assertEquals("c953d8c0f8b2f02eb8f7b1f6c2c6d12e26e0eb2f9eb73c7f9f5b5833f8ca0b3c",
                callsAuthSig.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for deleting FCM devices.
     */
    public void testCalculateSignature_DeleteFcmDevice() {
        final User user = new User().setId("57a099629007ba29115829b2")
                .setAuthKey("NVMsF8uNkFihbJRaklVSTr8RZoXbt8/s2biQssgwBoI=");

        String endpoint = "https://app.onepagecrm.com/api/v3/firebase.json";
        String body = "{\"id\":\"584a795f9007ba7c3f6bf25f\"}";

        OnePageAuthData authData = new OnePageAuthData(user, 1481280581, "DELETE",
                endpoint, body);

        assertEquals("8e2366a8410fcb74dd4ae06dd3ae7ce05498d66eb5c326e02510f0dc8bab1b32",
                authData.getSignature());
    }
}
