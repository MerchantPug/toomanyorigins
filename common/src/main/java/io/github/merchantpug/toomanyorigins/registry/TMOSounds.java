package io.github.merchantpug.toomanyorigins.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class TMOSounds {

    public static final SoundEvent ORIGIN_HARE_DASH = new SoundEvent(new Identifier("toomanyorigins", "origin.hare.dash"));

    public static void register() {
        TMORegistriesArchitectury.SOUNDS.register(ORIGIN_HARE_DASH.getId(), () -> ORIGIN_HARE_DASH);
    }
}
