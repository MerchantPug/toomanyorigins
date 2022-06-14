package com.github.merchantpug.toomanyorigins.mixin;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AreaEffectCloudEntity.class)
public interface AreaEffectCloudEntityAccessor {
    @Accessor
    Map<Entity, Integer> getAffectedEntities();

    @Accessor
    int getReapplicationDelay();
}
