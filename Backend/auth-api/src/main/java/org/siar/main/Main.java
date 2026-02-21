package org.siar.main;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        Quarkus.run(Server.class, args);
    }


    public static class Server implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            Log.info("*************************");
            Log.info("******* Let's go! *******");
            Log.info("*************************");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
