package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.items.DragonFireballItem;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOItems {

    public static Item DRAGON_FIREBALL;

    public static Item WITHERED_CROP_SEEDS;

    public static Item WITHERED_STEM_SEEDS;


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, name), item);
    }
    public static void register() {
        if (TooManyOriginsConfig.legacyDragonbornEnabled) {
            DRAGON_FIREBALL = registerItem("dragon_fireball",
                    new DragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT)));
        }
        if (TooManyOriginsConfig.legacyWitheredEnabled) {
           WITHERED_CROP_SEEDS = registerItem("withered_crop_seeds",
                    new AliasedBlockItem(TMOBlocks.WITHERED_CROP, (new Item.Settings()).group(ItemGroup.MATERIALS)));
           WITHERED_STEM_SEEDS = registerItem("withered_stem_seeds",
                    new AliasedBlockItem(TMOBlocks.WITHERED_STEM, (new Item.Settings()).group(ItemGroup.MATERIALS)));
        }
    }
}
