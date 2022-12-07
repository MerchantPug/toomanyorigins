package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.content.item.legacy.LegacyDragonFireballItem;
import com.github.merchantpug.toomanyorigins.content.item.legacy.WitheredCropAliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOItems {

    public static final Item DRAGON_FIREBALL = registerItem("dragon_fireball",
            new LegacyDragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT)));;

    public static final Item WITHERED_CROP_SEEDS = registerItem("withered_crop_seeds",
            new WitheredCropAliasedBlockItem(TMOBlocks.WITHERED_CROP, (new Item.Settings()).group(ItemGroup.MATERIALS)));

    public static final Item WITHERED_STEM_SEEDS = registerItem("withered_stem_seeds",
            new WitheredCropAliasedBlockItem(TMOBlocks.WITHERED_STEM, (new Item.Settings()).group(ItemGroup.MATERIALS)));;


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, name), item);
    }
    public static void register() {
    }
}
