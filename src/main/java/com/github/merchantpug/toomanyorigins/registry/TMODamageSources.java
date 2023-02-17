package com.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.calio.mixin.DamageSourceAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.world.explosion.Explosion;

public class TMODamageSources {

    public static final DamageSource DRAGON_MAGIC = DamageSourceAccessor.createDamageSource("dragonMagic").setBypassesArmor().setUsesMagic();
    public static final DamageSource ZOMBIFICATION = DamageSourceAccessor.createDamageSource("zombification").setBypassesArmor().setUnblockable();

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return new ProjectileDamageSource("indirectDragonMagic", magic, attacker).setBypassesArmor().setUsesMagic();
    }
}
