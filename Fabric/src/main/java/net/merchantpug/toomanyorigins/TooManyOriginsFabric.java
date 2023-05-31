/*
MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

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
