package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.CooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.toomanyorigins.mixin.CooldownPowerAccessor;
import net.minecraft.entity.player.PlayerEntity;

public class InvertedCooldownPower extends CooldownPower {

    public InvertedCooldownPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender) {
        super(type, player, cooldownDuration, hudRender);
    }

    @Override
    public float getProgress() {
        float time = player.getEntityWorld().getTime() - ((CooldownPowerAccessor)this).getLastUseTime();
        return Math.min(1F, Math.max(1F - (time / (float)cooldownDuration), 0F));
    }
}
