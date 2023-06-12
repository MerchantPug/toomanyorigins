package net.merchantpug.toomanyorigins.network;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.TooManyOriginsFabric;
import net.merchantpug.toomanyorigins.client.TooManyOriginsFabricClient;
import net.merchantpug.toomanyorigins.network.s2c.SyncLegacyContentPacket;
import net.merchantpug.toomanyorigins.network.s2c.TMOPacketS2C;
import net.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class TMOPackets {
    public static final ResourceLocation HANDSHAKE = TooManyOrigins.asResource("handshake");


    public static void registerS2C() {
        ClientLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, TMOPackets::handleHandshake);
        ClientPlayConnectionEvents.INIT.register((clientPlayNetworkHandler, minecraftClient) -> {
            ClientPlayNetworking.registerReceiver(SyncLegacyContentPacket.ID, createS2CHandler(SyncLegacyContentPacket::decode, SyncLegacyContentPacket::handle));
        });
    }

    private static <T extends TMOPacketS2C> ClientPlayNetworking.PlayChannelHandler createS2CHandler(Function<FriendlyByteBuf, T> decode, Consumer<T> handler) {
        return (client, _handler, buf, responseSender) -> handler.accept(decode.apply(buf));
    }

    private static CompletableFuture<FriendlyByteBuf> handleHandshake(Minecraft minecraftClient, ClientHandshakePacketListenerImpl clientLoginNetworkHandler, FriendlyByteBuf packetByteBuf, Consumer<GenericFutureListener<? extends Future<? super Void>>> genericFutureListenerConsumer) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeInt(TooManyOriginsFabric.SEMVER.length);
        for(int i = 0; i < TooManyOriginsFabric.SEMVER.length; i++) {
            buf.writeInt(TooManyOriginsFabric.SEMVER[i]);
        }
        TooManyOriginsFabricClient.isServerRunningTMO = true;
        return CompletableFuture.completedFuture(buf);
    }

    public static void registerC2S() {
        ServerLoginConnectionEvents.QUERY_START.register(TMOPackets::handshake);
        ServerLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, TMOPackets::handleHandshakeReply);
    }

    private static void handleHandshakeReply(MinecraftServer minecraftServer, ServerLoginPacketListenerImpl serverLoginNetworkHandler, boolean understood, FriendlyByteBuf packetByteBuf, ServerLoginNetworking.LoginSynchronizer loginSynchronizer, PacketSender packetSender) {
        if (TooManyOriginsConfig.performVersionCheck) {
            if (understood) {
                int clientSemVerLength = packetByteBuf.readInt();
                int[] clientSemVer = new int[clientSemVerLength];
                boolean mismatch = clientSemVerLength != TooManyOriginsFabric.SEMVER.length;
                for (int i = 0; i < clientSemVerLength; i++) {
                    clientSemVer[i] = packetByteBuf.readInt();
                    if (i < clientSemVerLength - 1 && clientSemVer[i] != TooManyOriginsFabric.SEMVER[i]) {
                        mismatch = true;
                    }
                }
                if (mismatch) {
                    StringBuilder clientVersionString = new StringBuilder();
                    for (int i = 0; i < clientSemVerLength; i++) {
                        clientVersionString.append(clientSemVer[i]);
                        if (i < clientSemVerLength - 1) {
                            clientVersionString.append(".");
                        }
                    }
                    serverLoginNetworkHandler.disconnect(Component.translatable("toomanyorigins.gui.version_mismatch", TooManyOriginsFabric.VERSION, clientVersionString));
                }
            } else {
                serverLoginNetworkHandler.disconnect(Component.literal("This server requires you to install the TooManyOrigins mod (v" + TooManyOriginsFabric.VERSION + ") to play."));
            }
        }
    }

    private static void handshake(ServerLoginPacketListenerImpl serverLoginNetworkHandler, MinecraftServer minecraftServer, PacketSender packetSender, ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        packetSender.sendPacket(TMOPackets.HANDSHAKE, PacketByteBufs.empty());
    }
}
