package com.onepagecrm.exceptions;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 31/08/2017.
 */
public class InvalidListingTypeException extends OnePageException {

    public InvalidListingTypeException(String message) {
        super(message);
    }

    public InvalidListingTypeException() {
        super();
    }
}
