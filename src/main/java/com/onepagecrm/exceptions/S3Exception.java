package com.onepagecrm.exceptions;

import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.models.internal.S3FileReference;

import java.util.Locale;
import java.util.Map;

import static com.onepagecrm.models.internal.Utilities.notNullOrEmpty;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 04/10/2017.
 */
@SuppressWarnings("WeakerAccess")
public class S3Exception extends OnePageException {

    public S3Exception(String message) {
        super(message);
    }

    public S3Exception() {

    }

    @Override
    public String getErrorName() {
        return super.getErrorName();
    }

    @Override
    public S3Exception setErrorName(String errorName) {
        super.setErrorName(errorName);
        return this;
    }

    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Override
    public S3Exception setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public S3Exception setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage();
    }

    @Override
    public S3Exception setErrorMessage(String errorMessage) {
        super.setErrorMessage(errorMessage);
        return this;
    }

    @Override
    public Map<String, String> getErrors() {
        return super.getErrors();
    }

    @Override
    public S3Exception setErrors(Map<String, String> errors) {
        super.setErrors(errors);
        return this;
    }

    public static String tooLargeMessage() {
        return tooLargeMessage(S3FileReference.MAX_SIZE_BYTES);
    }

    public static String tooLargeMessage(long maxSizeBytes) {
        return String.format(Locale.ENGLISH, "File size exceeds limit: %dMB.",
                FileUtilities.bytesToMb(maxSizeBytes));
    }

    public static String notEnoughSpaceMessage(String remainingText) {
        return !notNullOrEmpty(remainingText) ? "You have exceeded your file storage allowance." :
                String.format(Locale.ENGLISH, "You have exceeded your %s file storage allowance.", remainingText);
    }
}
