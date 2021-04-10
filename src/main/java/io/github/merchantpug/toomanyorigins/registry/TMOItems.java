package io.github.merchantpug.toomanyorigins.registry;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.items.DragonFireballItem;

public class TMOItems {

    public static final Item DRAGON_FIREBALL = new DragonFireballItem((new Item.Settings()).maxCount(16).group(null));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, "dragon_fireball"), DRAGON_FIREBALL);
    }
}
