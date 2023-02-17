/*
MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.github.merchantpug.toomanyorigins;

import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.networking.TMOPackets;
import com.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TooManyOriginsClient implements ClientModInitializer {
    public static boolean isServerRunningTMO = false;

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TMOPackets.registerS2C();

        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_STEM, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 255 << 8, TMOBlocks.WITHERED_STEM);

        ClientLoginConnectionEvents.DISCONNECT.register((handler, client) -> LegacyContentRegistry.disableAll());

        EntityRendererRegistry.register(TMOEntities.SMALL_DRAGON_FIREBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD, EmptyEntityRenderer::new);
    }
}