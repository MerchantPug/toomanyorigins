package net.merchantpug.toomanyorigins.networking.c2s;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public record ExhaustPacket(float amount) implements TMOPacketC2S {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(amount);
    }

    public static ExhaustPacket decode(FriendlyByteBuf buf) {
        return new ExhaustPacket(buf.readFloat());
    }

    @Override
    public ResourceLocation getFabricId() {
        return TooManyOrigins.asResource("exhaust");
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            player.causeFoodExhaustion(amount);
        });
    }
}
