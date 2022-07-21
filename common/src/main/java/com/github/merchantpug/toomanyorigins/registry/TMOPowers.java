package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import com.github.merchantpug.toomanyorigins.power.PreventShaderTogglePower;
import com.github.merchantpug.toomanyorigins.power.SimpleValueModifierPower;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.PowerTypeReference;
import io.github.apace100.origins.power.TargetActionOnHitPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.power.*;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class TMOPowers {

    public static final PowerType<TargetActionOnHitPower> DEATHLY_BITE = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "deathly_bite"));
    public static final PowerType<SimpleValueModifierPower> LOOSE_SCALES = new PowerTypeReference(TooManyOrigins.identifier("loose_scales"));

    public static void register() {
        register(ModifyDragonFireballPower.createFactory());
        register(PreventShaderTogglePower.createFactory());
        register(SimpleValueModifierPower.createFactory());
    }

    @ExpectPlatform
    private static void register(PowerFactory<?> serializer) {
        throw new AssertionError();
    }
}
