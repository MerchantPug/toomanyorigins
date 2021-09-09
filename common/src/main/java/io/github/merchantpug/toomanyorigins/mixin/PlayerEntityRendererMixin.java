package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.entity.feature.StackHeadFeatureRenderer;
import io.github.merchantpug.toomanyorigins.entity.feature.StackHelmetFeatureRenderer;
import io.github.merchantpug.toomanyorigins.power.SetTexturePower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRenderDispatcher entityRenderDispatcher, PlayerEntityModel<AbstractClientPlayerEntity> entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At("RETURN"))
    private void construct(EntityRenderDispatcher entityRenderDispatcher, boolean bl, CallbackInfo ci) {
        this.addFeature(new StackHeadFeatureRenderer(this));
        this.addFeature(new StackHelmetFeatureRenderer<>(this, new BipedEntityModel(1.0F)));
    }

    @ModifyArgs(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntitySolid(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private void modifyEntityLayerSolid(Args args, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        if (OriginComponent.hasPower(player, SetTexturePower.class)) {
            if (OriginComponent.getPowers(player, SetTexturePower.class).get(0).textureLocation != null) {
                args.set(0, OriginComponent.getPowers(player, SetTexturePower.class).get(0).textureLocation);
            }
        }
    }

    @ModifyArgs(method = "renderArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityTranslucent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private void modifyEntityLayerTranslucent(Args args, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        if (OriginComponent.hasPower(player, SetTexturePower.class)) {
            if (OriginComponent.getPowers(player, SetTexturePower.class).get(0).textureLocation != null) {
                args.set(0, OriginComponent.getPowers(player, SetTexturePower.class).get(0).textureLocation);
            }
        }
    }
}
