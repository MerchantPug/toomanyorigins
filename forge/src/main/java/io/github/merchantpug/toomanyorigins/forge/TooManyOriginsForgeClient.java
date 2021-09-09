package io.github.merchantpug.toomanyorigins.forge;

import io.github.merchantpug.toomanyorigins.TooManyOriginsClient;
import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class TooManyOriginsForgeClient {
    public static void initialize() {
        TooManyOriginsClient.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TooManyOriginsForgeClient::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        TooManyOriginsClient.setup();
    }

}
