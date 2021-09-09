package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import io.github.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TMOEntities {
    public static final EntityType<SmallDragonFireballEntity> SMALL_DRAGON_FIREBALL;
    public static final EntityType<FireballAreaEffectCloudEntity> FIREBALL_AREA_EFFECT_CLOUD;

    static {
        SMALL_DRAGON_FIREBALL = EntityType.Builder.<SmallDragonFireballEntity>create(SmallDragonFireballEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(64).trackingTickInterval(10).build("small_dragon_fireball");
        FIREBALL_AREA_EFFECT_CLOUD = EntityType.Builder.<FireballAreaEffectCloudEntity>create(FireballAreaEffectCloudEntity::new, SpawnGroup.MISC).makeFireImmune().setDimensions(6.0F, 0.5F).maxTrackingRange(10).trackingTickInterval(Integer.MAX_VALUE).build("fireball_area_effect_cloud");
    }

    public static void register() {
        TMORegistriesArchitectury.ENTITY_TYPES.register(new Identifier(TooManyOrigins.MODID, "small_dragon_fireball"), () -> SMALL_DRAGON_FIREBALL);
        TMORegistriesArchitectury.ENTITY_TYPES.register(new Identifier(TooManyOrigins.MODID, "fireball_area_effect_cloud"), () -> FIREBALL_AREA_EFFECT_CLOUD);
    }
}