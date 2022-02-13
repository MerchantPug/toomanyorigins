package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModifyDragonFireballPower extends ValueModifyingPower {
    private final float minRadius;
    private final float maxRadius;
    private final int duration;

    public static PowerFactory<?> createFactory() {
        return new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "modify_dragon_fireball"),
                new SerializableData()
                        .add("damage_modifier", SerializableDataType.ATTRIBUTE_MODIFIER, null)
                        .add("damage_modifiers", SerializableDataType.ATTRIBUTE_MODIFIERS, null)
                        .add("min_radius", SerializableDataType.FLOAT, 1.125F)
                        .add("max_radius", SerializableDataType.FLOAT, 2.25F)
                        .add("duration", SerializableDataType.INT, 60),
                data ->
                        (type, player) -> {
                            ModifyDragonFireballPower power = new ModifyDragonFireballPower(type, player, data.getFloat("min_radius"), data.getFloat("max_radius"), data.getInt("duration"));
                            if (data.isPresent("damage_modifier")) {
                                power.addModifier(data.getModifier("damage_modifier"));
                            }
                            if (data.isPresent("damage_modifiers")) {
                                ((List<EntityAttributeModifier>)data.get("damage_modifiers")).forEach(power::addModifier);
                            }
                            return power;
                        })
                .allowCondition();
    }

    public ModifyDragonFireballPower(PowerType<?> type, PlayerEntity player, float minRadius, float maxRadius, int duration) {
        super(type, player);
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.duration = duration;
    }

    public float getMinRadius() {
        return minRadius;
    }

    public float getMaxRadius() {
        return maxRadius;
    }

    public int getDuration() {
        return duration;
    }
}
