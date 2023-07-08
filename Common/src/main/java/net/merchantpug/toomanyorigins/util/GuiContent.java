package net.merchantpug.toomanyorigins.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record GuiContent(Either<ResourceLocation, ItemStack> content, int x, int y, int u, int v, int textureWidth, int textureHeight) { }
