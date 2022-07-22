package com.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.apoli.integration.AdditionalPowerDataCallback;
import io.github.apace100.apoli.power.PowerTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;

@Mixin(value = PowerTypes.class, remap = false)
public interface PowerTypesAccessor {
    @Accessor("LOADING_PRIORITIES")
    static HashMap<Identifier, Integer> getLoadingPriorities() {
        throw new RuntimeException("");
    }

    @Accessor("ADDITIONAL_DATA")
    static HashMap<String, AdditionalPowerDataCallback> getAdditionalData() {
        throw new RuntimeException("");
    }
}
