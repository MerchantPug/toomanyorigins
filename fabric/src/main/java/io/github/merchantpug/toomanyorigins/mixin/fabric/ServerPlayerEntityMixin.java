package io.github.merchantpug.toomanyorigins.mixin.fabric;

import com.mojang.authlib.GameProfile;
import io.github.merchantpug.toomanyorigins.power.SimpleValueModifierPower;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(world, blockPos, f, gameProfile);
    }

    @ModifyArg(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private float modifyDamageAmount(DamageSource source, float originalAmount) {
        if (TMOPowers.LOOSE_SCALES.isActive(this)) {
            Vec3d vector3d2 = source.getPosition();
            if (vector3d2 != null) {
                Vec3d vector3d = this.getRotationVec(1.0F);
                Vec3d vector3d1 = vector3d2.reverseSubtract(this.getPos()).normalize();
                vector3d1 = new Vec3d(vector3d1.x, 0.0D, vector3d1.z);
                if (vector3d1.dotProduct(vector3d) > 0.0D) {
                    return (float)SimpleValueModifierPower.modify(this, TMOPowers.LOOSE_SCALES, originalAmount);
                }
            }
        }
        return originalAmount;
    }
}