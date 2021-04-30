package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import net.minecraft.entity.player.PlayerEntity;

public class ModifyDragonFireballDamagePower extends ValueModifyingPower {
    public ModifyDragonFireballDamagePower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }
}
