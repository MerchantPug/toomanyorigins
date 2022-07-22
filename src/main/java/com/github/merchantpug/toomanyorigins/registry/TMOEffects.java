package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.effect.ChargedStatusEffect;
import com.github.merchantpug.toomanyorigins.effect.EndFireStatusEffect;
import com.github.merchantpug.toomanyorigins.effect.ZombifyingStatusEffect;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOEffects {
    public static final StatusEffect CHARGED = new ChargedStatusEffect();
    public static final InstantStatusEffect END_FIRE = new EndFireStatusEffect();
    public static StatusEffect ZOMBIFYING;

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "charged"), CHARGED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "end_fire"), END_FIRE);
        if (TooManyOriginsConfig.legacyUndeadEnabled) {
            ZOMBIFYING = Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "zombifying"), new ZombifyingStatusEffect());
        }
    }
}
