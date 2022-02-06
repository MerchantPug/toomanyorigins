package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.TargetActionOnHitPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.power.*;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class TMOPowers {

    public static final PowerType<TargetActionOnHitPower> DEATHLY_BITE = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "deathly_bite"));

    public static void register() {
        register(ModifyDragonFireballPower.getFactory());
    }

    @ExpectPlatform
    private static void register(PowerFactory<?> serializer) {
        throw new AssertionError();
    }
}
