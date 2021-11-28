package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import io.github.merchantpug.toomanyorigins.registry.TMOEntities;
import io.github.merchantpug.toomanyorigins.blocks.WitheredStemBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.Identifier;

public class TooManyOriginsClient implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_BEETROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CARROTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_MELON_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_POTATOES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_PUMPKIN_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_WHEAT, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(TMOEntities.SMALL_DRAGON_FIREBALL,
                (context) -> new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.INSTANCE.register(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD,
                (context) -> new EmptyEntityRenderer(context));
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_PUMPKIN_STEM, TMOBlocks.WITHERED_MELON_STEM, TMOBlocks.WITHERED_STEM);
    }
}