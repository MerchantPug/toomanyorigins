package io.github.merchantpug.toomanyorigins.mixin;

import com.mojang.authlib.GameProfile;
import io.github.merchantpug.toomanyorigins.registry.TMOComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements ScreenHandlerListener {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    private void rejoinHealthOnDisconnect(CallbackInfo ci) {
        if (this.getHealth() > 20.0F) {
            int extraHealth = (int)(this.getHealth() - 20.0F);
            TMOComponents.setExtraHealth((ServerPlayerEntity)(Object)this, extraHealth);
        } else {
            TMOComponents.setExtraHealth((ServerPlayerEntity)(Object)this, 0);
        }
    }
}
