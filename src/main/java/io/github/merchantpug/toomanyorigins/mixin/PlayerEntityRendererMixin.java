package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.entity.renderer.PlayerChargeEnergySwirlOverlayFeatureRenderer;
import io.github.merchantpug.toomanyorigins.entity.renderer.PlayerWitherArmorEnergySwirlOverlayFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher, PlayerEntityModel<AbstractClientPlayerEntity> model, float scale) {
        super(dispatcher, model, scale);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("RETURN"))
    private void construct(EntityRenderDispatcher dispatcher, boolean slim, CallbackInfo ci) {
        this.addFeature(new PlayerWitherArmorEnergySwirlOverlayFeatureRenderer(this));
        this.addFeature(new PlayerChargeEnergySwirlOverlayFeatureRenderer(this));
    }
}
