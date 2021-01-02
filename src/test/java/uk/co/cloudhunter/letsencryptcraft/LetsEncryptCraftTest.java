package uk.co.cloudhunter.letsencryptcraft;

/**
 * Tests the certificates without having to start up minecraft.
 *
 * Note: Since recent versions of java include the let's encrypt certificates,
 * testing in your IDE will typically not do anything. In order to properly
 * test, you will need to set the Alternative JRE option in your run configs.
 *
 * The JRE should be set to {@code "C:\Program Files (x86)\Minecraft Launcher\runtime\jre-x64"}
 * or where ever your launcher installed Java.
 */
public class LetsEncryptCraftTest {

    public static void main(String[] args) {
        LetsEncryptAdder.installLetsEncryptCertificates();
    }

}
