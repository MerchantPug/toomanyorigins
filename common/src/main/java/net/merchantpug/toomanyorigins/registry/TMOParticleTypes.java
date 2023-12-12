package net.merchantpug.toomanyorigins.registry;

import com.mojang.serialization.Codec;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.content.particle.CustomDragonBreathParticleOptions;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import net.merchantpug.toomanyorigins.registry.services.RegistryObject;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Function;

public class TMOParticleTypes {
    private static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, TooManyOrigins.MOD_ID);

    public static final RegistryObject<ParticleType<CustomDragonBreathParticleOptions>> CUSTOM_DRAGON_BREATH = register("custom_dragon_breath", false, CustomDragonBreathParticleOptions.DESERIALIZER, (particleType) -> CustomDragonBreathParticleOptions.CODEC);

    public static void register() {

    }

    private static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String name, boolean alwaysShow, ParticleOptions.Deserializer<T> deserializer, final Function<ParticleType<T>, Codec<T>> codec) {
        return PARTICLE_TYPES.register(name, () -> new ParticleType<>(alwaysShow, deserializer) {
            public Codec<T> codec() { return codec.apply(this); }
        });
    }
}
