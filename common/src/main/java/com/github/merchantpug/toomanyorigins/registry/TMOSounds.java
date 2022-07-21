package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.sound.SoundEvent;

public class TMOSounds {

    public static final SoundEvent ORIGIN_HARE_DASH = new SoundEvent(TooManyOrigins.identifier("origin.hare.dash"));

    public static void register() {
        TMORegistriesArchitectury.SOUNDS.register(TooManyOrigins.identifier("origin.hare.dash"), () -> ORIGIN_HARE_DASH);
    }
}
