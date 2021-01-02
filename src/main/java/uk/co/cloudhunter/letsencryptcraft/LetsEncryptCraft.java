package uk.co.cloudhunter.letsencryptcraft;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class LetsEncryptCraft implements PreLaunchEntrypoint {

    public LetsEncryptCraft() {
        LetsEncryptAdder.installLetsEncryptCertificates();
    }

    @Override
    public void onPreLaunch() {
    }
}
