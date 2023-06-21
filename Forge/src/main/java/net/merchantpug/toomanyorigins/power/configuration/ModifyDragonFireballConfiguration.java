package net.merchantpug.toomanyorigins.power.configuration;

import io.github.apace100.calio.data.SerializableData;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.List;

public record ModifyDragonFireballConfiguration(SerializableData.Instance data) implements IDynamicFeatureConfiguration {

    public List<AttributeModifier> damageModifiers() {
        List<AttributeModifier> modifiers = new ArrayList<>();
        data.<List<AttributeModifier>>ifPresent("damage_modifiers", modifiers::addAll);
        data.<AttributeModifier>ifPresent("damage_modifier", modifiers::add);
        return modifiers;
    }

    public List<AttributeModifier> minRadiusModifiers() {
        List<AttributeModifier> modifiers = new ArrayList<>();
        data.<List<AttributeModifier>>ifPresent("min_radius_modifiers", modifiers::addAll);
        data.<AttributeModifier>ifPresent("min_radius_modifier", modifiers::add);
        return modifiers;
    }

    public List<AttributeModifier> maxRadiusModifiers() {
        List<AttributeModifier> modifiers = new ArrayList<>();
        data.<List<AttributeModifier>>ifPresent("max_radius_modifiers", modifiers::addAll);
        data.<AttributeModifier>ifPresent("max_radius_modifier", modifiers::add);
        return modifiers;
    }

    public List<AttributeModifier> durationModifiers() {
        List<AttributeModifier> modifiers = new ArrayList<>();
        data.<List<AttributeModifier>>ifPresent("duration_modifiers", modifiers::addAll);
        data.<AttributeModifier>ifPresent("duration_modifier", modifiers::add);
        return modifiers;
    }

}
