package net.merchantpug.toomanyorigins.registry;

import io.github.apace100.calio.mixin.DamageSourceAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

public class TMODamageSources {

    public static final DamageSource DRAGON_MAGIC = ((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("dragonMagic")).callSetBypassesArmor().setMagic();
    public static final DamageSource ZOMBIFICATION = ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("zombification")).callSetBypassesArmor()).callSetUnblockable();

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccessor)new IndirectEntityDamageSource("indirectDragonMagic", magic, attacker)).callSetBypassesArmor().setMagic();
    }
}
