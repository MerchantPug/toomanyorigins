package com.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

public class TMODamageSources {

    @ExpectPlatform
    public static DamageSource dragonMagic() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static DamageSource zombification() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        throw new AssertionError();
    }
}
