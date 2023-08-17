package net.merchantpug.toomanyorigins;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class TooManyOriginsFabric implements ModInitializer {
	public static String VERSION = "";

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
		});

		TooManyOrigins.LOG.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");
		TooManyOrigins.init();
	}

}
