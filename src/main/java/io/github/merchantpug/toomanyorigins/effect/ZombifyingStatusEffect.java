package io.github.merchantpug.toomanyorigins.effect;

import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class ZombifyingStatusEffect extends StatusEffect {
    int mobUpdate = 1;

    public ZombifyingStatusEffect() {
        super(StatusEffectType.HARMFUL,
                0x274121);
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == TMOEffects.ZOMBIFYING) {
            if (entity instanceof PlayerEntity) {
                entity.damage(TMODamageSources.ZOMBIFICATION, 1.0F);
                ((PlayerEntity)entity).addExhaustion(4F);
            } else {
                if (mobUpdate > 0) {
                    mobUpdate -= 1;
                } else {
                    entity.damage(TMODamageSources.ZOMBIFICATION, 5.0F);
                    mobUpdate = 1;
                }
            }
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
