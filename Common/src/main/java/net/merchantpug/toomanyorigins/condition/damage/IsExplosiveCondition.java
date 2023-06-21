package net.merchantpug.toomanyorigins.condition.damage;

import io.github.apace100.calio.data.SerializableData;
import net.merchantpug.apugli.condition.factory.IConditionFactory;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;

public class IsExplosiveCondition implements IConditionFactory<Tuple<DamageSource, Float>> {
    
    @Override
    public boolean check(SerializableData.Instance data, Tuple<DamageSource, Float> damage) {
        return damage.getA().isExplosion();
    }
    
}
