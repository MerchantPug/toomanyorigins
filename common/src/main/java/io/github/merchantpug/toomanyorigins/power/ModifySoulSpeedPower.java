package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.player.PlayerEntity;

public class ModifySoulSpeedPower extends ValueModifyingPower {
    public final ConditionFactory<CachedBlockPosition>.Instance blockCondition;

    public ModifySoulSpeedPower(PowerType<?> type, PlayerEntity player, ConditionFactory<CachedBlockPosition>.Instance blockCondition) {
        super(type, player);
        this.blockCondition = blockCondition;
    }
}
