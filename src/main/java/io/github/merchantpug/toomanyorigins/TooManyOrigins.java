package io.github.merchantpug.toomanyorigins;

import io.github.merchantpug.toomanyorigins.networking.packet.LightUpBlockEntityPacket;
import io.github.merchantpug.toomanyorigins.networking.packet.RocketJumpPacket;
import io.github.merchantpug.toomanyorigins.registry.*;
import io.github.merchantpug.toomanyorigins.registry.conditions.TMOEntityConditions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TooManyOrigins implements ModInitializer {
	public static final String MODID = "toomanyorigins";
	public static final Logger LOGGER = LogManager.getLogger(TooManyOrigins.class);

	@Override
	public void onInitialize() {
		TMOBlocks.register();
		TMOEntityConditions.register();
		TMOEffects.register();
		TMOEntities.register();
		TMOItems.register();
		TMOPowers.init();
		ServerPlayNetworking.registerGlobalReceiver(LightUpBlockEntityPacket.ID, LightUpBlockEntityPacket::handle);
		ServerPlayNetworking.registerGlobalReceiver(RocketJumpPacket.ID, RocketJumpPacket::handle);
	}
}
