package net.merchantpug.toomanyorigins.power.configuration;

import io.github.apace100.calio.data.SerializableData;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredModifier;

import java.util.ArrayList;
import java.util.List;

public record ModifyDragonFireballConfiguration(SerializableData.Instance data) implements IDynamicFeatureConfiguration {

    public List<ConfiguredModifier<?>> damageModifiers() {
        List<ConfiguredModifier<?>> modifiers = new ArrayList<>();
        data.<List<ConfiguredModifier<?>>>ifPresent("damage_modifiers", modifiers::addAll);
        data.<ConfiguredModifier<?>>ifPresent("damage_modifier", modifiers::add);
        return modifiers;
    }

    public List<ConfiguredModifier<?>> minRadiusModifiers() {
        List<ConfiguredModifier<?>> modifiers = new ArrayList<>();
        data.<List<ConfiguredModifier<?>>>ifPresent("min_radius_modifiers", modifiers::addAll);
        data.<ConfiguredModifier<?>>ifPresent("min_radius_modifier", modifiers::add);
        return modifiers;
    }

    public List<ConfiguredModifier<?>> maxRadiusModifiers() {
        List<ConfiguredModifier<?>> modifiers = new ArrayList<>();
        data.<List<ConfiguredModifier<?>>>ifPresent("max_radius_modifiers", modifiers::addAll);
        data.<ConfiguredModifier<?>>ifPresent("max_radius_modifier", modifiers::add);
        return modifiers;
    }

    public List<ConfiguredModifier<?>> durationModifiers() {
        List<ConfiguredModifier<?>> modifiers = new ArrayList<>();
        data.<List<ConfiguredModifier<?>>>ifPresent("duration_modifiers", modifiers::addAll);
        data.<ConfiguredModifier<?>>ifPresent("duration_modifier", modifiers::add);
        return modifiers;
    }

}
