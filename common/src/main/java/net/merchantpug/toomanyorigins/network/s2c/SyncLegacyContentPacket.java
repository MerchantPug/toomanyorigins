package net.merchantpug.toomanyorigins.network.s2c;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public record SyncLegacyContentPacket(boolean dragonfireball,
                                      boolean witheredCrops,
                                      boolean zombifying) implements TMOPacketS2C {
    public static final ResourceLocation ID = TooManyOrigins.asResource("sync_legacy_content");

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(dragonfireball);
        buf.writeBoolean(witheredCrops);
        buf.writeBoolean(zombifying);
    }

    public static SyncLegacyContentPacket decode(FriendlyByteBuf buf) {
        boolean dragonfireball = buf.readBoolean();
        boolean witheredCrops = buf.readBoolean();
        boolean zombifying = buf.readBoolean();
        return new SyncLegacyContentPacket(dragonfireball, witheredCrops, zombifying);
    }

    @Override
    public void handle() {
        Minecraft.getInstance().execute(() -> {
            LegacyContentRegistry.setRecord(dragonfireball, witheredCrops, zombifying);
        });
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}