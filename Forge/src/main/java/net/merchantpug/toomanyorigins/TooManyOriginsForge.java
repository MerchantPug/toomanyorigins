package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.util.TMOConfigs;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(TooManyOrigins.MOD_ID)
public class TooManyOriginsForge {
    public static String VERSION = "";
    
    public TooManyOriginsForge() {
        VERSION = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        if (VERSION.contains("+")) {
            VERSION = VERSION.split("\\+")[0];
        }
        if (VERSION.contains("-")) {
            VERSION = VERSION.split("-")[0];
        }
        TooManyOrigins.LOG.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");
        TooManyOrigins.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, TMOConfigs.SERVER_SPECS);
    }
}