package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.effect.ChargedStatusEffect;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.effect.EndFireStatusEffect;

public class TMOEffects {
    public static final InstantStatusEffect END_FIRE = new EndFireStatusEffect();
    public static final StatusEffect CHARGED = new ChargedStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "end_fire"), END_FIRE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "charged"), CHARGED);
    }
}
