package net.merchantpug.toomanyorigins.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModifyDragonFireballPower extends ValueModifyingPower {
    private final float minRadius;
    private final float maxRadius;
    private final int duration;

    public static PowerFactory<?> createFactory() {
        return new PowerFactory<ModifyDragonFireballPower>(new Identifier(TooManyOrigins.MODID, "modify_dragon_fireball"),
                new SerializableData()
                        .add("damage_modifier", Modifier.DATA_TYPE, null)
                        .add("damage_modifiers", Modifier.LIST_TYPE, null)
                        .add("min_radius", SerializableDataTypes.FLOAT, 1.125F)
                        .add("max_radius", SerializableDataTypes.FLOAT, 2.25F)
                        .add("duration", SerializableDataTypes.INT, 60),
                data ->
                        (type, entity) -> {
                            ModifyDragonFireballPower power = new ModifyDragonFireballPower(type, entity, data.getFloat("min_radius"), data.getFloat("max_radius"), data.getInt("duration"));
                            if (data.isPresent("damage_modifier")) {
                                power.addModifier(data.get("damage_modifier"));
                            }
                            if (data.isPresent("damage_modifiers")) {
                                ((List<Modifier>)data.get("damage_modifiers")).forEach(power::addModifier);
                            }
                            return power;
                        })
                .allowCondition();
    }

    public ModifyDragonFireballPower(PowerType<?> type, LivingEntity entity, float minRadius, float maxRadius, int duration) {
        super(type, entity);
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
