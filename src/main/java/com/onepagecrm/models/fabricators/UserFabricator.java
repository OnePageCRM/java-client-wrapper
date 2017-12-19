package com.onepagecrm.models.fabricators;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.Account;
import com.onepagecrm.models.User;
import com.onepagecrm.models.internal.FileUtilities;
import com.onepagecrm.models.serializers.LoginSerializer;
import com.onepagecrm.net.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 15/02/2016.
 */
@SuppressWarnings("WeakerAccess")
public class UserFabricator extends BaseFabricator {

    private static final Logger LOG = Logger.getLogger(UserFabricator.class.getName());

    public static User single() {
        return loggedUser();
    }

    public static User basicUser() {
        return new User()
                .setId("556cb8b61787fa02e000047e")
                .setAuthKey("WqLLs1n/Y3SvOpGg5CNOpdKy74GkGI6lnhwSfYmgNl4=")
                .setFirstName("John")
                .setLastName("Smith")
                .setEmail("j.smith@bigcompanyinc.com");
    }

    public static List<User> list() {
        List<User> users = new ArrayList<>();
        User loggedUser = loggedUser();
        users.add(loggedUser);
        for (User member : Account.team) {
            if (!users.contains(member)) users.add(member);
        }
        return users;
    }

    private static User loggedUser() {
        User loggedUser = new User();
        String path = OnePageCRM.ASSET_PATH + "login.json";
        String body = FileUtilities.getResourceContents(path);
        Response response = Response.okay(body);
        try {
            loggedUser = LoginSerializer.fromResponse(response);
        } catch (OnePageException e) {
            LOG.severe("Problem creating user object from JSON file.");
            LOG.severe(e.toString());
        }
        return loggedUser;
    }
}
