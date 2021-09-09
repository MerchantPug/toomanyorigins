package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.*;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.util.AttributedEntityAttributeModifier;
import io.github.apace100.origins.util.HudRender;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.power.*;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.util.TMODataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class TMOPowers {
    public static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerType<Power> BLACK_THUMB = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "black_thumb"));
    public static final PowerType<TargetActionOnHitPower> DEATHLY_BITE = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "deathly_bite"));

    public static final PowerFactory<Power> TMO_ENTITY_GROUP = create(new PowerFactory<>(TooManyOrigins.identifier("entity_group"),
            new SerializableData()
                    .add("group", TMODataTypes.TMO_ENTITY_GROUP),
            data ->
                    (type, entity) ->
                            new SetTMOEntityGroupPower(type, entity, (EntityGroup)data.get("group")))
            .allowCondition());

    public static final PowerFactory<Power> MODIFY_SOUL_SPEED = create(new PowerFactory<>(TooManyOrigins.identifier("modify_soul_speed"),
            new SerializableData()
                    .add("modifier", SerializableDataType.ATTRIBUTE_MODIFIER, null)
                    .add("modifiers", SerializableDataType.ATTRIBUTE_MODIFIERS, null)
                    .add("block_condition", SerializableDataType.BLOCK_CONDITION, null),
            data ->
                    (type, entity) -> {
                        ModifySoulSpeedPower power = new ModifySoulSpeedPower(type, entity, (ConditionFactory<CachedBlockPosition>.Instance)data.get("block_condition"));
                        if(data.isPresent("modifier")) {
                            power.addModifier(data.getModifier("modifier"));
                        }
                        if(data.isPresent("modifiers")) {
                            ((List<EntityAttributeModifier>)data.get("modifiers")).forEach(power::addModifier);
                        }
                        return power;
                    })
            .allowCondition());
    public static final PowerFactory<Power> ENERGY_SWIRL = create(new PowerFactory<>(TooManyOrigins.identifier("energy_swirl"),
            new SerializableData()
                    .add("texture_location", SerializableDataType.IDENTIFIER)
                    .add("speed", SerializableDataType.FLOAT, 0.01F),
            data ->
                    (type, entity) ->
                            new EnergySwirlOverlayPower(type, entity, data.getId("texture_location"), data.getFloat("speed")))
            .allowCondition());
    public static final PowerFactory<Power> ROCKET_JUMP = create(new PowerFactory<>(TooManyOrigins.identifier("rocket_jump"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("source", SerializableDataType.DAMAGE_SOURCE, null)
                    .add("amount", SerializableDataType.FLOAT, 0.0F)
                    .add("speed", SerializableDataType.DOUBLE, 1.0D)
                    .add("use_charged", SerializableDataType.BOOLEAN, false)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            (data) ->
                    (type, entity) ->  {
                        RocketJumpPower power = new RocketJumpPower(type, entity, data.getInt("cooldown"), (HudRender)data.get("hud_render"), (DamageSource)data.get("source"), data.getFloat("amount"), data.getDouble("speed"), data.getBoolean("use_charged"));
                        power.setKey((Active.Key)data.get("key"));
                        return power;
                    })
            .allowCondition());

    public static final PowerFactory<Power> WEARABLE_STACK = create(new PowerFactory<>(TooManyOrigins.identifier("wearable_item"),
            new SerializableData()
                    .add("stack", SerializableDataType.ITEM_STACK)
                    .add("scale", SerializableDataType.FLOAT, 1.0F),
            data ->
                    (type, entity) ->
                            new WearableItemStackPower(type, entity, (ItemStack)data.get("stack"), data.getFloat("scale")))
            .allowCondition());

    public static final PowerFactory<Power> VISUAL_TIMER = create (new PowerFactory<>(TooManyOrigins.identifier("visual_timer"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("reset_on_respawn", SerializableDataType.BOOLEAN),
            data ->
                    (type, entity) ->
                            new VisualTimerPower(type, entity, data.getInt("cooldown"), (HudRender)data.get("hud_render"), data.getBoolean("reset_on_respawn")))
            .allowCondition());

    public static final PowerFactory<Power> BUNNY_HOP = create(new PowerFactory<>(TooManyOrigins.identifier("bunny_hop"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("increase_per_tick", SerializableDataType.DOUBLE, 0.000375)
                    .add("ability_velocity", SerializableDataType.INT, 5)
                    .add("max_velocity", SerializableDataType.DOUBLE, 0.015)
                    .add("tick_rate", SerializableDataType.INT, 10)
                    .add("sound", SerializableDataType.SOUND_EVENT, null)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, entity) -> {
                        BunnyHopPower power = new BunnyHopPower(type, entity, data.getInt("cooldown"), (HudRender)data.get("hud_render"), data.getDouble("increase_per_tick"), data.getInt("ability_velocity"), data.getDouble("max_velocity"), (SoundEvent)data.get("sound"), data.getInt("tick_rate"));
                        power.setKey((Active.Key)data.get("key"));
                        return power;
                    })
            .allowCondition());

    public static final PowerFactory<Power> EDIBLE_ITEM = create(new PowerFactory<>(TooManyOrigins.identifier("edible_item"),
            new SerializableData()
                    .add("item_condition", SerializableDataType.ITEM_CONDITION)
                    .add("food_component", TMODataTypes.FOOD_COMPONENT)
                    .add("use_action", TMODataTypes.EAT_ACTION, null)
                    .add("return_stack", SerializableDataType.ITEM_STACK, null)
                    .add("sound", SerializableDataType.SOUND_EVENT, null)
                    .add("entity_action", SerializableDataType.ENTITY_ACTION, null)
                    .add("tick_rate", SerializableDataType.INT, 10),
            data ->
                    (type, player) -> {
                        return new EdibleItemStackPower(type, player,
                                (ConditionFactory<ItemStack>.Instance)data.get("item_condition"),
                                (FoodComponent)data.get("food_component"),
                                (UseAction)data.get("use_action"),
                                (ItemStack)data.get("return_stack"),
                                (SoundEvent)data.get("sound"),
                                (ActionFactory<Entity>.Instance)data.get("entity_action"),
                                data.getInt("tick_rate"));
                    })
            .allowCondition());

    public static final PowerFactory<Power> SET_TEXTURE = create(new PowerFactory<>(TooManyOrigins.identifier("set_texture"),
            new SerializableData()
                    .add("texture_location", SerializableDataType.IDENTIFIER, null)
                    .add("player_model", SerializableDataType.STRING, null),
            data ->
                    (type, player) ->
                            new SetTexturePower(type, player, data.getId("texture_location"), data.getString("player_model")))
            .allowCondition());

    public static final PowerFactory<Power> MODIFY_DRAGON_FIREBALL = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "modify_dragon_fireball"),
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
            .allowCondition());

    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    @ExpectPlatform
    public static void init() {
        throw new AssertionError();
    }
}
