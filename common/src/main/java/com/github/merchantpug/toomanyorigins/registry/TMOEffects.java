package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.effect.ChargedStatusEffect;
import com.github.merchantpug.toomanyorigins.effect.EndFireStatusEffect;
import com.github.merchantpug.toomanyorigins.effect.ZombifyingStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;

public class TMOEffects {
    public static final StatusEffect CHARGED = new ChargedStatusEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "c12451f1-b2a4-47aa-88ef-3f11b1b21e5e", 0.20000000298023224D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);;
    public static final InstantStatusEffect END_FIRE = new EndFireStatusEffect();
    public static final StatusEffect ZOMBIFYING = new ZombifyingStatusEffect();

    public static void register() {
        TMORegistriesArchitectury.STATUS_EFFECTS.register(new Identifier(TooManyOrigins.MODID, "charged"), () -> CHARGED);
        TMORegistriesArchitectury.STATUS_EFFECTS.register(new Identifier(TooManyOrigins.MODID, "end_fire"), () -> END_FIRE);
        TMORegistriesArchitectury.STATUS_EFFECTS.register(new Identifier(TooManyOrigins.MODID, "zombifying"), () -> ZOMBIFYING);
    }
}
