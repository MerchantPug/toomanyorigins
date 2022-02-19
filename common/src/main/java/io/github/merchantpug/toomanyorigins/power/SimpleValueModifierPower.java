package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.util.AttributeUtil;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.apugli.util.ModComponents;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class SimpleValueModifierPower extends ValueModifyingPower {
    public static PowerFactory<?> createFactory() {
        return new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "simple_modifier"),
                new SerializableData()
                        .add("modifier", SerializableDataType.ATTRIBUTE_MODIFIER, null)
                        .add("modifiers", SerializableDataType.ATTRIBUTE_MODIFIERS, null),
                data ->
                        (type, player) -> {
                            SimpleValueModifierPower power = new SimpleValueModifierPower(type, player);
                            if (data.isPresent("modifier")) {
                                power.addModifier(data.getModifier("modifier"));
                            }
                            if (data.isPresent("modifiers")) {
                                ((List<EntityAttributeModifier>)data.get("modifiers")).forEach(power::addModifier);
                            }
                            return power;
                        })
                .allowCondition();
    }

    public static <T extends ValueModifyingPower> double modify(Entity entity, PowerType<T> powerType, double baseValue) {
        if(entity instanceof PlayerEntity) {
            ValueModifyingPower thisPower = ModComponents.getOriginComponent((PlayerEntity) entity).getPower(powerType);
            return AttributeUtil.sortAndApplyModifiers(thisPower.getModifiers(), baseValue);
        }
        return baseValue;
    }

    public SimpleValueModifierPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }
}
