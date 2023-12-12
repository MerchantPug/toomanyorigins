package net.merchantpug.toomanyorigins.power.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.BiFunction;

public interface ModifyDragonFireballPowerFactory<P> extends SpecialPowerFactory<P> {

    static SerializableData getSerializableData() {
        return new SerializableData()
                .add("damage", SerializableDataTypes.FLOAT, null)
                .add("damage_modifier", Services.PLATFORM.getModifierDataType(), null)
                .add("damage_modifiers", Services.PLATFORM.getModifiersDataType(), null)
                .add("min_radius", SerializableDataTypes.FLOAT, null)
                .add("min_radius_modifier", Services.PLATFORM.getModifierDataType(), null)
                .add("min_radius_modifiers", Services.PLATFORM.getModifiersDataType(), null)
                .add("max_radius", SerializableDataTypes.FLOAT, null)
                .add("max_radius_modifier", Services.PLATFORM.getModifierDataType(), null)
                .add("max_radius_modifiers", Services.PLATFORM.getModifiersDataType(), null)
                .add("duration", SerializableDataTypes.INT, null)
                .add("duration_modifier", Services.PLATFORM.getModifierDataType(), null)
                .add("duration_modifiers", Services.PLATFORM.getModifiersDataType(), null);
    }

    default float modifyDamage(Entity entity, float originalValue) {
        return modify(entity, "damage", this::getDamageModifiers, originalValue);
    }

    default float modifyMinRadius(Entity entity, float originalValue) {
        return modify(entity, "min_radius", this::getMinRadiusModifiers, originalValue);
    }

    default float modifyMaxRadius(Entity entity, float originalValue) {
        return modify(entity, "max_radius", this::getMaxRadiusModifiers, originalValue);
    }

    default float modifyDuration(Entity entity, float originalValue) {
        return modify(entity, "duration", this::getDurationModifiers, originalValue);
    }

    default float modify(Entity entity, String setterKey, BiFunction<P, Entity, List<?>> modifiers, float originalValue) {
        if (!(entity instanceof LivingEntity living))
            return originalValue;
        List<P> powers = Services.POWER.getPowers(living, this);
        float value = powers.stream().filter(p -> getDataFromPower(p).isPresent(setterKey)).map(p -> (float)(getDataFromPower(p).get(setterKey))).max(Float::compare).orElse(originalValue);
        List<?> modifierList = powers.stream().map(p -> modifiers.apply(p, living)).flatMap(List::stream).toList();
        return (float) Services.PLATFORM.applyModifiers(living, modifierList, value);
    }

    List<?> getDamageModifiers(P power, Entity entity);

    List<?> getMinRadiusModifiers(P power, Entity entity);

    List<?> getMaxRadiusModifiers(P power, Entity entity);

    List<?> getDurationModifiers(P power, Entity entity);

}
