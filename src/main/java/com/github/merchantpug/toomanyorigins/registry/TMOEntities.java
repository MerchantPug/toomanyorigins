package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.content.entity.legacy.LegacyFireballAreaEffectCloudEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.content.entity.legacy.LegacySmallDragonFireballEntity;

public class TMOEntities {
    public static final EntityType<LegacySmallDragonFireballEntity> SMALL_DRAGON_FIREBALL = FabricEntityTypeBuilder.<LegacySmallDragonFireballEntity>create(SpawnGroup.MISC, LegacySmallDragonFireballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(64, 10).build();
    public static final EntityType<LegacyFireballAreaEffectCloudEntity> FIREBALL_AREA_EFFECT_CLOUD = FabricEntityTypeBuilder.<LegacyFireballAreaEffectCloudEntity>create(SpawnGroup.MISC, LegacyFireballAreaEffectCloudEntity::new).fireImmune().dimensions(EntityDimensions.changing(6.0F, 0.5F)).trackable(10, 2147483647).build();

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "small_dragon_fireball"), SMALL_DRAGON_FIREBALL);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "fireball_area_effect_cloud"), FIREBALL_AREA_EFFECT_CLOUD);
    }
}