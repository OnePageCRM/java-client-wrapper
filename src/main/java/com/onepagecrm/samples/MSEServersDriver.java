package com.onepagecrm.samples;

import com.onepagecrm.OnePageCRM;
import com.onepagecrm.exceptions.OnePageException;
import com.onepagecrm.models.AppServer;
import com.onepagecrm.net.request.Request;

import java.util.List;
import java.util.logging.Logger;

public class MSEServersDriver {

    private static final Logger LOG = Logger.getLogger(MSEServersDriver.class.getName());

    public static void main(String[] args) throws OnePageException {

        List<AppServer> servers = OnePageCRM.availableServers(Request.AUTH_DEV_SERVER);

        LOG.info("Available servers: " + servers);

        int i = 0;
        for (AppServer server : servers) {
            LOG.info("Server[" + (i++) + "]: " + server);
        }
    }
}
