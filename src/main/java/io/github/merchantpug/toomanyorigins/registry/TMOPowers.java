package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.power.*;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.HudRender;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.power.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;

import java.util.LinkedHashMap;
import java.util.Map;

public class TMOPowers {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerFactory<Power> EXTRA_SOUL_SPEED = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "extra_soul_speed"),
            new SerializableData()
                    .add("modifier", SerializableDataType.INT),
            data ->
                    (type, player) ->
                            new ExtraSoulSpeedPower(type, player,
                                    data.getInt("modifier"))).allowCondition());
    public static final PowerFactory<Power> UNENCHANTED_SOUL_SPEED = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "unenchanted_soul_speed"),
            new SerializableData().add("modifier", SerializableDataType.INT),
            data ->
                    (type, player) ->
                            new UnenchantedSoulSpeedPower(type, player,
                                    data.getInt("modifier"))).allowCondition());
    public static final PowerType<Power> RENDER_WITHER_OVERLAY = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "undead_resistance"));
    public static final PowerType<Power> BLACK_THUMB = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "black_thumb"));

    public static final PowerFactory<Power> LIGHT_UP_BLOCK = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID,"light_up_block"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("burn_time", SerializableDataType.INT, 600)
                    .add("particle", SerializableDataType.PARTICLE_TYPE, ParticleTypes.FLAME)
                    .add("particle_count", SerializableDataType.INT, 15)
                    .add("sound", SerializableDataType.SOUND_EVENT, null)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, player) -> {
                        LightUpBlockPower power = new LightUpBlockPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"), data.getInt("burn_time"), (ParticleType)data.get("particle"), data.getInt("particle_count"), (SoundEvent)data.get("sound"));
                        power.setKey((Active.Key)data.get("key"));
                        return power;
                    })).allowCondition();

    public static final PowerFactory<Power> ROCKET_JUMP = create(new PowerFactory<>(new Identifier(TooManyOrigins.MODID, "rocket_jump"),
            new SerializableData()
                    .add("cooldown", SerializableDataType.INT)
                    .add("damage_source", SerializableDataType.DAMAGE_SOURCE, DamageSource.GENERIC)
                    .add("damage_amount", SerializableDataType.FLOAT, 3.0F)
                    .add("speed", SerializableDataType.DOUBLE, 1.0)
                    .add("hud_render", SerializableDataType.HUD_RENDER)
                    .add("key", SerializableDataType.BACKWARDS_COMPATIBLE_KEY, new Active.Key()),
            data ->
                    (type, player) -> {
            RocketJumpPower power = new RocketJumpPower(type, player, data.getInt("cooldown"), (HudRender)data.get("hud_render"), (DamageSource)data.get("damage_source"), data.getFloat("damage_amount"), data.getDouble("speed"));
            power.setKey((Active.Key)data.get("key"));
            return power;
    })).allowCondition();

    public static final PowerType<Power> BLAST_IMMUNITY = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "blast_immunity"));
    public static final PowerType<Power> CONDUCTOR = new PowerTypeReference(new Identifier(TooManyOrigins.MODID, "conductor"));

    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}
