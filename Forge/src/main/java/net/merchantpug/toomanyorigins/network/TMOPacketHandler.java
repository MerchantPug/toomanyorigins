package net.merchantpug.toomanyorigins.network;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.network.s2c.SyncLegacyContentPacket;
import net.merchantpug.toomanyorigins.network.s2c.TMOPacketS2C;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
        INSTANCE.registerMessage(i++, SyncLegacyContentPacket.class, SyncLegacyContentPacket::encode, SyncLegacyContentPacket::decode, createS2CHandler(SyncLegacyContentPacket::handle));
    }

    private static <MSG extends TMOPacketS2C> BiConsumer<MSG, Supplier<NetworkEvent.Context>> createS2CHandler(Consumer<MSG> handler) {
        return (msg, ctx) -> {
            handler.accept(msg);
            ctx.get().setPacketHandled(true);
        };
    }
}
