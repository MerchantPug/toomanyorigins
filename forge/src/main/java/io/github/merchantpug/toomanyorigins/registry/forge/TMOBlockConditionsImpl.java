package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import net.minecraft.block.pattern.CachedBlockPosition;

public class TMOBlockConditionsImpl {
    public static void register(ConditionFactory<CachedBlockPosition> conditionFactory) {
        ModRegistriesArchitectury.BLOCK_CONDITION.register(conditionFactory.getSerializerId(), () -> conditionFactory);
    }
}
