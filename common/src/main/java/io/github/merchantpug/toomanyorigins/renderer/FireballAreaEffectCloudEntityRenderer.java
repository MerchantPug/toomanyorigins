package io.github.merchantpug.toomanyorigins.renderer;

import io.github.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FireballAreaEffectCloudEntityRenderer extends EntityRenderer<FireballAreaEffectCloudEntity> {
    public FireballAreaEffectCloudEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(FireballAreaEffectCloudEntity fireballAreaEffectCloudEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
