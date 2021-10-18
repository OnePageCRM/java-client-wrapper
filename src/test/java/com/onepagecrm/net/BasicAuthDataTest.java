package com.onepagecrm.net;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.User;

public class BasicAuthDataTest extends BaseTest {

    private User currentUser;

    /**
     * Method is used to set up a fabricated User object.
     * <p/>
     * The fabricated User's details are my real details. These exact details
     * (Authorization) and particular requests have been used before and
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
        BasicAuthData authSig = new BasicAuthData(null, null);
        assertEquals("Authentication signature incorrectly constructed", authSig.getSignature(), "Basic ");
    }

    /**
     * Verify Authentication Signature generated matches correct answer for HTTP basic auth method.
     */
    public void testCalculateSignature_ByUser() {
        BasicAuthData basicAuthData = new BasicAuthData(currentUser);

        assertEquals("Basic NTU5Y2QxODY2ZjZlNjU2NzA3MDAwMDAxOnhiaWd1amsvSVd3R3d6MG9qWkFYbmwvak9pUTN5S25CQUZPV002RjljODg9",
                basicAuthData.getSignature());
    }

    /**
     * Verify Authentication Signature generated matches correct answer for HTTP basic auth method.
     */
    public void testCalculateSignature_ByUserIdAndApiKey() {
        BasicAuthData basicAuthData = new BasicAuthData(currentUser.getId(), currentUser.getAuthKey());

        assertEquals("Basic NTU5Y2QxODY2ZjZlNjU2NzA3MDAwMDAxOnhiaWd1amsvSVd3R3d6MG9qWkFYbmwvak9pUTN5S25CQUZPV002RjljODg9",
                basicAuthData.getSignature());
    }
}
