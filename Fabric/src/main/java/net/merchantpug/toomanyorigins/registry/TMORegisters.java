package net.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.badge.BadgeManager;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;

public class TMORegisters {
    
    public static final RegistrationProvider<PowerFactory> POWER_FACTORIES = RegistrationProvider.get(ApoliRegistries.POWER_FACTORY, TooManyOrigins.MOD_ID);

}