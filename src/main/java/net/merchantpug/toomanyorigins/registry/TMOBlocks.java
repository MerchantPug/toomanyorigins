package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.blocks.WitheredCropBlock;
import net.merchantpug.toomanyorigins.blocks.WitheredStemBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOBlocks {
    public static final Block WITHERED_CROP = new WitheredCropBlock(() -> TMOItems.WITHERED_CROP_SEEDS, FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_STEM = new WitheredStemBlock(() -> TMOItems.WITHERED_STEM_SEEDS, FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM));

    public static void register() {
        register("withered_crop", WITHERED_CROP);
        register("withered_stem", WITHERED_STEM);
    }

    public static void register(String blockName, Block block) {
        register(blockName, block, false, null);
    }

    public static void register(String blockName, Block block, boolean withBlockItem, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(TooManyOrigins.MODID, blockName), block);
        if(withBlockItem) {
            Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, blockName), new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
    }
}
