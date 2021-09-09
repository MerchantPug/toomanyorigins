package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Pair;

public class TMODamageConditions {
    public static void register() {
        register(new ConditionFactory<>(TooManyOrigins.identifier("explosive"), new SerializableData(),
                (data, dmg) -> dmg.getLeft().isExplosive()));
        register(new ConditionFactory<>(TooManyOrigins.identifier("magic"), new SerializableData(),
                (data, dmg) -> dmg.getLeft().getMagic()));
    }

    @ExpectPlatform
    public static void register(ConditionFactory<Pair<DamageSource, Float>> conditionFactory) {
        throw new AssertionError();
    }
}