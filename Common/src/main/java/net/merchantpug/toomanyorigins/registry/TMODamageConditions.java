package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.apugli.condition.factory.IConditionFactory;
import net.merchantpug.toomanyorigins.condition.damage.IsExplosiveCondition;
import net.merchantpug.toomanyorigins.platform.Services;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;

public class TMODamageConditions {

    public static void registerAll() {
        register("explosive", new IsExplosiveCondition());
    }

    private static void register(String name, IConditionFactory<Tuple<DamageSource, Float>> factory) {
        Services.PLATFORM.registerDamage(name, factory);
    }
}
