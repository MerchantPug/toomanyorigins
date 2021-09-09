package io.github.merchantpug.toomanyorigins.entity.feature;

import com.mojang.authlib.GameProfile;
import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.power.WearableItemStackPower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class StackHeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private ItemStack stack;

    public StackHeadFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (OriginComponent.hasPower(entity, WearableItemStackPower.class)) {
            setScaleX(entity);
            setScaleY(entity);
            setScaleZ(entity);
        }
        setStack(entity);
        renderIndividualStack(matrices, vertexConsumers, light, entity, tickDelta, scaleX, scaleY, scaleZ, stack);
    }

    public void renderIndividualStack(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float scaleX, float scaleY, float scaleZ, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            Item item = itemStack.getItem();
            matrixStack.push();
            matrixStack.scale(scaleX, scaleY, scaleZ);
            boolean bl = livingEntity instanceof VillagerEntity || livingEntity instanceof ZombieVillagerEntity;
            float p;
            if (livingEntity.isBaby() && !(livingEntity instanceof VillagerEntity)) {
                p = 2.0F;
                float n = 1.4F;
                matrixStack.translate(0.0D, 0.03125D, 0.0D);
                matrixStack.scale(0.7F, 0.7F, 0.7F);
                matrixStack.translate(0.0D, 1.0D, 0.0D);
            }

            ((ModelWithHead)this.getContextModel()).getHead().rotate(matrixStack);
            if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
                p = 1.1875F;
                matrixStack.scale(1.1875F, -1.1875F, -1.1875F);
                if (bl) {
                    matrixStack.translate(0.0D, 0.0625D, 0.0D);
                }

                GameProfile gameProfile = null;
                if (itemStack.hasTag()) {
                    CompoundTag compoundTag = itemStack.getTag();
                    if (compoundTag.contains("SkullOwner", 10)) {
                        gameProfile = NbtHelper.toGameProfile(compoundTag.getCompound("SkullOwner"));
                    } else if (compoundTag.contains("SkullOwner", 8)) {
                        String string = compoundTag.getString("SkullOwner");
                        if (!StringUtils.isBlank(string)) {
                            gameProfile = SkullBlockEntity.loadProperties(new GameProfile((UUID)null, string));
                            compoundTag.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), gameProfile));
                        }
                    }
                }

                matrixStack.translate(-0.5D, 0.0D, -0.5D);
                SkullBlockEntityRenderer.render((Direction)null, 180.0F, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType(), gameProfile, f, matrixStack, vertexConsumerProvider, i);
            } else if (!(item instanceof ArmorItem) || ((ArmorItem)item).getSlotType() != EquipmentSlot.HEAD) {
                p = 0.625F;
                matrixStack.translate(0.0D, -0.25D, 0.0D);
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
                matrixStack.scale(0.625F, -0.625F, -0.625F);
                if (bl) {
                    matrixStack.translate(0.0D, 0.1875D, 0.0D);
                }

                MinecraftClient.getInstance().getHeldItemRenderer().renderItem(livingEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, i);
            }

            matrixStack.pop();
        }
    }

    protected void setScaleX(LivingEntity entity) {
        WearableItemStackPower power = OriginComponent.getPowers(entity, WearableItemStackPower.class).get(0);
        scaleX = entity instanceof PiglinEntity ? power.getScale() + 1.0019531F : power.getScale();
    }

    protected void setScaleY(LivingEntity entity) {
        WearableItemStackPower power = OriginComponent.getPowers(entity, WearableItemStackPower.class).get(0);
        scaleY = power.getScale();
    }

    protected void setScaleZ(LivingEntity entity) {
        WearableItemStackPower power = OriginComponent.getPowers(entity, WearableItemStackPower.class).get(0);
        scaleZ = entity instanceof PiglinEntity ? power.getScale() + 1.0019531F : power.getScale();
    }

    protected void setStack(LivingEntity entity) {
        if (OriginComponent.hasPower(entity, WearableItemStackPower.class)) {
            WearableItemStackPower power = OriginComponent.getPowers(entity, WearableItemStackPower.class).get(0);
            stack = power.getItemStack() != null ? power.getItemStack() : ItemStack.EMPTY;
        } else {
            stack = ItemStack.EMPTY;
        }
    }
}
