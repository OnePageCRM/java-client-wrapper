package com.onepagecrm.exceptions;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 02/12/2015.
 */
public class InvalidListingTypeException extends OnePageException {

    public InvalidListingTypeException(String message) {
        super(message);
        this.setErrorMessage(message);
    }

    public InvalidListingTypeException setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
        return this;
    }
}
