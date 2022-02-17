package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.PowerFactory;

public class TMOPowersImpl {
    public static void register(PowerFactory<?> serializer) {
        TMORegistriesArchitecturyForge.POWER_FACTORY.register(serializer.getSerializerId(), () -> serializer);
    }
}