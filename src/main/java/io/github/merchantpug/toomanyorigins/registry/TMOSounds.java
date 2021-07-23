package io.github.merchantpug.toomanyorigins.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TMOSounds {

    public static final SoundEvent ORIGIN_HARE_DASH = new SoundEvent(new Identifier("toomanyorigins", "origin.hare.dash"));

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, new Identifier("toomanyorigins", "origin.hare.dash"), ORIGIN_HARE_DASH);
    }
}
