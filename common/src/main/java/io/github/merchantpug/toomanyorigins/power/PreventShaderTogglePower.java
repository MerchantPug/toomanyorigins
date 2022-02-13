package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class PreventShaderTogglePower extends Power {
    private final Identifier shaderLocation;

    public static PowerFactory<?> createFactory() {
        return new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "prevent_shader_toggle"),
                new SerializableData()
                        .add("shader", SerializableDataType.IDENTIFIER),
                data ->
                        (type, player) -> {
                            return new PreventShaderTogglePower(type, player, data.getId("shader"));
                        })
                .allowCondition();
    }

    public Identifier getShaderLocation() {
        return shaderLocation;
    }

    public PreventShaderTogglePower(PowerType<?> type, PlayerEntity player, Identifier shaderLocation) {
        super(type, player);
        this.shaderLocation = shaderLocation;
    }
}
