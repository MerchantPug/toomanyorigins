package net.merchantpug.toomanyorigins.networking.c2s;

import net.merchantpug.toomanyorigins.networking.TMOPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public interface TMOPacketC2S extends TMOPacket {
    void handle(MinecraftServer server, ServerPlayer player);
}
