package io.github.merchantpug.toomanyorigins.forge;

import me.shedaniel.architectury.platform.forge.EventBuses;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.maven.artifact.versioning.ArtifactVersion;

@Mod(TooManyOrigins.MODID)
public class TooManyOriginsForge {
    public TooManyOriginsForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(TooManyOrigins.MODID, FMLJavaModLoadingContext.get().getModEventBus());

        ArtifactVersion version = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion();
        TooManyOrigins.VERSION = version.toString();

        TooManyOrigins.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> TooManyOriginsForgeClient::initialize);
    }
}
