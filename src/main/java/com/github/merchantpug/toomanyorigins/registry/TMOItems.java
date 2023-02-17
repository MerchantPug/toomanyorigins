package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.content.legacy.item.LegacyDragonFireballItem;
import com.github.merchantpug.toomanyorigins.content.legacy.item.WitheredCropAliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOItems {

    public static final Item DRAGON_FIREBALL = registerItem("dragon_fireball",
            new LegacyDragonFireballItem((new Item.Settings()).maxCount(16)));;

    public static final Item WITHERED_CROP_SEEDS = registerItem("withered_crop_seeds",
            new WitheredCropAliasedBlockItem(TMOBlocks.WITHERED_CROP, (new Item.Settings())));

    public static final Item WITHERED_STEM_SEEDS = registerItem("withered_stem_seeds",
            new WitheredCropAliasedBlockItem(TMOBlocks.WITHERED_STEM, (new Item.Settings())));;


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TooManyOrigins.MODID, name), item);
    }
    public static void register() {
    }
}
