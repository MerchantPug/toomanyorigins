package net.merchantpug.toomanyorigins.content.legacy.effect;

import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.merchantpug.toomanyorigins.registry.TMODamageSources;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
        if (LegacyContentRegistry.isZombifyingEffectEnabled()) {
            entity.removeEffect(TMOEffects.ZOMBIFYING.get());
            if (!entity.level.isClientSide) {
                entity.sendSystemMessage(Component.translatable("toomanyorigins.content.disabled_message", LegacyContentRegistry.ZOMBIFYING).withStyle(ChatFormatting.RED));
            }
            return;
        }
        if (entity instanceof Player player) {
            player.causeFoodExhaustion(4.0F);
        }
        entity.hurt(TMODamageSources.ZOMBIFICATION, 1.0F);
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
