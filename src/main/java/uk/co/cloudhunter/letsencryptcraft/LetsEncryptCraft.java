package uk.co.cloudhunter.letsencryptcraft;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LetsEncryptCraft implements PreLaunchEntrypoint, ILetsEncryptMod {

    public static final String MOD_ID = "letsencryptcraft";

    public static Logger logger = LogManager.getLogger(LetsEncryptCraft.MOD_ID);

    public LetsEncryptCraft() {
        LetsEncryptAdder.doStuff(this);
    }

    @Override
    public void onPreLaunch() {
    }

    public void info(String log) {
        logger.info(log);
    }

    public void error(String log) {
        logger.error(log);
    }

    public void error(String log, Throwable t) {
        logger.error(log, t);
    }
}
