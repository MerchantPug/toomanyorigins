package io.github.merchantpug.toomanyorigins.config;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = TooManyOrigins.MODID)
public class ClientConfig implements ConfigData {
    public boolean showPlayerOverlays = true;
    public boolean showWitherShieldOverlay = true;

    @ConfigEntry.BoundedDiscrete(max = 1)
    public float witherShieldOverlayStrength = 0.15F;

    @Override
    public void validatePostLoad() {
        if (witherShieldOverlayStrength < 0F) {
            witherShieldOverlayStrength = 0F;
        } else if (witherShieldOverlayStrength > 1F) {
            witherShieldOverlayStrength = 1F;
        }
    }
}
