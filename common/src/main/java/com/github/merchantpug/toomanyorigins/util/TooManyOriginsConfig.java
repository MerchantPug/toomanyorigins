package com.github.merchantpug.toomanyorigins.util;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyOrigins.MODID)
public class TooManyOriginsConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public DragonFireballMixinConfig dragonFireballMixin = new DragonFireballMixinConfig();

    public static class DragonFireballMixinConfig {
        public boolean shouldFireballDamageUndead = true;
    }
}
