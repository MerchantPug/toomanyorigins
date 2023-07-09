package net.merchantpug.toomanyorigins.client.tooltip;

import com.mojang.blaze3d.platform.GlStateManager;
import net.merchantpug.toomanyorigins.util.GuiBackground;
import net.merchantpug.toomanyorigins.util.GuiContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiTooltipComponent implements ClientTooltipComponent {
    private final GuiBackground background;
    private final NonNullList<GuiContent> contents;
    private final int seed;

    public GuiTooltipComponent(GuiBackground background, NonNullList<GuiContent> contents, int seed) {
        this.background = background;
        this.contents = contents;
        this.seed = seed;
    }

    @Override
    public int getHeight() {
        return getTextureWidth(this.background.location(), this.background.vHeight()) + 8;
    }

    @Override
    public int getWidth(Font font) {
        return getTextureWidth(this.background.location(), this.background.uWidth()) + 8;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        drawTexture(guiGraphics, background.location(), x + 4, y + 4, background.uOffset(), background.vOffset(), getTextureHeight(background.location(), background.vHeight()), getTextureWidth(background.location(), background.uWidth()));
        for (GuiContent content : contents) {
            content.content()
                    .ifLeft(resourceLocation -> {
                        drawTexture(guiGraphics, resourceLocation,
                                x + 4 + content.x(), y + 4 + content.y(),
                                content.uOffset(), content.vOffset(),
                                getTextureHeight(resourceLocation, content.vHeight()), getTextureWidth(resourceLocation, content.uWidth()));
                    }).ifRight(either -> {
                        either.ifLeft(stack -> drawItem(guiGraphics, stack, x + 4 + content.x(), y + 4 + content.y()))
                                .ifRight(tag -> drawItem(guiGraphics, getStackFromTag(tag), x + 4 + content.x(), y + 4 + content.y()));
                    });
        }
    }

    private ItemStack getStackFromTag(TagKey<Item> tagKey) {
        if (BuiltInRegistries.ITEM.getTag(tagKey).isPresent()) {
            var tag = BuiltInRegistries.ITEM.getTag(tagKey).get();
            Holder<Item> holder = tag.get(seed % tag.size());
            if (holder.isBound()) {
                return new ItemStack(holder.value());
            }
        }
        return ItemStack.EMPTY;
    }

    private int getTextureHeight(ResourceLocation location, int vHeight) {
        return vHeight < 0 ? this.getTotalTextureHeight(location) : vHeight;
    }

    private int getTextureWidth(ResourceLocation location, int uWidth) {
        return uWidth < 0 ? this.getTotalTextureWidth(location) : uWidth;
    }

    private int getTotalTextureWidth(ResourceLocation location) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.bindForSetup(location);
        return GlStateManager._getTexLevelParameter(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
    }

    private int getTotalTextureHeight(ResourceLocation location) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.bindForSetup(location);
        return GlStateManager._getTexLevelParameter(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
    }

    public void drawTexture(GuiGraphics guiGraphics, ResourceLocation location, int x, int y, float uOffset, float vOffset, int textureHeight, int textureWidth) {
        guiGraphics.blit(location, x, y, uOffset, vOffset, textureWidth, textureHeight, getTotalTextureHeight(location), getTotalTextureWidth(location));
    }

    public void drawItem(GuiGraphics guiGraphics, ItemStack stack, int x, int y) {
        guiGraphics.renderItem(stack, x, y);
    }

}
