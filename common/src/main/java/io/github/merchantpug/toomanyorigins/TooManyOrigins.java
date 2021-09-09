package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.registry.*;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins {
    public static final String MODID = "toomanyorigins";
    public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);

    public static void register() {
        LOGGER.info("TooManyOrigins is initializing. Enjoy!");
        TMOBlocks.register();
        TMOEffects.register();
        TMOEntities.register();
        TMOItems.register();
        TMOPowers.init();
        TMOSounds.register();

        TMOBlockActions.register();
        TMOEntityActions.register();

        TMOBlockConditions.register();
        TMODamageConditions.register();
        TMOEntityConditions.register();
    }

    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }
}
