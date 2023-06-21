package net.merchantpug.toomanyorigins.platform.services;

import net.merchantpug.apugli.condition.factory.IConditionFactory;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

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
     * Apply generic modifier objects to value for a certain living entity.
     * @param living The living entity involved in the modification.
     * @param modifiers Generic modifiers.
     * @param value The value to be modified.
     * @return The modified value.
     */
    double applyModifiers(List<AttributeModifier> modifiers, double value);

    void registerDamage(String var1, IConditionFactory<Tuple<DamageSource, Float>> var2);

}