package io.github.merchantpug.toomanyorigins.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class TMODamageSources {

    public static final DamageSource DRAGON_MAGIC = new DamageSource("dragonMagic").setUsesMagic().setUnblockable().setBypassesArmor();
    public static final DamageSource ZOMBIFICATION = new DamageSource("zombification").setUnblockable().setBypassesArmor();

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return new ProjectileDamageSource("indirectDragonMagic", magic, attacker).setUsesMagic();
    }

    public static DamageSource zombification(Entity attacker) {
        return new EntityDamageSource("indirectZombification", attacker).setBypassesArmor().setUnblockable();
    }

    public static DamageSource jumpExplosion(LivingEntity attacker) {
        return attacker != null ? (new EntityDamageSource("jumpExplosion.player", attacker)).setExplosive() : new DamageSource("jumpExplosion").setExplosive();
    }
}
