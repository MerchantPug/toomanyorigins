package com.github.merchantpug.toomanyorigins.content.effect.legacy;

import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import com.github.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ZombifyingStatusEffect extends StatusEffect {
    public ZombifyingStatusEffect() {
        super(StatusEffectCategory.HARMFUL,
                0x274121);
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.ZOMBIFYING)) {
            entity.removeStatusEffect(TMOEffects.ZOMBIFYING);
            if (!entity.world.isClient) {
                entity.sendMessage(Text.translatable("toomanyorigins.content.disabled_message", LegacyContentRegistry.ZOMBIFYING).formatted(Formatting.RED));
            }
            return;
        }
        if (entity instanceof PlayerEntity) {
            ((PlayerEntity) entity).addExhaustion(4.0F);
        }
        entity.damage(TMODamageSources.ZOMBIFICATION, 1.0F);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.ZOMBIFYING))
            return true;
        int k;
        k = 25 >> amplifier;
        if (k > 0)
            return duration % k == 0;
        else
            return true;
    }
}
