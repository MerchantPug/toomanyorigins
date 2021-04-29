package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.mixin.DamageSourceAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.world.explosion.Explosion;

public class TMODamageSources {

    public static final DamageSource DRAGON_MAGIC = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("dragonMagic")).callSetBypassesArmor()).callSetUnblockable().setUsesMagic();
    public static final DamageSource ZOMBIFICATION = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("zombification")).callSetBypassesArmor()).callSetUnblockable();

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccessor)((DamageSourceAccessor)new ProjectileDamageSource("indirectDragonMagic", magic, attacker)).callSetBypassesArmor()).callSetUnblockable().setUsesMagic();
    }

    public static DamageSource zombification(Entity attacker) {
        return ((DamageSourceAccessor)((DamageSourceAccessor)new EntityDamageSource("indirectZombification", attacker)).callSetBypassesArmor()).callSetUnblockable();
    }

    public static DamageSource jumpExplosion(Explosion jumpExplosion) {
        return jumpExplosion(jumpExplosion != null ? jumpExplosion.getCausingEntity() : null);
    }

    public static DamageSource jumpExplosion(LivingEntity attacker) {
        return attacker != null ? (new EntityDamageSource("jumpExplosion.player", attacker)).setExplosive() : ((DamageSourceAccessor.createDamageSource("jumpExplosion")).setExplosive());
    }
}
