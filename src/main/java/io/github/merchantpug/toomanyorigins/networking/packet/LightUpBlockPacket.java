package io.github.merchantpug.toomanyorigins.networking.packet;

import io.github.merchantpug.toomanyorigins.mixin.AbstractFurnaceBlockEntityAccessor;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.util.registry.Registry;

import java.util.Random;

import static net.minecraft.state.property.Properties.LIT;

public class LightUpBlockPacket {
    public static final Identifier ID = new Identifier(TooManyOrigins.MODID, "light_up_block");

    public static void send(BlockPos pos, ParticleType particle, int particleCount, int burnTime) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ParticleEffect particleEffect = (ParticleEffect)particle;
        buf.writeBlockPos(pos);
        buf.writeInt(Registry.PARTICLE_TYPE.getRawId(particleEffect.getType()));
        buf.writeInt(particleCount);
        buf.writeInt(burnTime);
        particleEffect.write(buf);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        ParticleType<?> particle = Registry.PARTICLE_TYPE.get(buf.readInt());
        int particleCount = buf.readInt();
        int burnTime = buf.readInt();
        ParticleEffect particleEffect = readParticleParameters(buf, particle);
        server.execute(() -> {
            BlockState state = player.world.getBlockState(pos);
            BlockEntity entity = player.world.getBlockEntity(pos);
            if (state.getBlock() instanceof AbstractFurnaceBlock || state.getBlock() instanceof CampfireBlock) {
                player.world.setBlockState(pos, state.with(LIT, true).with(LIT, true), 2);
                ((ServerWorld)player.world).spawnParticles(particleEffect, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, particleCount, player.getRandom().nextDouble() * 0.2D - 0.1D, 0.1D, player.getRandom().nextDouble() * 0.2D - 0.1D, 0.05D);
                player.swingHand(Hand.MAIN_HAND, true);
                player.world.syncWorldEvent(1590, pos, 0);
            }
            if (entity instanceof AbstractFurnaceBlockEntity) {
                if (((AbstractFurnaceBlockEntityAccessor)entity).getBurnTime() < burnTime) {
                    ((AbstractFurnaceBlockEntityAccessor)entity).setFuelTime(burnTime);
                    ((AbstractFurnaceBlockEntityAccessor)entity).setBurnTime(burnTime);
                    player.world.syncWorldEvent(1591, pos, 0);
                }
            }
        });
    }

    private static <T extends ParticleEffect> T readParticleParameters(PacketByteBuf buf, ParticleType<T> type) {
        return type.getParametersFactory().read(type, buf);
    }
}
