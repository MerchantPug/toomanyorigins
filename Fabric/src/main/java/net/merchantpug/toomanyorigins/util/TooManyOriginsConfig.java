package net.merchantpug.toomanyorigins.util;

import eu.midnightdust.lib.config.MidnightConfig;

// TODO: Introduce an in-house config library.
public class TooManyOriginsConfig extends MidnightConfig {
    @Server @Entry
    public static boolean performVersionCheck = true;
}
