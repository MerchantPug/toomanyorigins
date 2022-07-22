package com.github.merchantpug.toomanyorigins.util;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import eu.midnightdust.lib.config.MidnightConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyOrigins.MODID)
public class TooManyOriginsConfig extends MidnightConfig {
    @Comment
    public static Comment dragonFireballMixinOptions;
    @Entry
    public static boolean shouldFireballDamageUndead = true;

    @Comment
    public static Comment legacyContentOptions;
    @Entry
    public static boolean legacyDragonbornEnabled = false;
    @Entry
    public static boolean legacyHareEnabled = false;
    @Entry
    public static boolean legacyHisskinEnabled = false;
    @Entry
    public static boolean legacySwarmEnabled = false;
    @Entry
    public static boolean legacyUndeadEnabled = false;
    @Entry
    public static boolean legacyWitheredEnabled = false;
}
