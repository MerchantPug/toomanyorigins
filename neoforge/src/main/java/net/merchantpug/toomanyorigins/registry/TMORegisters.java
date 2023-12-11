package net.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.badge.BadgeFactory;
import io.github.edwinmindcraft.origins.api.registry.OriginsBuiltinRegistries;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import io.github.edwinmindcraft.apoli.api.power.factory.*;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;

public class TMORegisters {
    
    public static final RegistrationProvider<PowerFactory<?>> POWER_FACTORIES = RegistrationProvider.get(ApoliRegistries.POWER_FACTORY_KEY, TooManyOrigins.MOD_ID);

    public static final RegistrationProvider<BadgeFactory> BADGE_FACTORIES = RegistrationProvider.get(OriginsBuiltinRegistries.BADGE_FACTORY_KEY, TooManyOrigins.MOD_ID);

}