package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import io.github.merchantpug.toomanyorigins.util.RaycastEntity;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Predicate;

public class TMOEntityActionsImpl {
    public static void register(ActionFactory<Entity> actionFactory) {
        ModRegistriesArchitectury.ENTITY_ACTION.register(actionFactory.getSerializerId(), () -> actionFactory);
    }

    public static void zombifyVillager(VillagerEntity villagerEntity) {
        if (!villagerEntity.getEntityWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld)villagerEntity.getEntityWorld();
            ZombieVillagerEntity zombievillagerentity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
            zombievillagerentity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombievillagerentity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
            zombievillagerentity.setVillagerData(villagerEntity.getVillagerData());
            zombievillagerentity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
            zombievillagerentity.setOfferData(villagerEntity.getOffers().toTag());
            zombievillagerentity.setXp(villagerEntity.getExperience());
            ForgeEventFactory.onLivingConvert(villagerEntity, zombievillagerentity);
            if (!villagerEntity.isSilent()) {
                serverWorld.syncWorldEvent(null, 1026, villagerEntity.getBlockPos(), 0);
            }
        }
    }

    public static void raycastRegistry(Entity entity, ConditionFactory<CachedBlockPosition>.Instance blockCondition, ActionFactory<Triple<World, BlockPos, Direction>>.Instance blockAction, ConditionFactory<LivingEntity>.Instance targetCondition, ActionFactory<Entity>.Instance targetAction, ActionFactory<Entity>.Instance selfAction) {
        if (entity instanceof LivingEntity && !entity.world.isClient()) {
            double reach = ((LivingEntity)entity).getAttributeValue(ForgeMod.REACH_DISTANCE.get());
            Vec3d vec3d = entity.getCameraPosVec(0.0F);
            Vec3d vec3d2 = entity.getRotationVec(0.0F);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * reach, vec3d2.y * reach, vec3d2.z * reach);
            Box box = entity.getBoundingBox().stretch(vec3d2).expand(1.0D);
            double d = reach * reach;
            Predicate<Entity> predicate = (entityx) -> !entityx.isSpectator() && entityx.collides();
            EntityHitResult entityHitResult = RaycastEntity.raycast(entity, vec3d, vec3d3, box, predicate, d);
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
}
