package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOComponents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {
    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(method = "onPlayerConnected", at = @At("HEAD"))
    private void healPlayerOnConnect(ServerPlayerEntity player, CallbackInfo ci) {
        if (TMOComponents.getExtraHealth(player) > 0) {
            player.heal((float)TMOComponents.getExtraHealth(player));
            TMOComponents.setExtraHealth(player, 0);
        }
    }
    @Inject(method = "onPlayerRespawned", at = @At("HEAD"))
    private void onPlayerRespawned(ServerPlayerEntity player, CallbackInfo ci) {
        player.heal(Float.MAX_VALUE);
    }
}
