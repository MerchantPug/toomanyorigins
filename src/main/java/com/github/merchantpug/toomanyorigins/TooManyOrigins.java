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

import com.github.merchantpug.apugli.Apugli;
import com.github.merchantpug.apugli.util.ApugliConfig;
import com.github.merchantpug.apugli.util.ApugliServerConfig;
import com.github.merchantpug.toomanyorigins.integration.LegacyContentRegistrationEvent;
import com.github.merchantpug.toomanyorigins.networking.TMOPacketsC2S;
import com.github.merchantpug.toomanyorigins.registry.*;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsServerConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import io.github.apace100.apoli.util.NamespaceAlias;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import io.github.apace100.origins.integration.OriginDataLoadedCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger("TooManyOrigins");
	public static String VERSION = "";
	public static int[] SEMVER;

	public static boolean legacyDragonbornContentRegistered = false;
	public static boolean legacyUndeadContentRegistered = false;
	public static boolean legacyWitheredContentRegistered = false;

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
		});
		LOGGER.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");

		MidnightConfig.init(TooManyOrigins.MODID, TooManyOriginsConfig.class);

		TMOBlocks.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.register();
		TMOSounds.register();

		NamespaceAlias.addAlias(MODID, "apugli");

		TMOPacketsC2S.register();
		OriginDataLoadedCallback.EVENT.register(LegacyContentRegistrationEvent::onDataLoaded);

		if (TooManyOriginsConfig.legacyDragonbornEnabled) {
			legacyDragonbornContentRegistered = true;
		}

		if (TooManyOriginsConfig.legacyUndeadEnabled) {
			legacyUndeadContentRegistered = true;
		}

		if (TooManyOriginsConfig.legacyWitheredEnabled) {
			legacyWitheredContentRegistered = true;
		}
	}

	public static Identifier identifier(String path) {
		return new Identifier(MODID, path);
	}
}
