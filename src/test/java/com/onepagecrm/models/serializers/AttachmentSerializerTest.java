package com.onepagecrm.models.serializers;

import com.onepagecrm.BaseTest;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.models.internal.S3FileReference;

import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 02/10/2017.
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class AttachmentSerializerTest extends BaseTest {

    private static Logger LOG = Logger.getLogger(AttachmentSerializerTest.class.getName());

    private static final String HARDCODED_FILENAME = "cillian.jpg";
    private static final String HARDCODED_LOCATION = "https://s3-us-west-1.amazonaws.com/onepagecrm-ud2-us-west-1/56fa81eb9007ba07fc000080%2F1506589944670%2Fcillian.jpg";
    private static final String HARDCODED_BUCKET = "onepagecrm-ud2-us-west-1";
    private static final String HARDCODED_KEY = "56fa81eb9007ba07fc000080/1506589944670/cillian.jpg";
    private static final String HARDCODED_ETAG = "\"b771fbbc72899aecedaf3aa77fcce2c1\"";

    private S3FileReference expected;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        expected = new S3FileReference()
                .setName(HARDCODED_FILENAME)
                .setLocation(HARDCODED_LOCATION)
                .setBucket(HARDCODED_BUCKET)
                .setKey(HARDCODED_KEY)
                .setEtag(HARDCODED_ETAG);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUploadS3_successXML() throws Exception {
        String filePath = "./src/test/res/responses/xml/s3_upload_success.xml";
        String successXml = FileUtilities.getResourceContents(filePath);
        S3FileReference actual = S3FileReferenceSerializer.fromString(successXml);

        // Make sure both are non-null and valid s3 file refs.
        assertTrue("Expected must be non-null and valid s3 file ref.",
                expected != null && expected.isValid());
        assertTrue("Actual must be non-null and valid s3 file ref.",
                actual != null && actual.isValid());

        assertEquals("Locations must be equal", expected.getLocation(), actual.getLocation());
        assertEquals("Buckets must be equal", expected.getBucket(), actual.getBucket());
        assertEquals("Keys must be equal", expected.getKey(), actual.getKey());
        assertEquals("ETags must be equal", expected.getEtag(), actual.getEtag());
    }

    public void testUploadS3_tooLargeFailureXML() throws Exception {
        String filePath = "./src/test/res/responses/xml/s3_upload_fail_too_large.xml";
        String failureXml = FileUtilities.getResourceContents(filePath);

        S3FileReference expectedFile = null;
        Exception expectedException = null;

        try {
            expectedFile = S3FileReferenceSerializer.fromString(failureXml);
        } catch (Exception e) {
            expectedException = e;
        }

        assertTrue("Exception is never thrown.", expectedFile == null && expectedException != null);
        assertTrue("Exception is not of expected type.", expectedException instanceof OnePageException);
    }

    public void testUploadS3_ServerFailureXML() throws Exception {
        String filePath = "./src/test/res/responses/xml/s3_upload_server_error.xml";
        String failureXml = FileUtilities.getResourceContents(filePath);

        S3FileReference expectedFile = null;
        Exception expectedException = null;

        try {
            expectedFile = S3FileReferenceSerializer.fromString(failureXml);
        } catch (Exception e) {
            expectedException = e;
        }

        assertTrue("Exception is never thrown.", expectedFile == null && expectedException != null);
        assertTrue("Exception is not of expected type.", expectedException instanceof OnePageException);
    }
}
