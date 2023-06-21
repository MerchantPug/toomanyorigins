package net.merchantpug.toomanyorigins.power.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.function.BiFunction;

public interface ModifyDragonFireballPowerFactory<P> extends SpecialPowerFactory<P> {

    static SerializableData getSerializableData() {
        return new SerializableData()
                .add("damage", SerializableDataTypes.FLOAT, null)
                .add("damage_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                .add("damage_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null)
                .add("min_radius", SerializableDataTypes.FLOAT, null)
                .add("min_radius_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                .add("min_radius_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null)
                .add("max_radius", SerializableDataTypes.FLOAT, null)
                .add("max_radius_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                .add("max_radius_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null)
                .add("duration", SerializableDataTypes.INT, null)
                .add("duration_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                .add("duration_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null);
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

    default float modify(Entity entity, String setterKey, BiFunction<P, Entity, List<AttributeModifier>> modifiers, float originalValue) {
        if (!(entity instanceof LivingEntity living))
            return originalValue;
        List<P> powers = Services.POWER.getPowers(living, this);
        float value = powers.stream().filter(p -> getDataFromPower(p).isPresent(setterKey)).map(p -> (float)(getDataFromPower(p).get(setterKey))).max(Float::compare).orElse(originalValue);
        List<AttributeModifier> modifierList = powers.stream().map(p -> modifiers.apply(p, living)).flatMap(List::stream).toList();
        return (float) Services.PLATFORM.applyModifiers(modifierList, value);
    }

    List<AttributeModifier> getDamageModifiers(P power, Entity entity);

    List<AttributeModifier> getMinRadiusModifiers(P power, Entity entity);

    List<AttributeModifier> getMaxRadiusModifiers(P power, Entity entity);

    List<AttributeModifier> getDurationModifiers(P power, Entity entity);

}
