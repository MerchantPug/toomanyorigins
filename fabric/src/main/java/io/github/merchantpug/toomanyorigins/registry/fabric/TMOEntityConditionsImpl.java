package io.github.merchantpug.toomanyorigins.registry.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.merchantpug.toomanyorigins.util.RaycastEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;

import java.util.function.Predicate;

public class TMOEntityConditionsImpl {
    public static boolean lookingAtRegistry(Entity entity, ConditionFactory<CachedBlockPosition>.Instance blockCondition, ConditionFactory<LivingEntity>.Instance targetCondition) {
        double baseReach = 4.5D;
        if (entity instanceof PlayerEntity) {
            if (((PlayerEntity) entity).abilities.creativeMode) {
                baseReach = 5.0D;
            }
        }
        double reach;
        reach = ReachEntityAttributes.getReachDistance((LivingEntity) entity, baseReach);
        Vec3d vec3d = entity.getCameraPosVec(0.0F);
        Vec3d vec3d2 = entity.getRotationVec(0.0F);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * reach, vec3d2.y * reach, vec3d2.z * reach);
        Box box = entity.getBoundingBox().stretch(vec3d2).expand(1.0D);
        double d = reach * reach;
        Predicate<Entity> predicate = (entityx) -> !entityx.isSpectator() && entityx.collides();
        EntityHitResult entityHitResult = RaycastEntity.raycast(entity, vec3d, vec3d3, box, predicate, d);
        BlockHitResult blockHitResult = entity.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity));
        if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity) {
            if (targetCondition != null) {
                return targetCondition.test((LivingEntity) entityHitResult.getEntity());
            }
            return false;
        } else if (entityHitResult != null && !(entityHitResult.getEntity() instanceof LivingEntity)) {
            return false;
        } else if (blockHitResult != null) {
            if (blockCondition != null) {
                return blockCondition.test(new CachedBlockPosition(entity.world, blockHitResult.getBlockPos(), true));
            }
        }
        return false;
    }

    public static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
