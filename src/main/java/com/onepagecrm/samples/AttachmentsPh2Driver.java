package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Attachment;
import com.onepagecrm.models.Deal;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.DeleteResult;
import com.onepagecrm.models.internal.FileReference;
import com.onepagecrm.models.internal.S3;
import com.onepagecrm.models.internal.S3Data;
import com.onepagecrm.models.internal.S3FileReference;
import com.onepagecrm.models.internal.S3Form;
import com.onepagecrm.net.request.Request;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class AttachmentsPh2Driver {

    private static final Logger LOG = Logger.getLogger(AttachmentsPh2Driver.class.getName());

    public static void main(String[] args) throws OnePageException {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");

            // Load the properties file
            prop.load(input);

        } catch (IOException e) {
            LOG.severe("Error loading the config.properties file");
            LOG.severe(e.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.severe("Error closing the config.properties file");
                    LOG.severe(e.toString());
                }
            }
        }

        OnePageCRM.setServer(Request.STAGING_SERVER);

        User loggedInUser = User.login(
                prop.getProperty("username"),
                prop.getProperty("password"));

        LOG.info("Logged in User : " + loggedInUser);

        final String contactId = "55acc4ee6f6e653fdc000099"; // Java Wrapper
        final String dealId = "575e8de59007ba0788493bd0"; // Big deal
        S3Form form = S3.form(contactId);
        S3Data data = form.getData();

        String fileName = "cillian.jpg";
        String contentType = "image/jpeg";
        String filePath = "./src/test/res/image_encode/" + fileName;
        FileReference file = new FileReference(filePath).setMimeType(contentType);
        LOG.info("" + file.toString());

        if (data == null || !data.isValid()) {
            LOG.severe("S3 data invalid!");
            return; // Cancel!
        }

        // OPTION 1: data + file
        final S3FileReference uploaded = S3.upload(contactId, data, file);

        // OPTION 2: form
        //form.setFileReference(file);
        //S3FileReference uploaded = S3.upload(contactId, form);

        // OPTION 3: fake
        //String hardcodedLocation = "https://s3-us-west-1.amazonaws.com/onepagecrm-ud2-us-west-1/56fa81eb9007ba07fc000080%2F1506589944670%2Fcillian.jpg";
        //String hardcodedBucket = "onepagecrm-ud2-us-west-1";
        //String hardcodedKey = "56fa81eb9007ba07fc000080/1506589944670/cillian.jpg";
        //String hardcodedEtag = "b771fbbc72899aecedaf3aa77fcce2c1";
        //long hardcodedSize = 31528L;
        //S3FileReference uploaded = new S3FileReference(file)
        //        .setLocation(hardcodedLocation)
        //        .setBucket(hardcodedBucket)
        //        .setKey(hardcodedKey)
        //        .setEtag(hardcodedEtag)
        //        .setSize(hardcodedSize);

        Deal reference = new Deal().setId(dealId);

        Attachment attachment = new Attachment(reference)
                .setFilename(fileName)
                .setProvider(Attachment.Provider.AMAZON);

        final Attachment saved = attachment.save(contactId, uploaded);

        final DeleteResult deleted = saved.delete();

        LOG.info("S3 data: " + data);
        LOG.info("Uploaded: " + uploaded);
        LOG.info("Saved: " + saved);
        LOG.info("Deleted: " + deleted);
    }
}
