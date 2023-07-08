package net.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.badge.BadgeFactory;
import net.merchantpug.toomanyorigins.badge.IGuiBadge;
import net.merchantpug.toomanyorigins.badge.factory.IBadgeFactory;
import net.merchantpug.toomanyorigins.badge.factory.IDefinedKeybindBadgeFactory;
import net.merchantpug.toomanyorigins.badge.factory.IGuiBadgeFactory;
import net.merchantpug.toomanyorigins.platform.Services;

import java.util.function.Supplier;

public class TMOBadges {
    public static final Supplier<BadgeFactory> DEFINED_KEYBIND = register("defined_keybind", IDefinedKeybindBadgeFactory.class);
    public static final Supplier<BadgeFactory> GUI = register("gui", IGuiBadgeFactory.class);

    public static void register() {
    }

    public static <B extends IBadgeFactory> Supplier<BadgeFactory> register(String name, Class<B> badgeClass) {
        return Services.POWER.registerBadge(name, badgeClass);
    }

}