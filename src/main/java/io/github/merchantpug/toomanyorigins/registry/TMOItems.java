package io.github.merchantpug.toomanyorigins.registry;

import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.items.DragonFireballItem;

public class TMOItems {
    public static final Item DRAGON_FIREBALL = new DragonFireballItem((new Item.Settings()).maxCount(16).group(ItemGroup.COMBAT));
    public static final Item WITHERED_CROP_SEEDS = new AliasedBlockItem(TMOBlocks.WITHERED_CROP, (new Item.Settings()).group(ItemGroup.MATERIALS));
    public static final Item WITHERED_STEM_SEEDS = new AliasedBlockItem(TMOBlocks.WITHERED_STEM, (new Item.Settings()).group(ItemGroup.MATERIALS));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, "dragon_fireball"), DRAGON_FIREBALL);
        Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, "withered_crop_seeds"), WITHERED_CROP_SEEDS);
        Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, "withered_stem_seeds"), WITHERED_STEM_SEEDS);
    }
}
