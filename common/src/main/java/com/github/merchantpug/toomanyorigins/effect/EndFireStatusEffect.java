package com.github.merchantpug.toomanyorigins.effect;

import com.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import com.github.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class EndFireStatusEffect extends InstantStatusEffect {

    public EndFireStatusEffect() {
        super(StatusEffectType.HARMFUL,
                0xc700c2);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(TMODamageSources.dragonMagic(), (float)(6 << amplifier));
    }

    @Override
    public void applyInstantEffect(Entity source, Entity attacker, LivingEntity target, int amplifier, double proximity) {
        int j = (int)(proximity * (double)(6 << amplifier) + 0.5D);
        if (this != TMOEffects.END_FIRE)
        {
            this.applyUpdateEffect(target, amplifier);
        } else {
            if (source == null) {
                target.damage(DamageSource.MAGIC, (float)j);
            } else {
                target.damage(DamageSource.magic(source, attacker), (float)j);
            }
        }
    }
}
