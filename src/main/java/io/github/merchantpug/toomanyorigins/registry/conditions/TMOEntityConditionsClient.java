package io.github.merchantpug.toomanyorigins.registry.conditions;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class TMOEntityConditionsClient {
    @Environment(EnvType.CLIENT)
    public static void register() {
        register(new ConditionFactory<>(new Identifier(TooManyOrigins.MODID, "block_looking_at"), new SerializableData()
                .add("reach", SerializableDataType.DOUBLE, 4.0D)
                .add("block_condition", SerializableDataType.BLOCK_CONDITION, null),
                (data, entity) -> {
                    if(entity instanceof ServerPlayerEntity) {
                        ConditionFactory<CachedBlockPosition>.Instance blockCondition = (ConditionFactory<CachedBlockPosition>.Instance)data.get("block_condition");
                        if (entity.raycast(data.getDouble("reach"), 0.0F, false).getType() == HitResult.Type.BLOCK) {
                            BlockPos pos = ((BlockHitResult)entity.raycast(data.getDouble("reach"), 0.0F, false)).getBlockPos();
                            return blockCondition.test(new CachedBlockPosition(entity.world, pos, true));
                        }
                    }
                    if(entity instanceof ClientPlayerEntity) {
                        ConditionFactory<CachedBlockPosition>.Instance blockCondition = (ConditionFactory<CachedBlockPosition>.Instance)data.get("block_condition");
                        if (MinecraftClient.getInstance().crosshairTarget.getType() == HitResult.Type.BLOCK) {
                            BlockPos pos = ((BlockHitResult) MinecraftClient.getInstance().crosshairTarget).getBlockPos();
                            return blockCondition.test(new CachedBlockPosition(entity.world, pos, true));
                        }
                    }
                    return false;
                }));
    }

    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ModRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
