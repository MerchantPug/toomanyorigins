package com.github.merchantpug.toomanyorigins.networking.s2c;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

public record SyncLegacyContentPacket(Set<String> enabledModules) implements TMOPacketS2C {
    public static final Identifier ID = TooManyOrigins.identifier("sync_legacy_content");

    @Override
    public void encode(PacketByteBuf buf) {
        buf.writeInt(enabledModules().size());
        enabledModules().forEach(buf::writeString);
    }

    public static SyncLegacyContentPacket decode(PacketByteBuf buf) {
        int enabledContentSize = buf.readInt();
        HashSet<String> enabledModules = new HashSet<>();
        for (int i = 0; i < enabledContentSize; ++i) {
            enabledModules.add(buf.readString());
        }
        return new SyncLegacyContentPacket(enabledModules);
    }

    @Override
    public void handle(MinecraftClient client) {
        client.execute(() -> {
            LegacyContentRegistry.disableAll();
            enabledModules().forEach(LegacyContentRegistry::enable);
        });
    }

    @Override
    public Identifier getId() {
        return null;
    }
}
