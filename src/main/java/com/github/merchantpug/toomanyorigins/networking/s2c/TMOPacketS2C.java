package com.github.merchantpug.toomanyorigins.networking.s2c;

import com.github.merchantpug.toomanyorigins.networking.TMOPacket;
import net.minecraft.client.MinecraftClient;

public interface TMOPacketS2C extends TMOPacket {
    void handle(MinecraftClient client);
}
