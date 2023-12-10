package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.data.LegacyContentManager;
import net.merchantpug.toomanyorigins.network.TMOPacketHandler;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TooManyOriginsForge::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        TMOPacketHandler.register();
    }

    @Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class TMOForgeBusEventHandler {
        @SubscribeEvent
        public static void addReloadListeners(AddReloadListenerEvent event) {
            event.addListener(new LegacyContentManager());
        }
    }

}