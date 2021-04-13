package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.Comparison;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class TMOEntityConditions {
    @SuppressWarnings("unchecked")
    public static void register() {
        register(new ConditionFactory<>(new Identifier(TooManyOrigins.MODID, "nearby_entities"), new SerializableData()
                .add("entity_type", SerializableDataType.ENTITY_TYPE)
                .add("player_box_multiplier", SerializableDataType.FLOAT)
                .add("comparison", SerializableDataType.COMPARISON)
                .add("compare_to", SerializableDataType.INT),
                (data, player) -> {
                    EntityType<?> entityType = (EntityType<?>) data.get("entity_type");
                    Float playerBoxMultiplier = (Float) data.get("player_box_multiplier");
                    int amount = player.world.getOtherEntities(player, player.getBoundingBox().expand(playerBoxMultiplier), entity -> {
                        return entity.getType() == entityType;
                    }).size();
                    Comparison comparison = ((Comparison) data.get("comparison"));
                    int compareTo = data.getInt("compare_to");

                    return comparison.compare(amount, compareTo);
                }));
        register(new ConditionFactory<>(new Identifier(TooManyOrigins.MODID, "block_looking_at"), new SerializableData()
                .add("reach", SerializableDataType.DOUBLE, 5.0D)
                .add("block_condition", SerializableDataType.BLOCK_CONDITION, null),
                (data, entity) -> {
                        ConditionFactory<CachedBlockPosition>.Instance blockCondition = (ConditionFactory<CachedBlockPosition>.Instance) data.get("block_condition");
                        if (entity.raycast(data.getDouble("reach"), 0.0F, false).getType() == HitResult.Type.BLOCK) {
                            BlockPos pos = ((BlockHitResult) entity.raycast(data.getDouble("reach"), 0.0F, false)).getBlockPos();
                            return blockCondition.test(new CachedBlockPosition(entity.world, pos, true));
                    }
                    return false;
                }));
    }

    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
