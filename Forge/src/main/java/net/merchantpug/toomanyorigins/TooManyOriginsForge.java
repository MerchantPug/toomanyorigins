package net.merchantpug.toomanyorigins;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

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
    }
}