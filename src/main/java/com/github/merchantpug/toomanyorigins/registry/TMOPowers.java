package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOPowers {
    public static final PowerType<TargetActionOnHitPower> DEATHLY_BITE = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "deathly_bite"));

    public static void register() {
        register(ModifyDragonFireballPower.createFactory());
    }

    public static void register(PowerFactory<?> serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}