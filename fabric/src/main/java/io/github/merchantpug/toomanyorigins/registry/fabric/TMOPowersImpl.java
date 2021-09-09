package io.github.merchantpug.toomanyorigins.registry.fabric;

import io.github.apace100.origins.registry.ModRegistries;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.util.registry.Registry;

public class TMOPowersImpl {
    public static void init() {
        TMOPowers.POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, TMOPowers.POWER_FACTORIES.get(powerType), powerType));
    }
}
