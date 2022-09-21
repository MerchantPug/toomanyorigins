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

package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.networking.TMOPacketsS2C;
import net.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.merchantpug.toomanyorigins.registry.TMOEntities;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.network.PacketByteBuf;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TooManyOriginsClient implements ClientModInitializer {
    public static boolean isServerRunningTMO = false;

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        TMOPacketsS2C.register();

        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_BEETROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CARROTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_MELON_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_POTATOES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_PUMPKIN_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_WHEAT, RenderLayer.getCutout());

        EntityRendererRegistry.register(TMOEntities.SMALL_DRAGON_FIREBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD, EmptyEntityRenderer::new);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int k = 255;
            int l = 0;
            return k << 8 | l;
        }, TMOBlocks.WITHERED_PUMPKIN_STEM, TMOBlocks.WITHERED_MELON_STEM, TMOBlocks.WITHERED_STEM);
    }

    @Environment(EnvType.CLIENT)
    private static CompletableFuture<PacketByteBuf> handleHandshake(MinecraftClient minecraftClient, ClientLoginNetworkHandler clientLoginNetworkHandler, PacketByteBuf packetByteBuf, Consumer<GenericFutureListener<? extends Future<? super Void>>> genericFutureListenerConsumer) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(TooManyOrigins.SEMVER.length);
        for(int i = 0; i < TooManyOrigins.SEMVER.length; i++) {
            buf.writeInt(TooManyOrigins.SEMVER[i]);
        }
        TooManyOriginsClient.isServerRunningTMO = true;
        return CompletableFuture.completedFuture(buf);
    }
}