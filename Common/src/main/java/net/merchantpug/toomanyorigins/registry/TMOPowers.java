package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.power.factory.ModifyDragonFireballPowerFactory;
import net.merchantpug.toomanyorigins.power.factory.SpecialPowerFactory;

import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
public class TMOPowers {
    public static final Supplier<ModifyDragonFireballPowerFactory> MODIFY_DRAGON_FIREBALL = register("modify_dragon_fireball", ModifyDragonFireballPowerFactory.class);

    public static void register() {

    }

    public static <F extends SpecialPowerFactory> Supplier<F> register(String name, Class<F> iface) {
        return Services.POWER.registerFactory(name, iface);
    }

}