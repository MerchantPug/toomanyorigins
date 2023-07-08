package net.merchantpug.toomanyorigins.util;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public record GuiBackground(ResourceLocation location, float uOffset, float vOffset, int uWidth, int vHeight) { }
