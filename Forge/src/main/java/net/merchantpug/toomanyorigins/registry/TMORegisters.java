package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import io.github.edwinmindcraft.apoli.api.power.factory.*;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;

public class TMORegisters {
    
    public static final RegistrationProvider<PowerFactory<?>> POWER_FACTORIES = RegistrationProvider.get(ApoliRegistries.POWER_FACTORY_KEY, TooManyOrigins.MOD_ID);
    public static final RegistrationProvider<DamageCondition<?>> DAMAGE_CONDITIONS = RegistrationProvider.get(ApoliRegistries.DAMAGE_CONDITION_KEY, TooManyOrigins.MOD_ID);

}