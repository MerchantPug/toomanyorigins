package net.merchantpug.toomanyorigins.effect;

import net.merchantpug.toomanyorigins.registry.TMODamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ZombifyingStatusEffect extends MobEffect {
    public ZombifyingStatusEffect() {
        super(MobEffectCategory.HARMFUL,
                0x274121);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.causeFoodExhaustion(4.0F);
        }
        entity.hurt(entity.level().damageSources().source(TMODamageTypes.ZOMBIFICATION), 1.0F);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k;
        k = 25 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }
}
