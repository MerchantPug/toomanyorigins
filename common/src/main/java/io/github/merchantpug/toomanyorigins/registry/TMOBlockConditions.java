package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.block.pattern.CachedBlockPosition;

public class TMOBlockConditions {
    @SuppressWarnings("unchecked")
    public static void register() {
        register(new ConditionFactory<>(TooManyOrigins.identifier("air"), new SerializableData(),
                (data, block) -> block.getBlockState().isAir()));
    }

    @ExpectPlatform
    private static void register(ConditionFactory<CachedBlockPosition> conditionFactory) {
        throw new AssertionError();
    }
}