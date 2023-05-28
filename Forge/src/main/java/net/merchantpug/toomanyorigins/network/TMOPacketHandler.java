package net.merchantpug.toomanyorigins.network;

import net.merchantpug.apugli.network.ApugliPacketHandler;
import net.merchantpug.apugli.networking.c2s.ApugliPacketC2S;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.networking.c2s.ExhaustPacket;
import net.merchantpug.toomanyorigins.networking.c2s.TMOPacketC2S;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TMOPacketHandler {
    private static final String PROTOCOL_VERISON = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            TooManyOrigins.asResource("main"),
            () -> PROTOCOL_VERISON,
            PROTOCOL_VERISON::equals,
            PROTOCOL_VERISON::equals
    );


    public static void register() {
        int i = 0;
        INSTANCE.registerMessage(i++, ExhaustPacket.class, ExhaustPacket::encode, ExhaustPacket::decode, createC2SHandler(ExhaustPacket::handle));
    }

    public static void sendC2S(TMOPacketC2S packet) {
        ApugliPacketHandler.INSTANCE.sendToServer(packet);
    }

    private static <MSG extends TMOPacketC2S> BiConsumer<MSG, Supplier<NetworkEvent.Context>> createC2SHandler(TriConsumer<MSG, MinecraftServer, ServerPlayer> handler) {
        return (msg, ctx) -> {
            handler.accept(msg, ctx.get().getSender().getServer(), ctx.get().getSender());
            ctx.get().setPacketHandled(true);
        };
    }
}
