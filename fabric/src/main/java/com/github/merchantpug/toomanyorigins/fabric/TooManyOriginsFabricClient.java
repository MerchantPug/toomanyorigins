package com.github.merchantpug.toomanyorigins.fabric;

import com.github.merchantpug.toomanyorigins.TooManyOriginsClient;
import net.fabricmc.api.ClientModInitializer;

public class TooManyOriginsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooManyOriginsClient.init();
        TooManyOriginsClient.setup();
    }
}