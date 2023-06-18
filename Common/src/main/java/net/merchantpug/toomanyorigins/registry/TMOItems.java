package net.merchantpug.toomanyorigins.registry;

import net.merchantpug.toomanyorigins.items.DragonFireballItem;
import net.merchantpug.toomanyorigins.registry.services.RegistrationProvider;
import net.merchantpug.toomanyorigins.registry.services.RegistryObject;
import net.minecraft.core.Registry;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

import java.util.function.Supplier;

public class TMOItems {
    private static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, TooManyOrigins.MOD_ID);

    public static final RegistryObject<Item> DRAGON_FIREBALL = register("dragon_fireball",
            () -> new DragonFireballItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> WITHERED_CROP_SEEDS = register("withered_crop_seeds",
            () -> new ItemNameBlockItem(TMOBlocks.WITHERED_CROP.get(), (new Item.Properties())));

    public static final RegistryObject<Item> WITHERED_STEM_SEEDS = register("withered_stem_seeds",
            () -> new ItemNameBlockItem(TMOBlocks.WITHERED_STEM.get(), (new Item.Properties())));


    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void register() {

    }
}
