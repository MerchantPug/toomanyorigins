package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.power.DamageFromDragonFireballPower;
import net.merchantpug.toomanyorigins.power.factory.ModifyDragonFireballPowerFactory;
import net.merchantpug.toomanyorigins.power.factory.SimplePowerFactory;
import net.merchantpug.toomanyorigins.power.factory.SpecialPowerFactory;

import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
public class TMOPowers {
    public static final Supplier<ModifyDragonFireballPowerFactory> MODIFY_DRAGON_FIREBALL = register("modify_dragon_fireball", ModifyDragonFireballPowerFactory.class);
    public static final Supplier<DamageFromDragonFireballPower.Factory> TAKE_DAMAGE_FROM_ENDER_DRAGON_FIREBALL = register(new DamageFromDragonFireballPower.Factory());

    public static void register() {

    }

    public static <F extends SimplePowerFactory<?>> Supplier<F> register(F factory) {
        return Services.POWER.registerFactory(factory);
    }

    public static <F extends SpecialPowerFactory> Supplier<F> register(String name, Class<F> iface) {
        return Services.POWER.registerFactory(name, iface);
    }

}