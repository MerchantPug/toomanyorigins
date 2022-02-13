package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.merchantpug.toomanyorigins.registry.TMORegistriesArchitectury;

public class TMOPowersImpl {
    public static void register(PowerFactory<?> serializer) {
        TMORegistriesArchitectury.POWER_FACTORY.register(serializer.getSerializerId(), () -> serializer);
    }
}