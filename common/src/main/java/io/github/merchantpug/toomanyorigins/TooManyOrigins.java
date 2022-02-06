package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.registry.*;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins {
    public static final String MODID = "toomanyorigins";
    public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);
    public static String VERSION = "";

    public static void init() {
        LOGGER.info("Apugli " + VERSION + " is initializing. Powering up your powered up game.");

        TMOBlocks.register();
        TMOEffects.register();
        TMOEntities.register();
        TMOItems.register();
        TMOPowers.register();
        TMOSounds.register();
    }

    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }
}
