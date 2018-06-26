package com.onepagecrm;

/**
 * Created by Cillian Myles on 09/04/2018.
 * Copyright (c) 2018 OnePageCRM. All rights reserved.
 */
public class TestHelper {

    public static int randomInRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
}
