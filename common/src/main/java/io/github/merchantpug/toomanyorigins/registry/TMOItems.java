package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.items.DragonFireballItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TMOItems {

    public static final Item DRAGON_FIREBALL = new DragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT));

    public static void register() {
        TMORegistriesArchitectury.ITEMS.register(new Identifier(TooManyOrigins.MODID, "dragon_fireball"), () -> DRAGON_FIREBALL);
    }
}
