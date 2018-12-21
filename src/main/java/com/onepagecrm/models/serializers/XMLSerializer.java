package com.onepagecrm.models.serializers;

import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.exceptions.S3Exception;
import com.onepagecrm.models.internal.Utilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles on 04/10/2017.
 * Copyright (c) 2017 OnePageCRM. All rights reserved.
 */

@SuppressWarnings({"WeakerAccess", "SpellCheckingInspection"})
public class XMLSerializer extends BaseSerializer {

    private static final Logger LOG = Logger.getLogger(XMLSerializer.class.getSimpleName());

    protected static final String LOCATION_TAG = "Location";
    protected static final String BUCKET_TAG = "Bucket";
    protected static final String KEY_TAG = "Key";
    protected static final String ETAG_TAG = "ETag";

    protected static final String ERROR_TAG = "Error";
    protected static final String CODE_TAG = "Code";
    protected static final String MESSAGE_TAG = "Message";
    protected static final String REQUEST_ID_TAG = "RequestId";
    protected static final String HOST_ID_TAG = "HostId";

    public static Document documentFromString(String xml) throws OnePageException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            return builder.parse(is);

        } catch (Exception e) {
            final String message = "Problems parsing XML to Document.";
            LOG.severe(message);
            LOG.severe(e.toString());
            e.printStackTrace();
            throw new S3Exception(message).setErrorMessage(message);
        }
    }

    public static Element rootElementFromDoc(Document document) throws OnePageException {
        document.getDocumentElement().normalize();
        Element rootElement = document.getDocumentElement();
        if (ERROR_TAG.equals(rootElement.getTagName())) {
            final String code = XMLSerializer.stringFromElement(rootElement, CODE_TAG);
            final String message = XMLSerializer.stringFromElement(rootElement, MESSAGE_TAG);
            final String hostId = XMLSerializer.stringFromElement(rootElement, HOST_ID_TAG);
            final String requestId = XMLSerializer.stringFromElement(rootElement, REQUEST_ID_TAG);
            throw new S3Exception(message)
                    .setErrorName(code)
                    .setErrorMessage(message)
                    .setHostId(hostId)
                    .setRequestId(requestId);
        }
        return rootElement;
    }

    public static String stringFromElement(Element element, String tagName) {
        if (!Utilities.notNullOrEmpty(tagName) || element == null) {
            return "";
        }
        final NodeList elements = element.getElementsByTagName(tagName);
        final Node node = elements.item(0);
        return node != null ? node.getTextContent() : "";
    }
}
