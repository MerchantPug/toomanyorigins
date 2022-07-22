package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class TMOSounds {

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, TooManyOrigins.identifier("origin.hare.dash"), new SoundEvent(TooManyOrigins.identifier("origin.hare.dash")));
    }
}
