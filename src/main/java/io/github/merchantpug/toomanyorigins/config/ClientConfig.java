package io.github.merchantpug.toomanyorigins.config;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = TooManyOrigins.MODID)
public class ClientConfig implements ConfigData {
    public boolean showPlayerOverlays = true;
}
