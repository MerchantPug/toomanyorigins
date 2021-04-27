package io.github.merchantpug.toomanyorigins.registry;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.Comparison;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.networking.packet.LightUpBlockPacket;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;

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
    }

    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
