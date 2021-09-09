package io.github.merchantpug.toomanyorigins.entity.feature;

import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.power.EnergySwirlOverlayPower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EnergySwirlOverlayFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private ArrayList<Identifier> skin = new ArrayList<>();
    private ArrayList<Float> speed = new ArrayList<>();
    private int powerAmount = 0;

    public EnergySwirlOverlayFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        List<EnergySwirlOverlayPower> powers = OriginComponent.getPowers(entity, EnergySwirlOverlayPower.class);
        if (powers.size() != powerAmount) {
            skin.clear();
            speed.clear();
            powers.forEach(power -> {
                skin.add(power.getTextureLocation());
                speed.add(power.getSpeed());
            });
            powerAmount = powers.size();
        }
        for (int i = 0; i < powers.size(); i++) {
            float f = (float)entity.age + tickDelta;
            EntityModel<T> entityModel = this.getContextModel();
            entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
            this.getContextModel().copyStateTo(entityModel);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(i), this.getEnergySwirlX(f, i) % 1.0F, f * 0.01F % 1.0F));
            entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
        }
    }

    protected float getEnergySwirlX(float partialAge, int i) {
        return partialAge * getSpeed(i);
    }

    protected Identifier getEnergySwirlTexture(int i) {
        return skin.get(i);
    }

    protected float getSpeed(int i) {
        return speed.get(i);
    }
}

