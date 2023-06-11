package net.merchantpug.toomanyorigins.client;

import eu.midnightdust.core.MidnightLibClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.networking.TMOPackets;
import net.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TooManyOriginsFabricClient implements ClientModInitializer {
    public static boolean isServerRunningTMO = false;

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TMOPackets.registerS2C();

        MidnightLibClient.hiddenMods.add(TooManyOrigins.MOD_ID);

        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_STEM.get(), RenderType.cutout());

        EntityRendererRegistry.register(TMOEntityTypes.SMALL_DRAGON_FIREBALL.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(TMOEntityTypes.FIREBALL_AREA_EFFECT_CLOUD.get(), NoopRenderer::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_STEM.get());
    }
}