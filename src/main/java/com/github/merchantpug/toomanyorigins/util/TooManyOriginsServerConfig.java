package com.github.merchantpug.toomanyorigins.util;

import com.github.merchantpug.apugli.Apugli;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyOrigins.MODID + "_server")
public class TooManyOriginsServerConfig implements ConfigData {
    public boolean performVersionCheck = true;
}
