package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.registry.ModComponents;
import io.github.merchantpug.toomanyorigins.registry.TMOComponents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    int framesOnGround = 0;

    public PlayerEntityMixin(World world) {
        super(EntityType.PLAYER, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!this.world.isClient) {
            ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
            if (ModComponents.ORIGIN.get(player).hasAllOrigins() && TMOComponents.getNewPlayer(player)) {
                if (player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME)) == 0) {
                    player.heal(40.0F);
                }
                TMOComponents.setNewPlayer(player, false);
            }
        }
        if (onGround) {
            setFramesOnGround();
        } else {
            framesOnGround = 0;
        }
    }

    // Unused code for a future origin
    @Unique
    private void setFramesOnGround() {
        for (int i2 = 0; i2 < 3; ++i2) {
            framesOnGround += 1;
        }
    }
}
