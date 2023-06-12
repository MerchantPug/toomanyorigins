package net.merchantpug.toomanyorigins.network.s2c;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public record SyncLegacyContentPacket(Set<String> enabledModules) implements TMOPacketS2C {
    public static final ResourceLocation ID = TooManyOrigins.asResource("sync_legacy_content");

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(enabledModules().size());
        enabledModules().forEach(buf::writeUtf);
    }

    public static SyncLegacyContentPacket decode(FriendlyByteBuf buf) {
        int enabledContentSize = buf.readInt();
        Set<String> enabledModules = new HashSet<>();
        for (int i = 0; i < enabledContentSize; ++i) {
            enabledModules.add(buf.readUtf());
        }
        return new SyncLegacyContentPacket(enabledModules);
    }

    @Override
    public void handle() {
        // TODO: This
        Minecraft.getInstance().execute(() -> {
            // LegacyContentRegistry.disableAll();
            // enabledModules().forEach(LegacyContentRegistry::enable);
        });
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}