package com.github.merchantpug.toomanyorigins.util;

import eu.midnightdust.lib.config.MidnightConfig;

public class TooManyOriginsConfig extends MidnightConfig {
    @Comment
    public static Comment dragonFireballMixinOptions;
    @Entry
    public static boolean shouldFireballDamageUndead = true;
}
