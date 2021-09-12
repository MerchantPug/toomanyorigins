package io.github.merchantpug.toomanyorigins.power.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.util.RaycastEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.explosion.Explosion;

import java.util.function.Predicate;

public class RocketJumpPowerImpl {
    boolean isSuccessful;

    public static boolean handleRocketJump(PlayerEntity player, DamageSource source, float amount, double speed, boolean useCharged) {
        if (!player.world.isClient()) {
            double baseReach = 4.5D;
            if (player instanceof PlayerEntity) {
                if (((PlayerEntity) player).abilities.creativeMode) {
                    baseReach = 5.0D;
                }
            }
            double reach;
            if (FabricLoader.getInstance().isModLoaded("reach-entity-attributes")) {
                reach = ReachEntityAttributes.getReachDistance((LivingEntity) player, baseReach);
            } else {
                reach = baseReach;
            }
            Vec3d vec3d = player.getCameraPosVec(0.0F);
            Vec3d vec3d2 = player.getRotationVec(0.0F);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * reach, vec3d2.y * reach, vec3d2.z * reach);
            Box box = player.getBoundingBox().stretch(vec3d2).expand(1.0D);
            double entityReach = reach * reach;
            Predicate<Entity> predicate = (entityx) -> !entityx.isSpectator() && entityx.collides();
            EntityHitResult entityHitResult = RaycastEntity.raycast(player, vec3d, vec3d3, box, predicate, entityReach);
            BlockHitResult blockHitResult = player.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));

            boolean tmoCharged;
            boolean cursedCharged;
            tmoCharged = ((LivingEntity) player).hasStatusEffect(TMOEffects.CHARGED);
            if (FabricLoader.getInstance().isModLoaded("cursedorigins")) {
                cursedCharged = ((LivingEntity) player).hasStatusEffect(Registry.STATUS_EFFECT.get(new Identifier("cursedorigins", "charged")));
            } else cursedCharged = false;
            double d = (tmoCharged || cursedCharged) && useCharged ? 1.5D : 1.0D;
            float e = (tmoCharged || cursedCharged) && useCharged ? 2.0F : 1.5F;
            if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity && entityHitResult.getType() == HitResult.Type.ENTITY) {
                if (source != null && amount != 0.0F) {
                    player.damage(source, amount);
                }
                float f = MathHelper.sin(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
                float g = MathHelper.sin(player.pitch * 0.017453292F);
                float h = -MathHelper.cos(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);

                player.world.createExplosion(player, TMODamageSources.jumpExplosion((LivingEntity) player), null, blockHitResult.getPos().getX(), blockHitResult.getPos().getY(), blockHitResult.getPos().getZ(), e, false, Explosion.DestructionType.NONE);
                player.addVelocity(f * speed * d, g * speed * d, h * speed * d);
                player.velocityModified = true;
                return true;
            } else if (blockHitResult != null && blockHitResult.getType() == HitResult.Type.BLOCK) {
                if (source != null && amount != 0.0F) {
                    player.damage(source, amount);
                }
                float f = MathHelper.sin(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
                float g = MathHelper.sin(player.pitch * 0.017453292F);
                float h = -MathHelper.cos(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);

                player.world.createExplosion(player, TMODamageSources.jumpExplosion((LivingEntity) player), null, blockHitResult.getPos().getX(), blockHitResult.getPos().getY(), blockHitResult.getPos().getZ(), e, false, Explosion.DestructionType.NONE);
                player.addVelocity(f * speed * d, g * speed * d, h * speed * d);
                player.velocityModified = true;
                return true;
            }
        }
        return false;
    }
}
