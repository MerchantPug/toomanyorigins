package net.merchantpug.toomanyorigins.util;

import eu.midnightdust.lib.config.MidnightConfig;

// TODO: Introduce an in-house config library.
public class TooManyOriginsConfig extends MidnightConfig {
    @Server
    public static boolean shouldFireballDamageUndead = true;

    @Server
    public static boolean performVersionCheck = true;
}
