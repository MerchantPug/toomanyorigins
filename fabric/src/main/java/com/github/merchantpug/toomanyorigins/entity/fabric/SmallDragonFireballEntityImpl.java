package com.github.merchantpug.toomanyorigins.entity.fabric;

import io.github.apace100.origins.component.OriginComponent;
import com.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import net.minecraft.entity.LivingEntity;

public class SmallDragonFireballEntityImpl {

    public static float getModifiedMinRadius(LivingEntity owner, float originalValue) {
        if (OriginComponent.hasPower(owner, ModifyDragonFireballPower.class)) {
            return OriginComponent.getPowers(owner, ModifyDragonFireballPower.class).get(0).getMinRadius();
        }
        return originalValue;
    }

    public static float getModifiedMaxRadius(LivingEntity owner, float originalValue) {
        if (OriginComponent.hasPower(owner, ModifyDragonFireballPower.class)) {
            return OriginComponent.getPowers(owner, ModifyDragonFireballPower.class).get(0).getMaxRadius();
        }
        return originalValue;
    }

    public static int getModifiedDuration(LivingEntity owner, int originalValue) {
        if (OriginComponent.hasPower(owner, ModifyDragonFireballPower.class)) {
            return OriginComponent.getPowers(owner, ModifyDragonFireballPower.class).get(0).getDuration();
        }
        return originalValue;
    }

    public static float getModifiedDamage(LivingEntity owner, float originalValue) {
        if (OriginComponent.hasPower(owner, ModifyDragonFireballPower.class)) {
            return OriginComponent.modify(owner, ModifyDragonFireballPower.class, originalValue);
        }
        return originalValue;
    }
}
