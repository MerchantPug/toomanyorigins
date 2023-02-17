package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class TMOSounds {

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, TooManyOrigins.identifier("origin.hare.dash"), SoundEvent.of(TooManyOrigins.identifier("origin.hare.dash")));
    }
}
