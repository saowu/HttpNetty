package org.saowu;

import org.saowu.core.server.BootServer;

/**
 * HttpNetty
 *
 */
public class Application {
    public static void main(String[] args) throws Exception {
        new BootServer().start();
    }
}
