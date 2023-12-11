package net.merchantpug.toomanyorigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.effect.HealOrHarmMobEffect")
public class HealOrHarmMobEffectMixin {
    @Shadow @Final private boolean isHarm;

    @ModifyExpressionValue(method = "applyInstantenousEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInvertedHealAndHarm()Z", ordinal = 0))
    private boolean toomanyorigins$shouldApplyInstantEffect0(boolean original, Entity source, Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        return original && (!this.isHarm || !Services.POWER.hasPower(livingEntity, TMOPowers.TAKE_DAMAGE_FROM_ENDER_DRAGON_FIREBALL.get()) || indirectSource instanceof EnderDragon);
    }

}
