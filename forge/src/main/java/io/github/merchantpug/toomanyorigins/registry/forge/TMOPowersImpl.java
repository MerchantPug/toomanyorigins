package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import io.github.merchantpug.toomanyorigins.registry.TMORegistriesArchitectury;

public class TMOPowersImpl {
    public static void init() {
        TMOPowers.POWER_FACTORIES.keySet().forEach(powerType -> ModRegistriesArchitectury.POWER_FACTORY.register(TMOPowers.POWER_FACTORIES.get(powerType), () -> powerType));
    }
}
