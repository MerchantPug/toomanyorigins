package io.github.merchantpug.toomanyorigins;

import io.github.apace100.apoli.util.NamespaceAlias;
import io.github.merchantpug.toomanyorigins.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);
	public static String VERSION = "";

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
		});
		LOGGER.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");
		TMOBlocks.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.register();
		TMOSounds.register();

		NamespaceAlias.addAlias(MODID, "apugli");
	}
}
