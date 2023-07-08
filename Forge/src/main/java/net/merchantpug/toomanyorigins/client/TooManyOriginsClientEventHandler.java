package net.merchantpug.toomanyorigins.client;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.client.particle.CustomDragonBreathParticle;
import net.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.merchantpug.toomanyorigins.registry.TMOParticleTypes;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TooManyOriginsClientEventHandler {

    @SubscribeEvent
    public static void registerColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_STEM.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TMOEntityTypes.SMALL_DRAGON_FIREBALL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(TMOEntityTypes.FIREBALL_AREA_EFFECT_CLOUD.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.register(TMOParticleTypes.CUSTOM_DRAGON_BREATH.get(), CustomDragonBreathParticle.Provider::new);
    }

}
