package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class SetTexturePower extends Power {
    public final Identifier textureLocation;
    public final String model;

    public SetTexturePower(PowerType<?> type, PlayerEntity entity, Identifier textureLocation, String model) {
        super(type, entity);
        this.textureLocation = textureLocation;
        this.model = model;
    }
}
