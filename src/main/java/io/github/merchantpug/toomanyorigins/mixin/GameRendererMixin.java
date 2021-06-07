package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.config.ClientConfig;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;


    @Shadow protected abstract void method_31136(float f);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private void drawWitherShieldOverlay(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        ClientConfig config = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
        if(this.client.player.hasStatusEffect(TMOEffects.SOUL_SHIELD) && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA) && config.showSoulShieldOverlay) {
            this.method_31136(config.soulShieldOverlayStrength);
        }
    }

    @ModifyArgs(method = "method_31136", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", ordinal = 0))
    private void modifyColor(Args args) {
        if(this.client.player.hasStatusEffect(TMOEffects.SOUL_SHIELD) && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
            args.set(0, (float)0.69411764705D);
            args.set(1, (float)0.76078431372D);
            args.set(2, (float)0.47058823529D);
        }
    }
}
