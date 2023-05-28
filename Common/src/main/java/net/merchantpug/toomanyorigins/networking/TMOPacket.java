package net.merchantpug.toomanyorigins.networking;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface TMOPacket {
    default FriendlyByteBuf toBuf() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        encode(buf);
        return buf;
    }

    void encode(FriendlyByteBuf buf);

    ResourceLocation getFabricId();
}
