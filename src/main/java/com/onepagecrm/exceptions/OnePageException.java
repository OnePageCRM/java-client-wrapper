package com.onepagecrm.exceptions;

/**
 * OnePageCRM HTTP response codes.
 * <p/>
 * 200 	OK - Operation successful.
 * <p/>
 * 400 	Bad Request. The request is not properly formatted, does not properly specify response format or contains
 * improper parameters or data.
 * <p/>
 * 401 	Unauthorised. Authorization data is absent, invalid or expired.
 * <p/>
 * 402 	Payment Required. Trial expired, subscription cancelled or payment declined etc.
 * <p/>
 * 403 	Forbidden. Issued when user is authenticated, but does not have permission for requested operation.
 * <p/>
 * 404 	Resource Not Found. When an id was provided for a request but no resource exists for that object.
 * <p/>
 * 405 	Method Not Allowed. A request method is not supported for the requested resource; for example, a GET request on
 * a form that requires data to be presented via POST, or a PUT request on a read-only resource.
 * <p/>
 * 409 	Precondition Failed. Server state does not allow requested operation to be carried on. Issued in cases such as
 * when trying to add an assigned nextPage action to a contact which already has one assigned for a user.
 * <p/>
 * 500 	Internal Server Error. A generic error message, given when an unexpected condition was encountered and no more
 * specific message is suitable.
 * <p/>
 * 503 	Service Unavailable. The server is currently unavailable (because it is overloaded or down for maintenance).
 * <p/>
 */

import java.util.Map;

public class OnePageException extends Exception {

    private String errorName;
    private int status;
    private String message;
    private String errorMessage;
    private Map<String, String> errors;

    public OnePageException() {

    }

    public OnePageException(String message) {
        this.setMessage(message);
    }

    public String getErrorName() {
        return errorName;
    }

    public OnePageException setErrorName(String errorName) {
        this.errorName = errorName;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public OnePageException setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OnePageException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public OnePageException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public OnePageException setErrors(Map<String, String> errors) {
        this.errors = errors;
        return this;
    }
}
