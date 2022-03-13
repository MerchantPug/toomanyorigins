package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.apugli.util.ApugliNamespaceAlias;
import io.github.merchantpug.toomanyorigins.registry.*;
import io.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins {
    public static final String MODID = "toomanyorigins";
    public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);
    public static String VERSION = "";

    public static TooManyOriginsConfig config;

    public static void init() {
        LOGGER.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");

        TMOBlocks.register();
        TMOEffects.register();
        TMOEntities.register();
        TMOItems.register();
        TMOPowers.register();
        TMOSounds.register();

        ApugliNamespaceAlias.addAlias("toomanyorigins");

        AutoConfig.register(TooManyOriginsConfig.class, JanksonConfigSerializer::new);
        TooManyOrigins.config = AutoConfig.getConfigHolder(TooManyOriginsConfig.class).getConfig();
    }

    public static Identifier identifier(String path) {
        return new Identifier(MODID, path);
    }
}
