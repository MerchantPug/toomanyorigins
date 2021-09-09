package io.github.merchantpug.toomanyorigins.mixin;

import com.mojang.authlib.GameProfile;
import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.power.SetTexturePower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    private void getModel(CallbackInfoReturnable<String> cir) {
        if (OriginComponent.hasPower(this, SetTexturePower.class)) {
            if (OriginComponent.getPowers(this, SetTexturePower.class).get(0).model.equals("slim") || OriginComponent.getPowers(this, SetTexturePower.class).get(0).model.equals("default")) {
                cir.setReturnValue(OriginComponent.getPowers(this, SetTexturePower.class).get(0).model);
            }
        }
    }
}
