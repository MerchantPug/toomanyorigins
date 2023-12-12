package net.merchantpug.toomanyorigins.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record GuiContent(Either<ResourceLocation, Either<ItemStack, TagKey<Item>>> content, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight) { }
