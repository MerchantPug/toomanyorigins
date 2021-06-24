package io.github.merchantpug.toomanyorigins;

import io.github.apace100.apoli.util.NamespaceAlias;
import io.github.merchantpug.toomanyorigins.registry.*;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);

	@Override
	public void onInitialize() {
		LOGGER.info("TooManyOrigins is initializing. Enjoy!");
		TMOBlocks.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.init();

		NamespaceAlias.addAlias(MODID, "apugli");
	}
}
