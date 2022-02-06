package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import io.github.merchantpug.toomanyorigins.registry.TMORegistriesArchitectury;
import net.minecraft.util.registry.Registry;

public class TMOPowersImpl {
    public static void register(PowerFactory<?> serializer) {
        ModRegistriesArchitectury.POWER_FACTORY.register(serializer.getSerializerId(), () -> serializer);
    }
}
