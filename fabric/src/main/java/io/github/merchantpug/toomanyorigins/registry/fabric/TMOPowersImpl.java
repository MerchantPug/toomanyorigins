package io.github.merchantpug.toomanyorigins.registry.fabric;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.util.registry.Registry;

public class TMOPowersImpl {
    public static void register(PowerFactory<?> serializer) {
        Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}
