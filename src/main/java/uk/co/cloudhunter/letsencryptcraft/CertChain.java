package uk.co.cloudhunter.letsencryptcraft;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * Stream-lined api for loading certificates into the default ssl context
 */
public class CertChain {
    private final CertificateFactory cf = CertificateFactory.getInstance("X.509");
    private final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

    public CertChain() throws Exception {
        // load the built-in certs!
        Path ksPath = Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts");
        keyStore.load(Files.newInputStream(ksPath), null);
    }

    public CertChain load(String filename) throws Exception {
        try (InputStream cert = CertChain.class.getResourceAsStream("/" + filename + ".der")) {
            InputStream caInput = new BufferedInputStream(cert);
            Certificate crt = cf.generateCertificate(caInput);
            keyStore.setCertificateEntry(filename, crt);
        }
        return this;
    }

    public void done() throws Exception {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);
    }
}
