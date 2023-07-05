package net.merchantpug.toomanyorigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.calio.data.SerializableData;
import net.merchantpug.toomanyorigins.power.factory.SimplePowerFactory;
import net.minecraft.world.entity.LivingEntity;

public class CancelOutSprintDisableOnHitPower extends Power {

    public CancelOutSprintDisableOnHitPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static class Factory extends SimplePowerFactory<CancelOutSprintDisableOnHitPower> {


        public Factory() {
            super("cancel_out_sprint_disable_on_hit",
                    new SerializableData(),
                    data -> CancelOutSprintDisableOnHitPower::new);
            allowCondition();
        }

        @Override
        public Class<CancelOutSprintDisableOnHitPower> getPowerClass() {
            return CancelOutSprintDisableOnHitPower.class;
        }
    }

}
