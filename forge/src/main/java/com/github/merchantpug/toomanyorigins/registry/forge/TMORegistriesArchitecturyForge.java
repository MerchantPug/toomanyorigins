package com.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.PowerFactory;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.architectury.registry.Registries;
import me.shedaniel.architectury.registry.Registry;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.RegistryKey;

public class TMORegistriesArchitecturyForge {
    public static final Lazy<Registries> REGISTRIES = new Lazy<>(() -> Registries.get(TooManyOrigins.MODID));

    public static final Registry<PowerFactory<?>> POWER_FACTORY;

    static {
        Registries registries = REGISTRIES.get();
        POWER_FACTORY = registries.get(RegistryKey.ofRegistry(Origins.identifier("power_factory")));
    }
}