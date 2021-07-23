package io.github.merchantpug.toomanyorigins;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.apace100.apoli.util.NamespaceAlias;
import io.github.merchantpug.toomanyorigins.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

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
		TMOSounds.register();

		NamespaceAlias.addAlias(MODID, "apugli");
	}
}
