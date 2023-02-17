package com.github.merchantpug.toomanyorigins.networking;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.TooManyOriginsClient;
import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.networking.s2c.SyncLegacyContentPacket;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.merchantpug.apugli.networking.ApugliPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class TMOPackets {
    public static final Identifier HANDSHAKE = TooManyOrigins.identifier("handshake");

    public static void registerC2S() {
        if (TooManyOriginsConfig.performVersionCheck) {
            ServerLoginConnectionEvents.QUERY_START.register(TMOPackets::handshake);
            ServerLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, TMOPackets::handleHandshakeReply);
        }
    }

    private static void handleHandshakeReply(MinecraftServer minecraftServer, ServerLoginNetworkHandler serverLoginNetworkHandler, boolean understood, PacketByteBuf packetByteBuf, ServerLoginNetworking.LoginSynchronizer loginSynchronizer, PacketSender packetSender) {
        if (understood) {
            int clientSemVerLength = packetByteBuf.readInt();
            int[] clientSemVer = new int[clientSemVerLength];
            boolean mismatch = clientSemVerLength != TooManyOrigins.SEMVER.length;
            for (int i = 0; i < clientSemVerLength; i++) {
                clientSemVer[i] = packetByteBuf.readInt();
                if (i < clientSemVerLength - 1 && clientSemVer[i] != TooManyOrigins.SEMVER[i]) {
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
                serverLoginNetworkHandler.disconnect(Text.translatable("toomanyorigins.gui.version_mismatch", TooManyOrigins.VERSION, clientVersionString));
            }
        } else {
            serverLoginNetworkHandler.disconnect(Text.literal("This server requires you to install the TooManyOrigins mod (v" + TooManyOrigins.VERSION + ") to play."));
        }
    }

    private static void handshake(ServerLoginNetworkHandler serverLoginNetworkHandler, MinecraftServer minecraftServer, PacketSender packetSender, ServerLoginNetworking.LoginSynchronizer loginSynchronizer) {
        packetSender.sendPacket(TMOPackets.HANDSHAKE, PacketByteBufs.empty());
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2C() {
        ClientLoginNetworking.registerGlobalReceiver(TMOPackets.HANDSHAKE, TMOPackets::handleHandshake);

        ClientPlayConnectionEvents.INIT.register((clientPlayNetworkHandler, minecraftClient) -> {
            ClientPlayNetworking.registerReceiver(SyncLegacyContentPacket.ID, createS2CHandler(SyncLegacyContentPacket::decode, SyncLegacyContentPacket::handle));
        });
    }

    public static void sendS2C(TMOPacket packet, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, packet.getId(), packet.toBuf());
    }

    private static <T extends TMOPacket> ClientPlayNetworking.PlayChannelHandler createS2CHandler(Function<PacketByteBuf, T> decoder, BiConsumer<T, MinecraftClient> handler) {
        return (client, _handler, buf, responseSender) -> handler.accept(decoder.apply(buf), client);
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
