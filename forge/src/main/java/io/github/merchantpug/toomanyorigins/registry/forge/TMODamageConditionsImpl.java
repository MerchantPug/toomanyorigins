package io.github.merchantpug.toomanyorigins.registry.forge;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Pair;

public class TMODamageConditionsImpl {
    public static void register(ConditionFactory<Pair<DamageSource, Float>> conditionFactory) {
        ModRegistriesArchitectury.DAMAGE_CONDITION.register(conditionFactory.getSerializerId(), () -> conditionFactory);
    }
}
