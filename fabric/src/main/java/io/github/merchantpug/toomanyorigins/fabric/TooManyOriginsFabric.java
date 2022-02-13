package io.github.merchantpug.toomanyorigins.fabric;

import io.github.merchantpug.apugli.Apugli;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class TooManyOriginsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(TooManyOrigins.MODID).ifPresent(modContainer -> {
            TooManyOrigins.VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
        });
        TooManyOrigins.init();
    }
}
