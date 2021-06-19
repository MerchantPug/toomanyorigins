package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.merchantpug.toomanyorigins.power.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TMOPowers {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerType<Power> BLACK_THUMB = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "black_thumb"));

    public static final PowerFactory<Power> MODIFY_DRAGON_FIREBALL = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "modify_dragon_fireball"),
            new SerializableData()
                    .add("damage_modifier", SerializableDataTypes.ATTRIBUTE_MODIFIER, null)
                    .add("damage_modifiers", SerializableDataTypes.ATTRIBUTE_MODIFIERS, null)
                    .add("min_radius", SerializableDataTypes.FLOAT, 1.125F)
                    .add("max_radius", SerializableDataTypes.FLOAT, 2.25F)
                    .add("duration", SerializableDataTypes.INT, 60),
            data ->
                    (type, entity) -> {
                        ModifyDragonFireballPower power = new ModifyDragonFireballPower(type, entity, data.getFloat("min_radius"), data.getFloat("max_radius"), data.getInt("duration"));
                        if (data.isPresent("damage_modifier")) {
                            power.addModifier(data.getModifier("damage_modifier"));
                        }
                        if (data.isPresent("damage_modifiers")) {
                            ((List<EntityAttributeModifier>)data.get("damage_modifiers")).forEach(power::addModifier);
                        }
                        return power;
                    })
            .allowCondition());

    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ApoliRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}
