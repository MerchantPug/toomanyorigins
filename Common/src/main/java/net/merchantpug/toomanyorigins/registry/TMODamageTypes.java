package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class TMODamageTypes {
    public static final ResourceKey<DamageType> DRAGON_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, TooManyOrigins.asResource("dragon_magic"));
    public static final ResourceKey<DamageType> INDIRECT_DRAGON_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, TooManyOrigins.asResource("indirect_dragon_magic"));
    public static final ResourceKey<DamageType> OVERHEAT = ResourceKey.create(Registries.DAMAGE_TYPE, TooManyOrigins.asResource("overheat"));
    public static final ResourceKey<DamageType> ZOMBIFICATION = ResourceKey.create(Registries.DAMAGE_TYPE, TooManyOrigins.asResource("zombification"));
}