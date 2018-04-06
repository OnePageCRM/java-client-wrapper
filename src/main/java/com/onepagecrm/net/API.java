package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.LoginDataSerializer;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.models.serializers.StartupDataSerializer;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.GoogleAuthRequest;
import com.onepagecrm.net.request.GoogleLoginRequest;
import com.onepagecrm.net.request.LoginRequest;
import com.onepagecrm.net.request.Request;

import static com.onepagecrm.net.ApiResource.BOOTSTRAP_ENDPOINT;
import static com.onepagecrm.net.ApiResource.STARTUP_ENDPOINT;
import static com.onepagecrm.net.request.Request.CUSTOM_URL_SERVER;
import static com.onepagecrm.net.request.Request.DEFAULT_AUTH_SERVER;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection", "unused"})
public interface API {

    abstract class Auth {

        public static LoginData authenticate(String username, String password) throws OnePageException {
            Request request = new LoginRequest(username, password);
            Response response = request.send();
            final String responseBody = response.getResponseBody();
            return LoginDataSerializer.fromString(responseBody);
        }

        public static com.onepagecrm.models.User login(String username, String password) throws OnePageException {
            return API.User.login(username, password);
        }

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

    @Deprecated
    abstract class GoogleOld {

        @Deprecated
        public static com.onepagecrm.models.User login(String oauth2Code) throws OnePageException {
            Request request = new GoogleLoginRequest(oauth2Code, true);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }

        @Deprecated
        public static com.onepagecrm.models.User signup(String oauth2Code) throws OnePageException {
            Request request = new GoogleLoginRequest(oauth2Code, false);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class User {

        public static com.onepagecrm.models.User login(String username, String password) throws OnePageException {
            Request request = new LoginRequest(username, password);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }

        public static com.onepagecrm.models.User bootstrap() throws OnePageException {
            Request request = new GetRequest(BOOTSTRAP_ENDPOINT);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }
    }

    abstract class App {

        public static StartupData startup(String username, String password) throws OnePageException {
            return API.App.startup(DEFAULT_AUTH_SERVER, username, password);
        }

        public static StartupData startup(int authServerId, String username, String password) throws OnePageException {
            OnePageCRM.setServer(authServerId);
            LoginData loginData = API.Auth.authenticate(username, password);
            OnePageCRM.setServer(CUSTOM_URL_SERVER);
            OnePageCRM.setCustomUrl(loginData.getEndpointUrl());
            return API.Auth.startup(loginData.setFullResponse(true));
        }

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
            OnePageCRM.setServer(CUSTOM_URL_SERVER);
            OnePageCRM.setCustomUrl(loginData.getEndpointUrl());
            return API.Auth.startup(loginData.setFullResponse(true));
        }

        public static StartupData googleSignup(String oath2Code, String serverId) throws OnePageException {
            return API.App.googleSignup(DEFAULT_AUTH_SERVER, oath2Code, serverId);
        }

        public static StartupData googleSignup(int authServerId, String oath2Code, String serverId) throws OnePageException {
            OnePageCRM.setServer(authServerId);
            LoginData loginData = API.Google.signup(oath2Code, serverId);
            OnePageCRM.setServer(CUSTOM_URL_SERVER);
            OnePageCRM.setCustomUrl(loginData.getEndpointUrl());
            return API.Auth.startup(loginData.setFullResponse(true));
        }
    }
}
