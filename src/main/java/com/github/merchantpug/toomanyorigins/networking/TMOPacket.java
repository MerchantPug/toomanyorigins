package com.github.merchantpug.toomanyorigins.networking;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface TMOPacket {
    default PacketByteBuf toBuf() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        encode(buf);
        return buf;
    }

    void encode(PacketByteBuf buf);

    Identifier getId();
}
