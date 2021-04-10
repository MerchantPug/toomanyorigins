package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;

public class TMOBlocks {
    public static final Block WITHERED_BEETROOTS = new WitheredBeetrootsBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_CARROTS = new WitheredCarrotsBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_POTATOES = new WitheredPotatoesBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));
    public static final Block WITHERED_MELON = new WitheredMelonBlock(FabricBlockSettings.of(Material.GOURD, MaterialColor.LIME).strength(1.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block ATTACHED_WITHERED_MELON_STEM = new AttachedWitheredStemBlock((WitheredGourdBlock)WITHERED_MELON,(AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block WITHERED_MELON_STEM = new WitheredStemBlock((WitheredGourdBlock)WITHERED_MELON,(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM)));
    public static final Block CARVED_WITHERED_PUMPKIN = new CarvedWitheredPumpkinBlock(AbstractBlock.Settings.of(Material.GOURD, MaterialColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning((state, world, pos, type) -> true));
    public static final Block WITHERED_PUMPKIN = new WitheredPumpkinBlock(FabricBlockSettings.of(Material.GOURD, MaterialColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD));
    public static final Block ATTACHED_WITHERED_PUMPKIN_STEM = new AttachedWitheredStemBlock((WitheredGourdBlock)WITHERED_PUMPKIN,(AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block WITHERED_PUMPKIN_STEM = new WitheredStemBlock((WitheredGourdBlock)WITHERED_PUMPKIN,(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.STEM)));
    public static final Block WITHERED_WHEAT = new WitheredCropBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP));

    public static void register() {
        register("withered_beetroots", WITHERED_BEETROOTS, false, null);
        register("withered_carrots", WITHERED_CARROTS, false, null);
        register("withered_potatoes", WITHERED_POTATOES, false, null);
        register("withered_melon", WITHERED_MELON, false, null);
        register("attached_withered_melon_stem", ATTACHED_WITHERED_MELON_STEM, false, null);
        register("withered_melon_stem", WITHERED_MELON_STEM, false, null);
        register("carved_withered_pumpkin", CARVED_WITHERED_PUMPKIN, false, null);
        register("withered_pumpkin", WITHERED_PUMPKIN, false, null);
        register("attached_withered_pumpkin_stem", ATTACHED_WITHERED_PUMPKIN_STEM, false, null);
        register("withered_pumpkin_stem", WITHERED_PUMPKIN_STEM, false, null);
        register("withered_wheat", WITHERED_WHEAT, false, null);
    }

    public static void register(String blockName, Block block)
    {
        register(blockName, block, true, null);
    }

    public static void register(String blockName, Block block, boolean withBlockItem, ItemGroup itemGroup) {
        Registry.register(Registry.BLOCK, new Identifier(TooManyOrigins.MODID, blockName), block);
        if(withBlockItem) {
            Registry.register(Registry.ITEM, new Identifier(TooManyOrigins.MODID, blockName), new BlockItem(block, new Item.Settings().group(itemGroup)));
        }
    }
}
