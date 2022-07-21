package com.github.merchantpug.toomanyorigins.forge;

import com.github.merchantpug.toomanyorigins.TooManyOriginsClient;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class TooManyOriginsForgeClient {
    public static void initialize() {
        TooManyOriginsClient.init();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> TooManyOriginsForgeClient::buildConfigScreen);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TooManyOriginsForgeClient::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        TooManyOriginsClient.setup();
    }

    private static Screen buildConfigScreen(MinecraftClient minecraftClient, Screen parent) {
        return AutoConfig.getConfigScreen(TooManyOriginsConfig.class, parent).get();
    }
}
