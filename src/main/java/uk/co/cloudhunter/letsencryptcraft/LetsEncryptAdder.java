package uk.co.cloudhunter.letsencryptcraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class LetsEncryptAdder {

    private static final String UNKNOWN_ERROR_MSG = "" +
            "An unknown error occurred whilst adding the Let's Encrypt root certificate." +
            " I'm afraid you may not be able to access resources with a Let's Encrypt certificate D:";

    private static final Logger logger = LogManager.getLogger("letsencrypt");

    private static boolean certificateLoaded;

    public static boolean isCertificateLoaded() {
        return LetsEncryptAdder.certificateLoaded;
    }

    private static void addLetsEncryptCertificates() throws Exception {
        new CertChain()
                .load("isrgrootx1")
                .load("lets-encrypt-r3")
                .done();
    }

    private static boolean testJavaVersion() {
        String version = System.getProperty("java.version");
        // java 7 will not be used
        if (!version.startsWith("1.8.0_")) {
            return true;
        }
        return Integer.parseInt(version.substring(6)) >= 101;
    }

    private static void checkCertificateValidity() {
        new Thread(() -> {
            try {
                URL url = new URL("https://helloworld.letsencrypt.org");
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();

                logger.info("Done - you are now able to access resources with a Let's Encrypt certificate :D");
                LetsEncryptAdder.certificateLoaded = true;
            } catch (IOException e) {
                logger.error(UNKNOWN_ERROR_MSG, e);
            }
        }, "Lets Encrypt Certificate Verifier").start();
    }

    static void installLetsEncryptCertificates() {
        if (testJavaVersion()) {
            logger.info("Not running as Java version is at least Java 8u101.");
            LetsEncryptAdder.certificateLoaded = true;
            return;
        }

        try {
            logger.info("Adding Let's Encrypt certificates");
            LetsEncryptAdder.addLetsEncryptCertificates();
            logger.info("Attempting to connect to https://helloworld.letsencrypt.org...");
            LetsEncryptAdder.checkCertificateValidity();
        } catch (Exception e) {
            logger.error(UNKNOWN_ERROR_MSG, e);
        }
    }
}
