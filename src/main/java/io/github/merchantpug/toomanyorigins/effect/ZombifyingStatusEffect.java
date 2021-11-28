package io.github.merchantpug.toomanyorigins.effect;

import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class ZombifyingStatusEffect extends StatusEffect {
    public ZombifyingStatusEffect() {
        super(StatusEffectCategory.HARMFUL,
                0x274121);
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == TMOEffects.ZOMBIFYING) {
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).addExhaustion(4.0F);
            }
            entity.damage(TMODamageSources.ZOMBIFICATION, 1.0F);
        }
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int k;
        k = 25 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
}
