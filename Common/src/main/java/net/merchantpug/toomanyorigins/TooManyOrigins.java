package net.merchantpug.toomanyorigins;

import io.github.apace100.apoli.util.NamespaceAlias;
import net.merchantpug.toomanyorigins.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TooManyOrigins {
    
    public static final String MOD_ID = "toomanyorigins";
    public static final String MOD_NAME = "TooManyOrigins";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        TMOBlocks.register();
        TMOEffects.register();
        TMOEntityTypes.register();
        TMOItems.register();
        TMOPowers.register();
        TMOSounds.register();

        TMODamageConditions.registerAll();

        NamespaceAlias.addAlias(MOD_ID, "apugli");
    }
    
    public static ResourceLocation asResource(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
    
}