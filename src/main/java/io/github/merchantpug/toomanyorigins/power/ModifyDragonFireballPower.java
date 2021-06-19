package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import net.minecraft.entity.LivingEntity;

public class ModifyDragonFireballPower extends ValueModifyingPower {
    private final float minRadius;
    private final float maxRadius;
    private final int duration;

    public ModifyDragonFireballPower(PowerType<?> type, LivingEntity entity, float minRadius, float maxRadius, int duration) {
        super(type, entity);
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
