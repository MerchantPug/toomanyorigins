package net.merchantpug.toomanyorigins.content.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.toomanyorigins.registry.TMOParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public record CustomDragonBreathParticleOptions(Vector3f color) implements ParticleOptions {
    public static final Deserializer<CustomDragonBreathParticleOptions> DESERIALIZER = new Deserializer<>() {
        public CustomDragonBreathParticleOptions fromCommand(ParticleType<CustomDragonBreathParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float h = reader.readFloat();
            return new CustomDragonBreathParticleOptions(new Vector3f(f, g, h));
        }

        public CustomDragonBreathParticleOptions fromNetwork(ParticleType<CustomDragonBreathParticleOptions> particleType, FriendlyByteBuf buf) {
            return new CustomDragonBreathParticleOptions(new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat()));
        }
    };

    public static final Codec<CustomDragonBreathParticleOptions> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Vector3f.CODEC.fieldOf("color").forGetter(CustomDragonBreathParticleOptions::color)
    ).apply(instance, CustomDragonBreathParticleOptions::new));

    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.color.x());
        buf.writeFloat(this.color.y());
        buf.writeFloat(this.color.z());
    }

    public String writeToString() {
        return String.format(
                Locale.ROOT, "%s %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z()
        );
    }

    public ParticleType<CustomDragonBreathParticleOptions> getType() {
        return TMOParticleTypes.CUSTOM_DRAGON_BREATH.get();
    }
}
