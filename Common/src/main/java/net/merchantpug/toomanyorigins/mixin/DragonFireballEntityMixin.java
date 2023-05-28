package net.merchantpug.toomanyorigins.mixin;

import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.projectile.DragonFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DragonFireball.class)
public class DragonFireballEntityMixin {

    @ModifyArg(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/world/effect/MobEffect;II)V"))
    private MobEffect statusEffect(MobEffect effect) {
        if (Services.CONFIG.shouldDragonFireballDamageUndead()) {
            return effect = TMOEffects.END_FIRE.get();
        }
        return effect;
    }

}
