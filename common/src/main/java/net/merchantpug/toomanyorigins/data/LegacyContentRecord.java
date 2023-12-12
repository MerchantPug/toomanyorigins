package net.merchantpug.toomanyorigins.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LegacyContentRecord(boolean dragonFireballEnabled,
                                  boolean witheredCropsEnabled,
                                  boolean zombifyingEnabled) {

    public static final Codec<LegacyContentRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf(LegacyContentRegistry.DRAGON_FIREBALL, false).forGetter(LegacyContentRecord::dragonFireballEnabled),
            Codec.BOOL.optionalFieldOf(LegacyContentRegistry.WITHERED_CROPS, false).forGetter(LegacyContentRecord::witheredCropsEnabled),
            Codec.BOOL.optionalFieldOf(LegacyContentRegistry.ZOMBIFYING, false).forGetter(LegacyContentRecord::zombifyingEnabled)
    ).apply(instance, LegacyContentRecord::new));

    public int count() {
        int count = 0;
        if (dragonFireballEnabled()) {
            ++count;
        }
        if (witheredCropsEnabled()) {
            ++count;
        }
        if (zombifyingEnabled()) {
            ++count;
        }
        return count;
    }

}
