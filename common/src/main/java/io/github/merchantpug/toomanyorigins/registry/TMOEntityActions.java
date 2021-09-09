package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.util.TMODataTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;

public class TMOEntityActions {
    @SuppressWarnings("unchecked")
    public static void register() {
        register(new ActionFactory<>(TooManyOrigins.identifier("zombify_villager"), new SerializableData(),
                (data, entity) -> {
                    if (entity instanceof VillagerEntity) {
                        VillagerEntity villagerEntity = (VillagerEntity)entity;
                        zombifyVillager(villagerEntity);
                    }
                }));
        register(new ActionFactory<>(TooManyOrigins.identifier("raycast"), new SerializableData()
                .add("block_action", SerializableDataType.BLOCK_ACTION, null)
                .add("block_condition", SerializableDataType.BLOCK_CONDITION, null)
                .add("target_action", SerializableDataType.ENTITY_ACTION, null)
                .add("target_condition", SerializableDataType.ENTITY_CONDITION, null)
                .add("self_action", SerializableDataType.ENTITY_ACTION, null),
                (data, entity) -> {
                    raycastRegistry(entity, data.get("block_condition"), data.get("block_action"), data.get("target_condition"), data.get("target_action"), data.get("self_action"));
                }));
        register(new ActionFactory<>(TooManyOrigins.identifier("explode"), new SerializableData()
                .add("radius", SerializableDataType.FLOAT)
                .add("behavior", TMODataTypes.EXPLOSION_BEHAVIOR)
                .add("use_charged", SerializableDataType.BOOLEAN, false)
                .add("spawn_effect_cloud", SerializableDataType.BOOLEAN, false)
                .add("source", SerializableDataType.DAMAGE_SOURCE, null)
                .add("amount", SerializableDataType.FLOAT, 0.0F),
                (data, entity) -> {
                    if (entity instanceof LivingEntity && !entity.world.isClient()) {
                        boolean useCharged = data.getBoolean("use_charged");
                        boolean tmoCharged;
                        boolean cursedCharged;
                        float f = 1.0F;
                        if (useCharged) {
                            tmoCharged = ((LivingEntity)entity).hasStatusEffect(TMOEffects.CHARGED);
                            cursedCharged = ((LivingEntity)entity).hasStatusEffect(Registry.STATUS_EFFECT.get(new Identifier("cursedorigins", "charged")));
                            if (tmoCharged) {
                                ((LivingEntity) entity).removeStatusEffect(TMOEffects.CHARGED);
                                f = 2.0F;
                            }
                            if (cursedCharged) {
                                ((LivingEntity) entity).removeStatusEffect(Registry.STATUS_EFFECT.get(new Identifier("cursedorigins", "charged")));
                                f = 2.0F;
                            }
                        }

                        entity.world.createExplosion(entity, DamageSource.explosion((LivingEntity) entity), null, entity.getX(), entity.getY(), entity.getZ(), data.getFloat("radius") * f, false, (Explosion.DestructionType) data.get("behavior"));

                        Collection<StatusEffectInstance> collection = ((LivingEntity) entity).getStatusEffects();
                        if (!collection.isEmpty() && data.getBoolean("spawn_effect_cloud")) {
                            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
                            areaEffectCloudEntity.setRadius(2.5F);
                            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
                            areaEffectCloudEntity.setWaitTime(10);
                            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
                            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());

                            for (StatusEffectInstance statusEffectInstance : collection) {
                                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
                            }
                            entity.world.spawnEntity(areaEffectCloudEntity);
                        }
                        DamageSource source = (DamageSource) data.get("source");
                        float amount = data.getFloat("amount");
                        if (source != null && amount != 0.0F) {
                            entity.damage(source, amount);
                        }
                    }
                }));
        register(new ActionFactory<>(TooManyOrigins.identifier("swing_hand"), new SerializableData()
                .add("hand", TMODataTypes.HAND),
                (data, entity) -> {
                    if (entity instanceof PlayerEntity && !entity.world.isClient) {
                        ((PlayerEntity) entity).swingHand((Hand) data.get("hand"), true);
                    }
                }));
    }

    @ExpectPlatform
    public static void raycastRegistry(Entity entity, ConditionFactory<CachedBlockPosition>.Instance blockCondition, ActionFactory<Triple<World, BlockPos, Direction>>.Instance blockAction, ConditionFactory<LivingEntity>.Instance targetCondition, ActionFactory<Entity>.Instance targetAction, ActionFactory<Entity>.Instance selfAction) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void zombifyVillager(VillagerEntity villagerEntity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void register(ActionFactory<Entity> actionFactory) {
        throw new AssertionError();
    }
}