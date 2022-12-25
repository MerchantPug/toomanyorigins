package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.content.effect.ChargedStatusEffect;
import com.github.merchantpug.toomanyorigins.content.effect.EndFireStatusEffect;
import com.github.merchantpug.toomanyorigins.content.legacy.effect.ZombifyingStatusEffect;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOEffects {
    public static final StatusEffect CHARGED = new ChargedStatusEffect();
    public static final InstantStatusEffect END_FIRE = new EndFireStatusEffect();
    public static final StatusEffect ZOMBIFYING = new ZombifyingStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "charged"), CHARGED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "end_fire"), END_FIRE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(TooManyOrigins.MODID, "zombifying"), ZOMBIFYING);
    }
}
