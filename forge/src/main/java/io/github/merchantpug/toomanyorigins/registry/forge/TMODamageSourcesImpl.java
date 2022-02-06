package io.github.merchantpug.toomanyorigins.registry.forge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class TMODamageSourcesImpl {
    public static DamageSource dragonMagic() {
        return new DamageSource("dragonMagic").setBypassesArmor().setUsesMagic();
    }

    public static DamageSource dragonMagic(Entity magic, Entity attacker) {
        return new ProjectileDamageSource("indirectDragonMagic", magic, attacker).setUsesMagic();
    }

    public static DamageSource zombification() {
        return new DamageSource("zombification").setBypassesArmor().setUnblockable();
    }
}
