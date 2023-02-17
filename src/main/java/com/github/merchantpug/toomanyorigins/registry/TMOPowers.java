package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public class TMOPowers {

    public static void register() {
        register(ModifyDragonFireballPower.createFactory());
    }

    public static void register(PowerFactory<?> serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}