package com.github.merchantpug.toomanyorigins;

import com.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import com.github.merchantpug.toomanyorigins.renderer.FireballAreaEffectCloudEntityRenderer;
import me.shedaniel.architectury.registry.ColorHandlers;
import me.shedaniel.architectury.registry.RenderTypes;
import me.shedaniel.architectury.registry.entity.EntityRenderers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TooManyOriginsClient {
    @Environment(EnvType.CLIENT)
    public static void init() {
        EntityRenderers.register(TMOEntities.SMALL_DRAGON_FIREBALL,
                (dispatcher) -> new FlyingItemEntityRenderer<>(dispatcher, MinecraftClient.getInstance().getItemRenderer()));
        EntityRenderers.register(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD, FireballAreaEffectCloudEntityRenderer::new);
        ColorHandlers.registerBlockColors((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_PUMPKIN_STEM, TMOBlocks.WITHERED_MELON_STEM);
    }

    public static void setup() {
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_BEETROOTS);
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_CARROTS);
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_MELON_STEM);
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_POTATOES);
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_PUMPKIN_STEM);
        RenderTypes.register(RenderLayer.getCutout(), TMOBlocks.WITHERED_WHEAT);
    }
}
