package io.github.merchantpug.toomanyorigins.registry.fabric;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class TMODamageConditionsImpl {
    public static void register(ConditionFactory<Pair<DamageSource, Float>> conditionFactory) {
        Registry.register(ModRegistries.DAMAGE_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
