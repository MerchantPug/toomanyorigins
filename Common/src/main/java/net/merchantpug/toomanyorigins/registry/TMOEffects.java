package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.effect.ChargedStatusEffect;
import net.merchantpug.toomanyorigins.effect.EndFireStatusEffect;
import net.merchantpug.toomanyorigins.effect.ZombifyingStatusEffect;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import net.merchantpug.toomanyorigins.registry.services.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

public class TMOEffects {
    private static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registry.MOB_EFFECT, TooManyOrigins.MOD_ID);

    public static final RegistryObject<MobEffect> CHARGED = register("charged",
            () -> new ChargedStatusEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "c12451f1-b2a4-47aa-88ef-3f11b1b21e5e", 0.20000000298023224D, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<EndFireStatusEffect> END_FIRE = register("end_fire", EndFireStatusEffect::new);
    public static final RegistryObject<ZombifyingStatusEffect> ZOMBIFYING = register("zombifying", ZombifyingStatusEffect::new);

    public static void register() {
    }

    public static <T extends MobEffect> RegistryObject<T> register(String name, Supplier<T> effects) {
        return MOB_EFFECTS.register(name, effects);
    }

}
