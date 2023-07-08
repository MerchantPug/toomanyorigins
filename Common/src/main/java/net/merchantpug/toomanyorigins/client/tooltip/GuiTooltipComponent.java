package net.merchantpug.toomanyorigins.client.tooltip;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.toomanyorigins.util.GuiBackground;
import net.merchantpug.toomanyorigins.util.GuiContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiTooltipComponent implements ClientTooltipComponent {
    private final GuiBackground background;
    private final NonNullList<GuiContent> contents;

    public GuiTooltipComponent(GuiBackground background, NonNullList<GuiContent> contents) {
        this.background = background;
        this.contents = contents;
    }

    @Override
    public int getHeight() {
        int textureHeight = this.background.textureHeight() < 0 ? this.getTextureHeight(this.background.location()) : this.background.textureHeight();
        return textureHeight + 20;
    }

    @Override
    public int getWidth(Font font) {
        int textureWidth = this.background.textureWidth() < 0 ? this.getTextureWidth(this.background.location()) : this.background.textureWidth();
        return textureWidth + 20;
    }

    @Override
    public void renderImage(Font font, int x, int y, PoseStack poseStack, ItemRenderer itemRenderer, int blitOffset) {
        int backgroundWidth = this.background.textureWidth() < 0 ? this.getTextureWidth(this.background.location()) : this.background.textureWidth();
        int backgroundHeight = this.background.textureHeight() < 0 ? this.getTextureHeight(this.background.location()) : this.background.textureHeight();
        drawTexture(poseStack, background.location(), 10, 10, blitOffset, background.u(), background.v(), backgroundHeight, backgroundWidth);
        for (GuiContent content : contents) {
            content.content()
                    .ifLeft(resourceLocation -> {
                        int textureWidth = content.textureWidth() < 0 ? this.getTextureWidth(resourceLocation) : content.textureWidth();
                        int textureHeight = content.textureHeight() < 0 ? this.getTextureHeight(resourceLocation) : content.textureHeight();
                        drawTexture(poseStack, resourceLocation,
                                content.x(), content.y(), blitOffset,
                                content.u(), content.v(),
                                textureHeight, textureWidth);
                    }).ifRight(stack -> drawItem(itemRenderer, stack, content.x(), content.y()));
        }
    }

    private int getTextureWidth(ResourceLocation textureLocation) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.bindForSetup(textureLocation);
        return GlStateManager._getTexLevelParameter(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
    }

    private int getTextureHeight(ResourceLocation textureLocation) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.bindForSetup(textureLocation);
        return GlStateManager._getTexLevelParameter(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
    }

    public void drawTexture(PoseStack matrices, ResourceLocation location, int x, int y, int blitOffset, int u, int v, int textureHeight, int textureWidth) {
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        GuiComponent.blit(matrices, x, y, blitOffset, 0.0F, 0.0F, u, v, textureHeight, textureWidth);
    }

    public void drawItem(ItemRenderer itemRenderer, ItemStack stack, int x, int y) {
        itemRenderer.renderAndDecorateItem(stack, x, y);
    }

}
