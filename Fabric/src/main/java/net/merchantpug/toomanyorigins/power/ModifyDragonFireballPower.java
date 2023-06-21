package net.merchantpug.toomanyorigins.power;

import com.google.auto.service.AutoService;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.power.factory.ModifyDragonFireballPowerFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

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
    public List<AttributeModifier> getDamageModifiers(Instance power, Entity entity) {
        return power.damageModifiers;
    }

    @Override
    public List<AttributeModifier> getMinRadiusModifiers(Instance power, Entity entity) {
        return power.minRadiusModifiers;
    }

    @Override
    public List<AttributeModifier> getMaxRadiusModifiers(Instance power, Entity entity) {
        return power.maxRadiusModifiers;
    }

    @Override
    public List<AttributeModifier> getDurationModifiers(Instance power, Entity entity) {
        return power.durationModifiers;
    }

    @Override
    public SerializableData.Instance getDataFromPower(Instance power) {
        return power.data;
    }

    public static class Instance extends Power {
        private final SerializableData.Instance data;
        private final List<AttributeModifier> damageModifiers = new LinkedList<>();
        private final List<AttributeModifier> minRadiusModifiers = new LinkedList<>();
        private final List<AttributeModifier> maxRadiusModifiers = new LinkedList<>();
        private final List<AttributeModifier> durationModifiers = new LinkedList<>();
    
        public Instance(PowerType<?> type, LivingEntity entity, SerializableData.Instance data) {
            super(type, entity);
            this.data = data;
            data.<AttributeModifier>ifPresent("damage_modifier", damageModifiers::add);
            data.<List<AttributeModifier>>ifPresent("damage_modifiers", damageModifiers::addAll);
            data.<AttributeModifier>ifPresent("min_radius_modifier", minRadiusModifiers::add);
            data.<List<AttributeModifier>>ifPresent("min_radius_modifiers", minRadiusModifiers::addAll);
            data.<AttributeModifier>ifPresent("max_radius_modifier", maxRadiusModifiers::add);
            data.<List<AttributeModifier>>ifPresent("max_radius_modifiers", maxRadiusModifiers::addAll);
            data.<AttributeModifier>ifPresent("duration_modifier", durationModifiers::add);
            data.<List<AttributeModifier>>ifPresent("duration_modifiers", durationModifiers::addAll);
        }

    }
    
}
