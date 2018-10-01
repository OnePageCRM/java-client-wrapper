package com.onepagecrm.exceptions;

/**
 * Created by Cillian Myles on 01/10/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
@SuppressWarnings("WeakerAccess")
public class ConnectivityException extends OnePageException {

    public static final String MESSAGE = "Please verify your internet connection.";

    public ConnectivityException() {
        this(MESSAGE);
    }

    private ConnectivityException(String message) {
        super(message);
    }
}
