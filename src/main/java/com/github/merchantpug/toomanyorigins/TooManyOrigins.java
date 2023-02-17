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

package com.github.merchantpug.toomanyorigins;

import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.networking.TMOPackets;
import com.github.merchantpug.toomanyorigins.networking.s2c.SyncLegacyContentPacket;
import com.github.merchantpug.toomanyorigins.registry.*;
import eu.midnightdust.lib.config.MidnightConfig;
import io.github.apace100.apoli.util.NamespaceAlias;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger("TooManyOrigins");
	public static String VERSION = "";
	public static int[] SEMVER;

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
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

			ResourceManagerHelper.registerBuiltinResourcePack(TooManyOrigins.identifier("legacytoomanyorigins"), modContainer, "Legacy TooManyOrigins", ResourcePackActivationType.NORMAL);
		});
		LOGGER.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");

		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
			Set<String> stringList = LegacyContentRegistry.asStream().filter(Map.Entry::getValue).map(Map.Entry::getKey).collect(Collectors.toSet());
			TMOPackets.sendS2C(new SyncLegacyContentPacket(stringList), player);
		});

		MidnightConfig.init(TooManyOrigins.MODID, TooManyOriginsConfig.class);
		LegacyContentRegistry.init();

		TMOBadges.register();
		TMOBlocks.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.register();
		TMOSounds.register();

		NamespaceAlias.addAlias(MODID, "apugli");

		TMOPackets.registerC2S();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MODID, path);
	}
}
