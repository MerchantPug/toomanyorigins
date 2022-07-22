package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.blocks.WitheredCropBlock;
import com.github.merchantpug.toomanyorigins.blocks.WitheredStemBlock;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import com.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOBlocks {
    public static Block WITHERED_CROP;
    public static Block WITHERED_STEM;

    public static void register() {
        if (TooManyOriginsConfig.legacyWitheredEnabled) {
            WITHERED_CROP = register("withered_crop", new WitheredCropBlock(() -> TMOItems.WITHERED_CROP_SEEDS, FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
            WITHERED_STEM = register("withered_stem", new WitheredStemBlock(() -> TMOItems.WITHERED_STEM_SEEDS, FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM)));
        }
    }

    public static Block register(String blockName, Block block) {
        return register(blockName, block, false, null);
    }

    public static Block register(String blockName, Block block, boolean withBlockItem, ItemGroup itemGroup) {
        if(withBlockItem) {
            Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, blockName), new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
        return Registry.register(Registry.BLOCK, new Identifier(TooManyOrigins.MODID, blockName), block);
    }
}
