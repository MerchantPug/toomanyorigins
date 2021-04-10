package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class ExtraSoulSpeedPower extends Power {
    int modifier;
    public ExtraSoulSpeedPower(PowerType<?> type, PlayerEntity player, int modifier) {
        super(type, player);
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }
}
