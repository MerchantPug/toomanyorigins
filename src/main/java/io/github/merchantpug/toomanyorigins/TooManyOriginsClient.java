package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.networking.packet.EntitySpawnPacket;
import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import io.github.merchantpug.toomanyorigins.registry.TMOEntities;
import io.github.merchantpug.toomanyorigins.blocks.WitheredStemBlock;
import io.github.merchantpug.toomanyorigins.config.*;
import io.github.merchantpug.toomanyorigins.registry.TMOEntityConditionsClient;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.AreaEffectCloudEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import java.util.UUID;

public class TooManyOriginsClient implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(TooManyOrigins.MODID, "spawn_packet");

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        AutoConfig.register(ClientConfig.class, Toml4jConfigSerializer::new);

        TMOEntityConditionsClient.register();

        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_BEETROOTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_CARROTS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.ATTACHED_WITHERED_MELON_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_MELON_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_POTATOES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.ATTACHED_WITHERED_PUMPKIN_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_PUMPKIN_STEM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TMOBlocks.WITHERED_WHEAT, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(TMOEntities.SMALL_DRAGON_FIREBALL,
                (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD,
                (dispatcher, context) -> new AreaEffectCloudEntityRenderer(dispatcher));
        receiveEntityPacket();

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int j = 7 * 32;
            int k = 255 - 7 * 8;
            int l = 7 * 14;
            return j << 16 | k << 8 | l;
        }, TMOBlocks.ATTACHED_WITHERED_PUMPKIN_STEM, TMOBlocks.ATTACHED_WITHERED_MELON_STEM);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int i = state.get(WitheredStemBlock.AGE);
            int j = i * 32;
            int k = 255 - i * 8;
            int l = i * 14;
            return j << 16 | k << 8 | l;
        }, TMOBlocks.WITHERED_PUMPKIN_STEM, TMOBlocks.WITHERED_MELON_STEM);
    }

    @Environment(EnvType.CLIENT)
    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }
}