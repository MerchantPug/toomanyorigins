package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import net.minecraft.entity.player.PlayerEntity;

public class ModifyDragonFireballPower extends ValueModifyingPower {
    private final float minRadius;
    private final float maxRadius;
    private final int duration;

    public ModifyDragonFireballPower(PowerType<?> type, PlayerEntity player, float minRadius, float maxRadius, int duration) {
        super(type, player);
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.duration = duration;
    }

    public float getMinRadius() {
        return minRadius;
    }

    public float getMaxRadius() {
        return maxRadius;
    }

    public int getDuration() {
        return duration;
    }
}
