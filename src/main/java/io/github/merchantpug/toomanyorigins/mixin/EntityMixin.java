package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, CommandOutput {
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    public void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if ((Entity)(Object)this instanceof LivingEntity) {
            if (damageSource.isExplosive() && TMOPowers.BLAST_IMMUNITY.isActive((LivingEntity)(Object)this)) {
                cir.setReturnValue(true);
            } else if (damageSource.isProjectile() && ((LivingEntity)(Object)this).hasStatusEffect(TMOEffects.SOUL_SHIELD)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"))
    private void onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if ((Entity)(Object)this instanceof LivingEntity) {
            if (TMOPowers.CONDUCTOR.isActive((LivingEntity)(Object)this)) {
                ((LivingEntity)(Object)this).addStatusEffect(new StatusEffectInstance(TMOEffects.CHARGED, 48000, 0));
            }
        }
    }
}
