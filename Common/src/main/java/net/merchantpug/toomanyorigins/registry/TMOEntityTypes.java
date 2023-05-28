package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import net.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import net.merchantpug.toomanyorigins.registry.services.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class TMOEntityTypes {
    private static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(Registry.ENTITY_TYPE, TooManyOrigins.MOD_ID);

    public static final RegistryObject<EntityType<SmallDragonFireballEntity>> SMALL_DRAGON_FIREBALL = register("small_dragon_fireball", () -> EntityType.Builder.<SmallDragonFireballEntity>of(SmallDragonFireballEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(64).updateInterval(10).build("small_dragon_fireball"));
    public static final RegistryObject<EntityType<FireballAreaEffectCloudEntity>> FIREBALL_AREA_EFFECT_CLOUD = register("fireball_area_effect_cloud", () -> EntityType.Builder.<FireballAreaEffectCloudEntity>of(FireballAreaEffectCloudEntity::new, MobCategory.MISC).fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(2147483647).build("fireball_area_effect_cloud"));

    public static void register() {

    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType<T>> entityType) {
        return ENTITY_TYPES.register(name, entityType);
    }

}