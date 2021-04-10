package io.github.merchantpug.toomanyorigins.registry.conditions;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.Comparison;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TMOEntityConditions {
    public static void register() {
        register(new ConditionFactory<>(new Identifier(TooManyOrigins.MODID, "nearby_entities"), new SerializableData()
                .add("entity_type", SerializableDataType.ENTITY_TYPE)
                .add("player_box_multiplier", SerializableDataType.FLOAT)
                .add("comparison", SerializableDataType.COMPARISON)
                .add("compare_to", SerializableDataType.INT),
                (data, player) -> {
                    EntityType<?> entityType = (EntityType<?>)data.get("entity_type");
                    Float playerBoxMultiplier = (Float)data.get("player_box_multiplier");
                    int amount = player.world.getOtherEntities(player, player.getBoundingBox().expand(playerBoxMultiplier), entity -> {
                        return entity.getType() == entityType;
                    }).size();
                    Comparison comparison = ((Comparison)data.get("comparison"));
                    int compareTo = data.getInt("compare_to");

                    return comparison.compare(amount, compareTo);
                }));
    }

    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
