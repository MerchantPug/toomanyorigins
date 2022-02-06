package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;

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
