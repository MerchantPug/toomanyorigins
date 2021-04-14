package io.github.merchantpug.toomanyorigins.entity.renderer;

import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PlayerWitherArmorEnergySwirlOverlayFeatureRenderer<T extends LivingEntity & SkinOverlayOwner> extends EnergySwirlOverlayFeatureRenderer<T, BipedEntityModel<T>> {

    private static final Identifier SKIN = new Identifier("textures/entity/wither/wither_armor.png");
    private final BipedEntityModel<T> model = new BipedEntityModel(2.0F);

    public PlayerWitherArmorEnergySwirlOverlayFeatureRenderer(FeatureRendererContext<T, BipedEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.hasStatusEffect(TMOEffects.WITHER_RESISTANCE)) {
            float f = (float)entity.age + tickDelta;
            BipedEntityModel<T> entityModel = this.getContextModel();
            entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.getContextModel().copyStateTo(entityModel);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(f), f * 0.01F));
            entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

    protected float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }

    protected net.minecraft.util.Identifier getEnergySwirlTexture() {
        return SKIN;
    }

    protected EntityModel<T> getEnergySwirlModel() {
        return this.model;
    }
}
