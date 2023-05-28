package net.merchantpug.toomanyorigins.power;

import com.google.auto.service.AutoService;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.power.factory.ModifyDragonFireballPowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.LinkedList;
import java.util.List;

@AutoService(ModifyDragonFireballPowerFactory.class)
public class ModifyDragonFireballPower extends PowerFactory<ModifyDragonFireballPower.Instance> implements ModifyDragonFireballPowerFactory<ModifyDragonFireballPower.Instance> {

    public ModifyDragonFireballPower() {
        super(TooManyOrigins.asResource("modify_dragon_fireball"),
                ModifyDragonFireballPowerFactory.getSerializableData(),
            data -> (type, entity) -> new Instance(type, entity, data));
        allowCondition();
    }

    @Override
    public Class<Instance> getPowerClass() {
        return Instance.class;
    }

    @Override
    public List<?> getDamageModifiers(Instance power, Entity entity) {
        return power.damageModifiers;
    }

    @Override
    public List<?> getMinRadiusModifiers(Instance power, Entity entity) {
        return power.minRadiusModifiers;
    }

    @Override
    public List<?> getMaxRadiusModifiers(Instance power, Entity entity) {
        return power.maxRadiusModifiers;
    }

    @Override
    public List<?> getDurationModifiers(Instance power, Entity entity) {
        return power.durationModifiers;
    }

    @Override
    public SerializableData.Instance getDataFromPower(Instance power) {
        return power.data;
    }

    public static class Instance extends Power {
        private final SerializableData.Instance data;
        private final List<Modifier> damageModifiers = new LinkedList<>();
        private final List<Modifier> minRadiusModifiers = new LinkedList<>();
        private final List<Modifier> maxRadiusModifiers = new LinkedList<>();
        private final List<Modifier> durationModifiers = new LinkedList<>();
    
        public Instance(PowerType<?> type, LivingEntity entity, SerializableData.Instance data) {
            super(type, entity);
            this.data = data;
            data.<Modifier>ifPresent("damage_modifier", damageModifiers::add);
            data.<List<Modifier>>ifPresent("damage_modifiers", damageModifiers::addAll);
            data.<Modifier>ifPresent("min_radius_modifier", minRadiusModifiers::add);
            data.<List<Modifier>>ifPresent("min_radius_modifiers", minRadiusModifiers::addAll);
            data.<Modifier>ifPresent("max_radius_modifier", maxRadiusModifiers::add);
            data.<List<Modifier>>ifPresent("max_radius_modifiers", maxRadiusModifiers::addAll);
            data.<Modifier>ifPresent("duration_modifier", durationModifiers::add);
            data.<List<Modifier>>ifPresent("duration_modifiers", durationModifiers::addAll);
        }

    }
    
}
