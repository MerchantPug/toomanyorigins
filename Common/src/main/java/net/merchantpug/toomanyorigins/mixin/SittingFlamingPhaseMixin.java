package net.merchantpug.toomanyorigins.mixin;

import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonSittingFlamingPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DragonSittingFlamingPhase.class)
public class SittingFlamingPhaseMixin {

    @ModifyArg(method = "doServerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/world/effect/MobEffect;)V"))
    private MobEffect statusEffect(MobEffect effect) {
        if (Services.CONFIG.shouldDragonFireballDamageUndead()) {
            return effect = TMOEffects.END_FIRE.get();
        }
        return effect;
    }

}
