package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.items.DragonFireballItem;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOItems {

    public static final Item DRAGON_FIREBALL = registerItem("dragon_fireball",
            new DragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT)));

    public static final Item WITHERED_CROP_SEEDS = registerItem("withered_crop_seeds",
            new AliasedBlockItem(TMOBlocks.WITHERED_CROP, (new Item.Settings()).group(ItemGroup.MATERIALS)));

    public static final Item WITHERED_STEM_SEEDS = registerItem("withered_stem_seeds",
            new AliasedBlockItem(TMOBlocks.WITHERED_STEM, (new Item.Settings()).group(ItemGroup.MATERIALS)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, name), item);
    }
    public static void register() {

    }
}
