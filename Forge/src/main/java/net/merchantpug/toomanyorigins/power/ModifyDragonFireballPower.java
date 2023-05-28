package net.merchantpug.toomanyorigins.power;

import com.google.auto.service.AutoService;
import io.github.apace100.calio.data.SerializableData;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.merchantpug.toomanyorigins.power.configuration.ModifyDragonFireballConfiguration;
import net.merchantpug.toomanyorigins.power.factory.ModifyDragonFireballPowerFactory;
import net.minecraft.world.entity.Entity;

import java.util.List;

@AutoService(ModifyDragonFireballPowerFactory.class)
public class ModifyDragonFireballPower extends PowerFactory<ModifyDragonFireballConfiguration> implements ModifyDragonFireballPowerFactory<ConfiguredPower<ModifyDragonFireballConfiguration, ?>> {

    public ModifyDragonFireballPower() {
        super(ModifyDragonFireballPowerFactory.getSerializableData().xmap(
                ModifyDragonFireballConfiguration::new,
                ModifyDragonFireballConfiguration::data
        ).codec());
    }

    @Override
    public List<?> getDamageModifiers(ConfiguredPower<ModifyDragonFireballConfiguration, ?> power, Entity entity) {
        return power.getConfiguration().damageModifiers();
    }

    @Override
    public List<?> getMinRadiusModifiers(ConfiguredPower<ModifyDragonFireballConfiguration, ?> power, Entity entity) {
        return power.getConfiguration().minRadiusModifiers();
    }

    @Override
    public List<?> getMaxRadiusModifiers(ConfiguredPower<ModifyDragonFireballConfiguration, ?> power, Entity entity) {
        return power.getConfiguration().maxRadiusModifiers();
    }

    @Override
    public List<?> getDurationModifiers(ConfiguredPower<ModifyDragonFireballConfiguration, ?> power, Entity entity) {
        return power.getConfiguration().durationModifiers();
    }

    @Override
    public SerializableData.Instance getDataFromPower(ConfiguredPower<ModifyDragonFireballConfiguration, ?> power) {
        return power.getConfiguration().data();
    }

}
