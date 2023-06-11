package net.merchantpug.toomanyorigins;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.merchantpug.toomanyorigins.networking.TMOPackets;
import net.merchantpug.toomanyorigins.registry.TMOItems;
import net.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

public class TooManyOriginsFabric implements ModInitializer {
	public static String VERSION = "";
	public static int[] SEMVER;
	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getModContainer(TooManyOrigins.MOD_ID).ifPresent(modContainer -> {
			VERSION = modContainer.getMetadata().getVersion().getFriendlyString();
			if(VERSION.contains("+")) {
				VERSION = VERSION.split("\\+")[0];
			}
			if(VERSION.contains("-")) {
				VERSION = VERSION.split("-")[0];
			}
			String[] splitVersion = VERSION.split("\\.");
			SEMVER = new int[splitVersion.length];
			for(int i = 0; i < SEMVER.length; i++) {
				SEMVER[i] = Integer.parseInt(splitVersion[i]);
			}
		});
		TooManyOrigins.LOG.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");
		TooManyOrigins.init();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> entries.accept(TMOItems.DRAGON_FIREBALL.get()));
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.accept(TMOItems.WITHERED_CROP_SEEDS.get()));
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> entries.accept(TMOItems.WITHERED_STEM_SEEDS.get()));

		MidnightConfig.init(TooManyOrigins.MOD_ID, TooManyOriginsConfig.class);

		TMOPackets.registerC2S();
	}

}
