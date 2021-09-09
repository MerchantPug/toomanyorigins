package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.util.Comparison;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.util.TMODataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Predicate;

public class TMOEntityConditions {
    @SuppressWarnings("unchecked")
    public static void register() {
        register(new ConditionFactory<>(TooManyOrigins.identifier("entity_in_radius"), new SerializableData()
                .add("condition", SerializableDataType.ENTITY_CONDITION)
                .add("radius", SerializableDataType.DOUBLE)
                .add("compare_to", SerializableDataType.INT, 1)
                .add("comparison", SerializableDataType.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL),
                (data, entity) -> {
                    Predicate<LivingEntity> entityCondition = ((ConditionFactory<LivingEntity>.Instance)data.get("condition"));
                    int stopAt = -1;
                    Comparison comparison = ((Comparison)data.get("comparison"));
                    int compareTo = data.getInt("compare_to");
                    switch(comparison) {
                        case EQUAL:
                        case LESS_THAN_OR_EQUAL:
                        case GREATER_THAN:
                            stopAt = compareTo + 1;
                            break;
                        case LESS_THAN:
                        case GREATER_THAN_OR_EQUAL:
                            stopAt = compareTo;
                    }
                    int count = 0;
                    for (Entity target : entity.world.getOtherEntities(entity, entity.getBoundingBox().expand(data.getDouble("radius")))) {
                        if (target instanceof LivingEntity) {
                            if (entityCondition.test((LivingEntity)target)) {
                                count++;
                                if (count == stopAt) {
                                    break;
                                }
                            }
                        }
                    }
                    return comparison.compare(count, compareTo);
                }));
        register(new ConditionFactory<>(TooManyOrigins.identifier("entity_group"), new SerializableData()
                .add("group", TMODataTypes.TMO_ENTITY_GROUP),
                (data, entity) ->
                        entity.getGroup() == data.get("group")));
        register(new ConditionFactory<>(TooManyOrigins.identifier("can_have_effect"), new SerializableData()
                .add("effect", SerializableDataType.STATUS_EFFECT),
                (data, entity) -> {
                    StatusEffect effect = (StatusEffect)data.get("effect");
                    StatusEffectInstance instance = new StatusEffectInstance(effect);
                    return entity.canHaveStatusEffect(instance);
                }));
        register(new ConditionFactory<>(TooManyOrigins.identifier("looking_at"), new SerializableData()
                .add("block_condition", SerializableDataType.BLOCK_CONDITION, null)
                .add("target_condition", SerializableDataType.ENTITY_CONDITION, null),
                (data, entity) -> lookingAtRegistry(entity, (ConditionFactory<CachedBlockPosition>.Instance)data.get("block_condition"), (ConditionFactory<LivingEntity>.Instance)data.get("target_condition"))));
        register(new ConditionFactory<>(TooManyOrigins.identifier("structure"), new SerializableData()
                .add("structure", SerializableDataType.IDENTIFIER),
                (data, entity) -> {
                    if (entity.getEntityWorld() instanceof ServerWorld) {
                        StructureFeature<?> structure = Registry.STRUCTURE_FEATURE.get(data.getId("structure"));
                        BlockPos structurePos = ((ServerWorld)entity.getEntityWorld()).locateStructure(structure, entity.getBlockPos(), 12, false);
                        if (structurePos != null) {
                            ChunkPos structureChunkPos = new ChunkPos(structurePos.getX() >> 4, structurePos.getZ() >> 4);
                            StructureStart<?> structureStart = ((ServerWorld)entity.getEntityWorld()).getStructureAccessor().getStructureStart(ChunkSectionPos.from(structureChunkPos, 0), structure, entity.world.getChunk(structurePos));
                            StructurePiece[] structurePieces = (StructurePiece[])structureStart.getChildren().toArray();
                            for (StructurePiece structurePiece : structurePieces) {
                                BlockBox box = structurePiece.getBoundingBox();
                                if (entity.getBoundingBox().intersects(Box.from(box))) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                }));
        register(new ConditionFactory<>(TooManyOrigins.identifier("join_invulnerability_ticks"), new SerializableData()
                .add("compare_to", SerializableDataType.INT)
                .add("comparison", SerializableDataType.COMPARISON, Comparison.GREATER_THAN_OR_EQUAL),
                (data, entity) -> {
                    Comparison comparison = ((Comparison)data.get("comparison"));
                    int compareTo = data.getInt("compare_to");
                    if (entity instanceof ServerPlayerEntity && !entity.world.isClient()) {
                        return comparison.compare(((ServerPlayerEntity)entity).joinInvulnerabilityTicks, compareTo);
                    }
                    return false;
                }));
    }

    @ExpectPlatform
    private static boolean lookingAtRegistry(Entity entity, ConditionFactory<CachedBlockPosition>.Instance blockCondition, ConditionFactory<LivingEntity>.Instance targetCondition) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        throw new AssertionError();
    }
}
