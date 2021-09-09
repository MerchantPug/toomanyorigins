package io.github.merchantpug.toomanyorigins.fabric;

import net.fabricmc.api.ModInitializer;

import static io.github.merchantpug.toomanyorigins.TooManyOrigins.register;

public class TooManyOriginsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        register();
    }
}
