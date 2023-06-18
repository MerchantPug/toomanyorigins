package net.merchantpug.toomanyorigins.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Deprecated
public class EndFireStatusEffect extends InstantenousMobEffect {

    public EndFireStatusEffect() {
        super(MobEffectCategory.HARMFUL,
                0xc700c2);
    }

    @Override
    public void applyInstantenousEffect(Entity source, Entity attacker, LivingEntity target, int amplifier, double proximity) {
        int j = (int)(proximity * (double)(6 << amplifier) + 0.5D);
        if (source == null) {
            target.hurt(DamageSource.MAGIC, (float)j);
        } else {
            target.hurt(DamageSource.indirectMagic(source, attacker), (float)j);
        }
    }
}
