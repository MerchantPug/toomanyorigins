package net.merchantpug.toomanyorigins.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class TMOSounds {

    public static final SoundEvent ORIGIN_HARE_DASH = SoundEvent.of(new Identifier("toomanyorigins", "origin.hare.dash"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, ORIGIN_HARE_DASH.getId(), ORIGIN_HARE_DASH);
    }
}
