package com.onepagecrm.net.request;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.net.AuthData;
import com.onepagecrm.net.BasicAuthData;
import com.onepagecrm.net.OnePageAuthData;

@SuppressWarnings("WeakerAccess")
public abstract class SignedRequest extends Request {

    private AuthData authData;

    @Override
    public void setRequestHeaders() {
        super.setRequestHeaders();

        if (getAuthData() == null || getAuthData().getUserId() == null) {
            return;
        }

        if (!OnePageCRM.COMPLEX_AUTH && getAuthData() instanceof BasicAuthData) {
            BasicAuthData authData = (BasicAuthData) getAuthData();
            connection.setRequestProperty(AUTHORIZATION, authData.getSignature());

        } else if (OnePageCRM.COMPLEX_AUTH && getAuthData() instanceof OnePageAuthData) {
            OnePageAuthData authData = (OnePageAuthData) getAuthData();
            connection.setRequestProperty(X_UID, authData.getUserId());
            connection.setRequestProperty(X_TS, Integer.toString(authData.getTimestamp()));
            connection.setRequestProperty(X_AUTH, authData.getSignature());
        }
    }

    @Override
    protected void printHeaders() {
        super.printHeaders();

        if (getAuthData() == null || getAuthData().getUserId() == null) {
            return;
        }

        if (!OnePageCRM.COMPLEX_AUTH && getAuthData() instanceof BasicAuthData) {
            printValue(AUTHORIZATION, getAuthData().getSignature());

        } else if (OnePageCRM.COMPLEX_AUTH && getAuthData() instanceof OnePageAuthData) {
            printHeader(X_UID);
            printHeader(X_TS);
            printHeader(X_AUTH);
        }
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    public AuthData getAuthData() {
        return authData;
    }
}
