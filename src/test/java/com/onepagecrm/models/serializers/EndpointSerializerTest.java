package com.onepagecrm.models.serializers;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Cillian Myles on 06/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class EndpointSerializerTest {

    @Test
    public void removesTrailingSlash_simple() {
        final String expected = "https://secure.onepagecrm.com/api/v3";
        final String input = "https://secure.onepagecrm.com/api/v3/";
        final String output = EndpointSerializer.full(input);

        assertEquals(expected, output);
    }

    @Test
    public void removesApiNamespace_simple() {
        final String expected = "https://secure.onepagecrm.com";
        final String input = "https://secure.onepagecrm.com/api/v3/";
        final String output = EndpointSerializer.base(input);

        assertEquals(expected, output);
    }

    @Test
    public void removesTrailingSlash_withPort() {
        final String expected = "http://127.0.0.1:3000/api/v3";
        final String input = "http://127.0.0.1:3000/api/v3/";
        final String output = EndpointSerializer.full(input);

        assertEquals(expected, output);
    }

    @Test
    public void removesApiNamespace_withPort() {
        final String expected = "http://127.0.0.1:3000";
        final String input = "http://127.0.0.1:3000/api/v3/";
        final String output = EndpointSerializer.base(input);

        assertEquals(expected, output);
    }
}
