package io.github.merchantpug.toomanyorigins.config;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyOrigins.MODID)
public class ClientConfig implements ConfigData {
    public boolean showPlayerOverlays = true;
    public boolean showSoulShieldOverlay = true;

    @ConfigEntry.BoundedDiscrete(max = 1)
    public float soulShieldOverlayStrength = 0.15F;

    @Override
    public void validatePostLoad() {
        if (soulShieldOverlayStrength < 0F) {
            soulShieldOverlayStrength = 0F;
        } else if (soulShieldOverlayStrength > 1F) {
            soulShieldOverlayStrength = 1F;
        }
    }
}
