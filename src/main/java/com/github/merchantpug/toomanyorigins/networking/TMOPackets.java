package com.github.merchantpug.toomanyorigins.networking;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.util.Identifier;

public class TMOPackets {
    public static final Identifier HANDSHAKE = TooManyOrigins.identifier("handshake");

    public static final Identifier SYNC_LEGACY_CONTENT = TooManyOrigins.identifier("sync_legacy_content");
}
