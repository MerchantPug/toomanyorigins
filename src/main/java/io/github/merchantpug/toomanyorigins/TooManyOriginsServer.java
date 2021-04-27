package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.registry.TMOEntityConditionsServer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class TooManyOriginsServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        TMOEntityConditionsServer.register();
    }
}
