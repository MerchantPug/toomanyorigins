package net.merchantpug.toomanyorigins.data;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.resources.ResourceLocation;

public class LegacyContentManagerFabric extends LegacyContentManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return TooManyOrigins.asResource("legacy_content");
    }
}
