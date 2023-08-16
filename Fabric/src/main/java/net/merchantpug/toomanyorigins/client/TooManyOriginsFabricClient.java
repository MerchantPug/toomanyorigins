package net.merchantpug.toomanyorigins.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.merchantpug.toomanyorigins.client.particle.CustomDragonBreathParticle;
import net.merchantpug.toomanyorigins.network.TMOPackets;
import net.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.merchantpug.toomanyorigins.registry.TMOParticleTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class TooManyOriginsFabricClient implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TMOPackets.registerS2C();

        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_STEM.get(), RenderType.cutout());

        EntityRendererRegistry.register(TMOEntityTypes.SMALL_DRAGON_FIREBALL.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(TMOEntityTypes.FIREBALL_AREA_EFFECT_CLOUD.get(), NoopRenderer::new);

        ParticleFactoryRegistry.getInstance().register(TMOParticleTypes.CUSTOM_DRAGON_BREATH.get(), CustomDragonBreathParticle.Provider::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_STEM.get());
    }

}