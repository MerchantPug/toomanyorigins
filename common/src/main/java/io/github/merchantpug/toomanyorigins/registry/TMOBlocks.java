package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.registry.ModRegistriesArchitectury;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.blocks.*;
import me.shedaniel.architectury.registry.BlockProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TMOBlocks {
    public static final Block WITHERED_BEETROOTS = new WitheredCropBlock(() -> Items.BEETROOT_SEEDS, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_CARROTS = new WitheredCropBlock(() -> Items.CARROT, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_POTATOES = new WitheredCropBlock(() -> Items.POTATO, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_MELON_STEM = new WitheredStemBlock(() -> Items.MELON_SEEDS, (AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM)));
        public static final Block WITHERED_PUMPKIN_STEM = new WitheredStemBlock(() -> Items.PUMPKIN_SEEDS, (AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM)));
    public static final Block WITHERED_WHEAT = new WitheredCropBlock(() -> Items.WHEAT_SEEDS, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

    public static final Block WITHERED_CROP = new WitheredCropBlock(() -> TMOItems.WITHERED_CROP_SEEDS, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_STEM = new WitheredStemBlock(() -> TMOItems.WITHERED_STEM_SEEDS, BlockProperties.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM));


    public static void register() {
        register("withered_beetroots", WITHERED_BEETROOTS, false, null);
        register("withered_carrots", WITHERED_CARROTS, false, null);
        register("withered_potatoes", WITHERED_POTATOES, false, null);
        register("withered_melon_stem", WITHERED_MELON_STEM, false, null);
        register("withered_pumpkin_stem", WITHERED_PUMPKIN_STEM, false, null);
        register("withered_wheat", WITHERED_WHEAT, false, null);
    }

    public static void register(String blockName, Block block)
    {
        register(blockName, block, true, null);
    }

    public static void register(String blockName, Block block, boolean withBlockItem, ItemGroup itemGroup) {
        TMORegistriesArchitectury.BLOCKS.register(new Identifier(TooManyOrigins.MODID, blockName), () -> block);
        if(withBlockItem) {
            TMORegistriesArchitectury.ITEMS.register(new Identifier(TooManyOrigins.MODID, blockName), () -> new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
    }
}
