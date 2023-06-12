package net.merchantpug.toomanyorigins;

import net.merchantpug.apugli.network.ApugliPacketHandler;
import net.merchantpug.toomanyorigins.network.TMOPacketHandler;
import net.merchantpug.toomanyorigins.util.TMOConfigs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TooManyOrigins.MOD_ID)
public class TooManyOriginsForge {
    public static String VERSION = "";
    
    public TooManyOriginsForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
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

        eventBus.addListener((FMLCommonSetupEvent event) -> TMOPacketHandler.register());
    }
}