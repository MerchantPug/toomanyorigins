package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, CommandOutput {
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    public void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.isExplosive() && TMOPowers.BLAST_IMMUNITY.isActive((Entity)(Object)this)) {
            cir.setReturnValue(true);
        }
    }
}
