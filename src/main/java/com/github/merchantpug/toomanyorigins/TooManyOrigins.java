package com.github.merchantpug.toomanyorigins;

import com.github.merchantpug.toomanyorigins.registry.*;
import io.github.apace100.apoli.util.NamespaceAlias;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import com.github.merchantpug.apugli.Apugli;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);
	public static String VERSION = "";
	public static int[] SEMVER;

	public static TooManyOriginsConfig config;

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
		TMOBlocks.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.register();
		TMOSounds.register();

		Apugli.addDependentModid(MODID);
		NamespaceAlias.addAlias(MODID, "apugli");

		AutoConfig.register(TooManyOriginsConfig.class, JanksonConfigSerializer::new);
		TooManyOrigins.config = AutoConfig.getConfigHolder(TooManyOriginsConfig.class).getConfig();
	}
}
