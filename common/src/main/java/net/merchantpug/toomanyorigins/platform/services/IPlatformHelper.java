package net.merchantpug.toomanyorigins.platform.services;

import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Get the supported modifier type by platform due to the fact that new Modifier is different on Forge's end.
     * @return SerializableDataType of Modifier(on Fabric) or AttributeModifier(on Forge).
     */
    SerializableDataType<?> getModifierDataType();

    /**
     * Get the supported modifier type by platform due to the fact that new Modifier is different on Forge's end.
     * @return SerializableDataType of List of Modifier(on Fabric) or ConfiguredModifier(on Forge).
     */
    SerializableDataType<?> getModifiersDataType();

    /**
     * Apply generic modifier objects to value for a certain living entity.
     * @param living The living entity involved in the modification.
     * @param modifiers Generic modifiers.
     * @param value The value to be modified.
     * @return The modified value.
     */
    double applyModifiers(LivingEntity living, List<?> modifiers, double value);

}