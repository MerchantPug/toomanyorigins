package io.github.merchantpug.toomanyorigins.registry.fabric;


import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Predicate;

public class TMOEntityActionsImpl {
    public static void zombifyVillager(VillagerEntity villagerEntity) {
        ZombieVillagerEntity zombieVillagerEntity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
        if (zombieVillagerEntity != null) {
            zombieVillagerEntity.initialize((ServerWorldAccess) zombieVillagerEntity.world, zombieVillagerEntity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
            zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
            zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
            zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toTag());
            zombieVillagerEntity.setXp(villagerEntity.getExperience());
        }
        villagerEntity.damage(TMODamageSources.zombification((villagerEntity).getAttacker()), Float.MAX_VALUE);
        villagerEntity.world.syncWorldEvent(null, 1026, villagerEntity.getBlockPos(), 0);
    }

    public static void raycastRegistry(Entity entity, ConditionFactory<CachedBlockPosition>.Instance blockCondition, ActionFactory<Triple<World, BlockPos, Direction>>.Instance blockAction, ConditionFactory<LivingEntity>.Instance targetCondition, ActionFactory<Entity>.Instance targetAction, ActionFactory<Entity>.Instance selfAction) {
        if (entity instanceof LivingEntity && !entity.world.isClient()) {
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
            EntityHitResult entityHitResult = ProjectileUtil.raycast(entity, vec3d, vec3d3, box, predicate, d);
            BlockHitResult blockHitResult = entity.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity));
            if (entityHitResult != null && entityHitResult.getEntity() instanceof LivingEntity && entityHitResult.getType() == HitResult.Type.ENTITY) {
                if (targetAction != null) {
                    boolean entityCondition = targetCondition == null || targetCondition.test((LivingEntity) entityHitResult.getEntity());
                    if (entityCondition) {
                        targetAction.accept(entityHitResult.getEntity());
                        if (selfAction != null) {
                            selfAction.accept(entity);
                        }
                    }
                }
            } else if (blockHitResult != null && blockHitResult.getType() == HitResult.Type.BLOCK) {
                if (blockAction != null) {
                    boolean blockTargetCondition = blockCondition == null || blockCondition.test(new CachedBlockPosition(entity.world, blockHitResult.getBlockPos(), true));
                    if (blockTargetCondition) {
                        blockAction.accept(Triple.of(entity.world, blockHitResult.getBlockPos(), Direction.UP));
                        if (selfAction != null) {
                            selfAction.accept(entity);
                        }
                    }
                }
            }
        }
    }

    public static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
