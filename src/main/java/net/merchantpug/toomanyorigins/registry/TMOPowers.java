package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import io.github.apace100.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOPowers {
    public static void register() {
        register(ModifyDragonFireballPower.createFactory());
    }

    public static void register(PowerFactory<?> serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}