package com.onepagecrm.models.fabricators;

import com.onepagecrm.models.Call;
import com.onepagecrm.models.CallResult;
import com.onepagecrm.models.serializers.InstantSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 29/08/2016.
 */
public class CallFabricator extends BaseFabricator {

    public static Call single() {
        return new Call()
                .setId("573eddb89007ba18d28d1dc1")
                .setAuthor("Cillian M.")
                .setText("Java Wrapper call")
                .setTime(InstantSerializer.getInstance().ofMillis(1463737799755L))
                .setContactId("56fa81eb9007ba07fc000080")
                .setVia("unknown")
                .setPhoneNumber(null)
                .setRecordingLink("")
                .setCreatedAt(InstantSerializer.getInstance().parse("2016-05-20T09:49:44Z"))
                .setModifiedAt(InstantSerializer.getInstance().parse("2016-05-20T09:49:44Z"))
                .setCallResult(new CallResult()
                        .setId("interested"));
    }

    public static List<Call> list() {
        List<Call> calls = new ArrayList<>();
        calls.add(single());
        calls.add(new Call()
                .setId("5757e4b69007ba13a3d4b09d")
                .setAuthor("Cillian M.")
                .setText("")
                .setTime(InstantSerializer.getInstance().ofSeconds(1465377900L))
                .setContactId("56fa81eb9007ba07fc000080")
                .setVia("unknown")
                .setPhoneNumber("0868825552 Direct")
                .setRecordingLink("")
                .setCreatedAt(InstantSerializer.getInstance().parse("2016-06-08T09:26:14Z"))
                .setModifiedAt(InstantSerializer.getInstance().parse("2016-06-08T09:26:14Z"))
                .setCallResult(new CallResult()
                        .setId("559fcd4c6f6e65629c0001ad"))
        );
        calls.add(new Call()
                .setId("57444c7c9007ba0d4a6f828b")
                .setAuthor(null)
                .setText("Tester")
                .setTime(InstantSerializer.getInstance().ofSeconds(1464093820L))
                .setContactId("56fa81eb9007ba07fc000080")
                .setVia("unknown")
                .setPhoneNumber(null)
                .setRecordingLink("")
                .setCreatedAt(InstantSerializer.getInstance().parse("2016-05-24T12:43:40Z"))
                .setModifiedAt(InstantSerializer.getInstance().parse("2016-05-24T12:43:40Z"))
                .setCallResult(new CallResult()
                        .setId("interested"))
        );
        return calls;
    }
}
