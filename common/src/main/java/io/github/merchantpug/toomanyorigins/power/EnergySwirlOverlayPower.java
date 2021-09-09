package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class EnergySwirlOverlayPower extends Power {
    private final Identifier textureLocation;
    private final float speed;

    public EnergySwirlOverlayPower(PowerType<?> type, PlayerEntity player, Identifier textureLocation, float speed) {
        super(type, player);
        this.textureLocation = textureLocation;
        this.speed = speed;
    }

    public Identifier getTextureLocation() {
        return textureLocation;
    }

    public float getSpeed() {
        return speed;
    }
}
