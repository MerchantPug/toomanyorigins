package io.github.merchantpug.toomanyorigins.registry.fabric;

import io.github.apace100.origins.mixin.DamageSourceAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class TMODamageSourcesImpl {
    public static DamageSource dragonMagic() {
        return ((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("dragonMagic")).callSetBypassesArmor().setUsesMagic();
    }

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return ((DamageSourceAccessor)new ProjectileDamageSource("indirectDragonMagic", magic, attacker)).callSetBypassesArmor().setUsesMagic();
    }

    public static DamageSource zombification() {
        return ((DamageSourceAccessor)((DamageSourceAccessor)DamageSourceAccessor.createDamageSource("zombification")).callSetBypassesArmor()).callSetUnblockable();
    }
}
