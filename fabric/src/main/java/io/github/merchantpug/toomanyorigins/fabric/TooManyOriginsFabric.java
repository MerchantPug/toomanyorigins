package io.github.merchantpug.toomanyorigins.fabric;

import io.github.merchantpug.apugli.Apugli;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class TooManyOriginsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TooManyOrigins.init();
        FabricLoader.getInstance().getModContainer(Apugli.MODID).ifPresent(modContainer -> {
            TooManyOrigins.VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
        });
    }
}
