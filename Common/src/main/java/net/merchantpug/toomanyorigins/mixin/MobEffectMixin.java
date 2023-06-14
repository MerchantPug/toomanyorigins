package net.merchantpug.toomanyorigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEffect.class)
public class MobEffectMixin {
    private Entity toomanyorigins$indirectSource;
    private LivingEntity toomanyorigins$target;

    @Inject(method = "applyInstantenousEffect", at = @At("HEAD"))
    private void toomanyorigins$captureInstantEffectParams(Entity source, Entity indirectSource, LivingEntity livingEntity, int amplifier, double health, CallbackInfo ci) {
        toomanyorigins$indirectSource = indirectSource;
        toomanyorigins$target = livingEntity;
    }

    @ModifyExpressionValue(method = "applyInstantenousEffect", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/LivingEntity;isInvertedHealAndHarm()Z", ordinal = 0))
    private boolean toomanyorigins$shouldApplyInstantEffect0(boolean original) {
        return original || !(toomanyorigins$indirectSource instanceof EnderDragon && Services.POWER.hasPower(toomanyorigins$target, TMOPowers.TAKE_DAMAGE_FROM_ENDER_DRAGON_FIREBALL.get()));
    }

    @ModifyExpressionValue(method = "applyInstantenousEffect", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/LivingEntity;isInvertedHealAndHarm()Z", ordinal = 1))
    private boolean toomanyorigins$shouldApplyInstantEffect1(boolean original) {
        return original || !(toomanyorigins$indirectSource instanceof EnderDragon && Services.POWER.hasPower(toomanyorigins$target, TMOPowers.TAKE_DAMAGE_FROM_ENDER_DRAGON_FIREBALL.get()));
    }

    @ModifyExpressionValue(method = "applyInstantenousEffect", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/LivingEntity;isInvertedHealAndHarm()Z", ordinal = 2))
    private boolean toomanyorigins$shouldApplyInstantEffect2(boolean original) {
        return original || !(toomanyorigins$indirectSource instanceof EnderDragon && Services.POWER.hasPower(toomanyorigins$target, TMOPowers.TAKE_DAMAGE_FROM_ENDER_DRAGON_FIREBALL.get()));
    }

}
