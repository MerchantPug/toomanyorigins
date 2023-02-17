package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.content.legacy.entity.LegacyFireballAreaEffectCloudEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.content.legacy.entity.LegacySmallDragonFireballEntity;

public class TMOEntities {
    public static final EntityType<LegacySmallDragonFireballEntity> SMALL_DRAGON_FIREBALL = FabricEntityTypeBuilder.<LegacySmallDragonFireballEntity>create(SpawnGroup.MISC, LegacySmallDragonFireballEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(64).trackedUpdateRate(10).build();
    public static final EntityType<LegacyFireballAreaEffectCloudEntity> FIREBALL_AREA_EFFECT_CLOUD = FabricEntityTypeBuilder.<LegacyFireballAreaEffectCloudEntity>create(SpawnGroup.MISC, LegacyFireballAreaEffectCloudEntity::new).fireImmune().dimensions(EntityDimensions.changing(6.0F, 0.5F)).trackRangeBlocks(10).trackedUpdateRate(2147483647).build();

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "small_dragon_fireball"), SMALL_DRAGON_FIREBALL);
        Registry.register(Registries.ENTITY_TYPE, new Identifier(TooManyOrigins.MODID, "fireball_area_effect_cloud"), FIREBALL_AREA_EFFECT_CLOUD);
    }
}