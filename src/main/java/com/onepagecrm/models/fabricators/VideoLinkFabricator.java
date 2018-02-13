package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.VideoLink;
import com.onepagecrm.models.VideoTutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/02/2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class VideoLinkFabricator extends BaseFabricator {

    private static final Logger LOG = Logger.getLogger(VideoLinkFabricator.class.getName());

    public static VideoLink single() {
        return new VideoLink()
                .setLink("https://cdn-static.onepagecrm.com/videos/android/mobile_android_add_deal.mp4")
                .setResolution("480");
    }

    public static List<VideoLink> list() {
        List<VideoLink> videoLinkList = new ArrayList<>();

        VideoLink videoLink1 = new VideoLink().
                setLink("https://cdn-static.onepagecrm.com/videos/android/mobile_android_add_deal.mp4").
                setResolution("480");
        videoLinkList.add(videoLink1);

        VideoLink videoLink2 = new VideoLink().
                setLink("https://cdn-static.onepagecrm.com/videos/android/contact_view.mp4").
                setResolution("480");
        videoLinkList.add(videoLink2);

        VideoLink videoLink3 = new VideoLink().
                setLink("https://cdn-static.onepagecrm.com/videos/android/next_actions.mp4").
                setResolution("480");
        videoLinkList.add(videoLink3);

        return videoLinkList;
    }

    public static List<VideoLink> singleItemList() {
        // Create a list for video links.
        List<VideoLink> videoLinkList = new ArrayList<>();
        // Add one item.
        videoLinkList.add(single());
        return videoLinkList;
    }
}
