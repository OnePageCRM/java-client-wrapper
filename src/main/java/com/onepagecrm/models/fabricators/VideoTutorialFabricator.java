package com.onepagecrm.models.fabricators;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Contact;
import com.onepagecrm.models.ContactList;
import com.onepagecrm.models.VideoTutorial;
import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.models.serializers.ContactListSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 15/02/2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class VideoTutorialFabricator extends BaseFabricator {

    private static final Logger LOG = Logger.getLogger(VideoTutorialFabricator.class.getName());

    public static VideoTutorial single() {
        return new VideoTutorial()
                .setId("1")
                .setName("Video 1")
                .setVideoLinks(VideoLinkFabricator.singleItemList());
    }

    public static List<VideoTutorial> list() {
        List<VideoTutorial> videoTutorialList = new ArrayList<>();

        VideoTutorial videoTutorial1 = new VideoTutorial().
                setId("1").
                setName("Video 1").
                setVideoLinks(VideoLinkFabricator.singleItemList());
        videoTutorialList.add(videoTutorial1);

        VideoTutorial videoTutorial2 = new VideoTutorial().
                setId("2").
                setName("Video 2").
                setVideoLinks(VideoLinkFabricator.singleItemList());;
        videoTutorialList.add(videoTutorial2);

        VideoTutorial videoTutorial3 = new VideoTutorial().
                setId("3").
                setName("Video 3").
                setVideoLinks(VideoLinkFabricator.singleItemList());
        videoTutorialList.add(videoTutorial3);

        return videoTutorialList;
    }
}
