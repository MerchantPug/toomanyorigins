package net.merchantpug.toomanyorigins.mixin;

import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AreaEffectCloud.class)
public interface AreaEffectCloudEntityAccessor {
    @Accessor("victims")
    Map<Entity, Integer> getAffectedEntities();

    @Accessor("reapplicationDelay")
    int getReapplicationDelay();
}
