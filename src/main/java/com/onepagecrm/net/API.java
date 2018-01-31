package com.onepagecrm.net;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.StartupData;
import com.onepagecrm.models.internal.LoginData;
import com.onepagecrm.models.serializers.LoginDataSerializer;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.models.serializers.StartupDataSerializer;
import com.onepagecrm.net.request.GetRequest;
import com.onepagecrm.net.request.GoogleLoginRequest;
import com.onepagecrm.net.request.LoginRequest;
import com.onepagecrm.net.request.Request;

import static com.onepagecrm.net.ApiResource.BOOTSTRAP_ENDPOINT;
import static com.onepagecrm.net.ApiResource.STARTUP_ENDPOINT;

/**
 * Created by Cillian Myles on 02/01/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
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

        public static com.onepagecrm.models.User googleLogin(String authCode) throws OnePageException {
            Request request = new GoogleLoginRequest(authCode, true);
            Response response = request.send();
            return LoginSerializer.fromString(response.getResponseBody());
        }

        public static com.onepagecrm.models.User googleSignup(String authCode) throws OnePageException {
            Request request = new GoogleLoginRequest(authCode, false);
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
            OnePageCRM.setServer(Request.AUTH_SERVER);
            LoginData loginData = Auth.authenticate(username, password);
            OnePageCRM.setServer(Request.CUSTOM_URL_SERVER);
            OnePageCRM.setCustomUrl(loginData.getEndpointUrl());
            return Auth.startup(loginData.setFullResponse(true));
        }

        public static StartupData startup() throws OnePageException {
            return API.Auth.startup();
        }

        public static StartupData fullRefresh() throws OnePageException {
            return API.Auth.startup();
        }
    }
}
