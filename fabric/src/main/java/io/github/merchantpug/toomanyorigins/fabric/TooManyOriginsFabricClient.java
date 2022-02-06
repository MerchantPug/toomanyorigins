package io.github.merchantpug.toomanyorigins.fabric;

import io.github.merchantpug.toomanyorigins.TooManyOriginsClient;
import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class TooManyOriginsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooManyOriginsClient.init();
        TooManyOriginsClient.setup();
    }
}