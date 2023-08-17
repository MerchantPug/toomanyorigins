package net.merchantpug.toomanyorigins;

import io.github.apace100.apoli.power.PowerTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.merchantpug.toomanyorigins.data.LegacyContentManagerFabric;
import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.merchantpug.toomanyorigins.network.s2c.SyncLegacyContentPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;

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
			ResourceManagerHelper.registerBuiltinResourcePack(TooManyOrigins.asResource("legacytoomanyorigins"), modContainer, "Legacy TooManyOrigins", ResourcePackActivationType.NORMAL);
		});

		TooManyOrigins.LOG.info("TooManyOrigins " + VERSION + " is initializing. Enjoy!");
		TooManyOrigins.init();

		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
			var packet = new SyncLegacyContentPacket(LegacyContentRegistry.isDragonFireballEnabled(), LegacyContentRegistry.areWitheredCropsEnabled(), LegacyContentRegistry.isZombifyingEffectEnabled());
			ServerPlayNetworking.send(player, packet.getFabricId(), packet.toBuf());
		});

		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new LegacyContentManagerFabric());

		FabricLoader.getInstance().getModContainer("origins").ifPresent(modContainer -> {
			if (!modContainer.getMetadata().getVersion().getFriendlyString().equals("1.7.1")) return;
			PowerTypes.DEPENDENCIES.add(TooManyOrigins.asResource("legacy_content"));
		});
	}

}
