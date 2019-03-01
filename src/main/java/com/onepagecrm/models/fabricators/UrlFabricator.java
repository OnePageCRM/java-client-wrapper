package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 16/02/2016.
 */
public class UrlFabricator extends BaseFabricator {

    public static Url single() {
        return new Url()
                .setType(Url.TYPE_WEBSITE)
                .setValue("www.website.com");
    }

    public static List<Url> list() {
        // Create a list for Web addresses.
        List<Url> urls = new ArrayList<>();

        Url website = new Url();
        website.setType(Url.TYPE_WEBSITE);
        website.setValue("www.website.com");
        urls.add(website);

        Url blog = new Url();
        blog.setType(Url.TYPE_BLOG);
        blog.setValue("www.blog.com");
        urls.add(blog);

        Url twitter = new Url();
        twitter.setType(Url.TYPE_TWITTER);
        twitter.setValue("www.twitter.com");
        urls.add(twitter);

        Url linkedIn = new Url();
        linkedIn.setType(Url.TYPE_LINKEDIN);
        linkedIn.setValue("www.linkedin.com");
        urls.add(linkedIn);

        Url xing = new Url();
        xing.setType(Url.TYPE_XING);
        xing.setValue("www.xing.com");
        urls.add(xing);

        Url facebook = new Url();
        facebook.setType(Url.TYPE_FACEBOOK);
        facebook.setValue("www.facebook.com");
        urls.add(facebook);

        Url googlePlus = new Url();
        googlePlus.setType(Url.TYPE_GOOGLE_PLUS);
        googlePlus.setValue("www.googleplus.com");
        urls.add(googlePlus);

        Url otherUrl = new Url();
        otherUrl.setType(Url.TYPE_OTHER);
        otherUrl.setValue("www.other.com");
        urls.add(otherUrl);

        return urls;
    }

    public static List<Url> singleItemList() {
        // Create a list for Web addresses.
        List<Url> urls = new ArrayList<>();
        // Add one item.
        urls.add(single());
        return urls;
    }
}
