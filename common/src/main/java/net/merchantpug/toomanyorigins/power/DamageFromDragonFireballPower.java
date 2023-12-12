package net.merchantpug.toomanyorigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.merchantpug.toomanyorigins.power.factory.SimplePowerFactory;
import net.minecraft.world.entity.LivingEntity;

public class DamageFromDragonFireballPower extends Power {
    public DamageFromDragonFireballPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static class Factory extends SimplePowerFactory<DamageFromDragonFireballPower> {

        public Factory() {
            super(
                    "take_damage_from_ender_dragon_fireball",
                    new SerializableData(),
                    data -> DamageFromDragonFireballPower::new
            );
            this.allowCondition();
        }

        @Override
        public Class<DamageFromDragonFireballPower> getPowerClass() {
            return DamageFromDragonFireballPower.class;
        }
    }

}
