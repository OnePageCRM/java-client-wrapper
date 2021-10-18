package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.helpers.TextHelper;
import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.ContactSerializer;
import com.onepagecrm.models.serializers.LoginDataSerializer;
import com.onepagecrm.models.serializers.BootstrapSerializer;
import com.onepagecrm.models.serializers.StartupDataSerializer;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.GoogleAuthRequest;
import com.onepagecrm.net.request.GoogleContactsAuthRequest;
import com.onepagecrm.net.request.GoogleLoginRequest;
import com.onepagecrm.net.request.LoginRequest;
import com.onepagecrm.net.request.PostRequest;
import com.onepagecrm.net.request.Request;

import static com.onepagecrm.net.ApiResource.BOOTSTRAP_ENDPOINT;
import static com.onepagecrm.net.ApiResource.GOOGLE_CONTACTS_ENDPOINT;
import static com.onepagecrm.net.ApiResource.STARTUP_ENDPOINT;
import static com.onepagecrm.net.request.Request.CUSTOM_URL_SERVER;
import static com.onepagecrm.net.request.Request.DEFAULT_AUTH_SERVER;
import static com.onepagecrm.net.request.Request.UNRESOLVED_SERVER;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection", "unused"})
public interface API {

    abstract class Auth {

        public static com.onepagecrm.models.User bootstrap() throws OnePageException {
            return API.User.bootstrap();
        }

        public static StartupData startup(LoginData loginData) throws OnePageException {
            Request request = new LoginRequest(loginData);
            Response response = request.send();
            return StartupDataSerializer.fromString(response.getResponseBody());
        }

        public static StartupData startup() throws OnePageException {
            Request request = new GetRequest(STARTUP_ENDPOINT);
            Response response = request.send();
            return StartupDataSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class Google {

        public static LoginData authenticate(String oauth2Code) throws OnePageException {
            Request request = new GoogleAuthRequest(oauth2Code);
            Response response = request.send();
            return LoginDataSerializer.fromString(response.getResponseBody());
        }

        public static LoginData signup(String oauth2Code, String serverId) throws OnePageException {
            Request request = new GoogleAuthRequest(oauth2Code, serverId);
            Response response = request.send();
            return LoginDataSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class GoogleContacts {

        public static boolean authorize(String oauth2Code) throws OnePageException {
            Request request = new GoogleContactsAuthRequest(oauth2Code);
            Response response = request.send();
            final int code = response != null ? response.getResponseCode() : -1;
            final String body = response != null ? response.getResponseBody() : "{malformed-json-body[]}";
            BootstrapSerializer.fromString(body); // only to throw exception
            return code == 200 || code == 201;
        }

        public static Contact save(Contact contact) throws OnePageException {
            if (contact == null || !contact.isValid()) {
                throw new OnePageException("Contact must be non-null and valid")
                        .setErrorMessage("Contact must be non-null and valid");
            }

            return TextHelper.isEmpty(contact.getGoogleId()) ? create(contact) : update(contact);
        }

        private static Contact create(Contact toBeSaved) throws OnePageException {
            return saveImpl(toBeSaved, false);
        }

        private static Contact update(Contact toBeSaved) throws OnePageException {
            return saveImpl(toBeSaved, true);
        }

        private static Contact saveImpl(Contact toBeSaved, boolean updating) throws OnePageException {
            Request request = new PostRequest(
                    GOOGLE_CONTACTS_ENDPOINT.replace("{id}", toBeSaved.getId()),
                    null);
            Response response = request.send();
            String responseBody = response.getResponseBody();
            Contact contact = ContactSerializer.fromString(responseBody);
            BootstrapSerializer.updateDynamicResources(responseBody);
            return contact;
        }
    }

    @Deprecated
    abstract class GoogleOld {

        @Deprecated
        public static com.onepagecrm.models.User login(String oauth2Code) throws OnePageException {
            Request request = new GoogleLoginRequest(oauth2Code, true);
            Response response = request.send();
            return BootstrapSerializer.fromString(response.getResponseBody());
        }

        @Deprecated
        public static com.onepagecrm.models.User signup(String oauth2Code) throws OnePageException {
            Request request = new GoogleLoginRequest(oauth2Code, false);
            Response response = request.send();
            return BootstrapSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class User {
        public static com.onepagecrm.models.User bootstrap() throws OnePageException {
            Request request = new GetRequest(BOOTSTRAP_ENDPOINT);
            Response response = request.send();
            return BootstrapSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class App {
        public static StartupData startup() throws OnePageException {
            return API.Auth.startup();
        }

        public static StartupData fullRefresh() throws OnePageException {
            return API.Auth.startup();
        }

        public static StartupData googleLogin(String oath2Code) throws OnePageException {
            return API.App.googleLogin(DEFAULT_AUTH_SERVER, oath2Code);
        }

        public static StartupData googleLogin(int authServerId, String oath2Code) throws OnePageException {
            OnePageCRM.setServer(authServerId);
            LoginData loginData = API.Google.authenticate(oath2Code);
            processLoginData(loginData);
            return API.Auth.startup(loginData.setFullResponse(true));
        }

        public static StartupData googleSignup(String oath2Code, String serverId) throws OnePageException {
            return API.App.googleSignup(DEFAULT_AUTH_SERVER, oath2Code, serverId);
        }

        public static StartupData googleSignup(int authServerId, String oath2Code, String serverId) throws OnePageException {
            OnePageCRM.setServer(authServerId);
            LoginData loginData = API.Google.signup(oath2Code, serverId);
            processLoginData(loginData);
            return API.Auth.startup(loginData.setFullResponse(true));
        }

        private static void processLoginData(LoginData loginData) {
            if (loginData == null) return;
            final int serverId = Request.getServerIdFromUrl(loginData.getEndpointUrl(), UNRESOLVED_SERVER);
            if (serverId != UNRESOLVED_SERVER) {
                OnePageCRM.setServer(serverId);
            } else {
                OnePageCRM.setServer(CUSTOM_URL_SERVER);
                OnePageCRM.setCustomUrl(loginData.getEndpointUrl());
            }
        }
    }
}
