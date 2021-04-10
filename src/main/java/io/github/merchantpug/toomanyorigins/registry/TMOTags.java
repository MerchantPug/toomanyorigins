package io.github.merchantpug.toomanyorigins.registry;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TMOTags {
    public static final Tag<Block> LIGHTABLE = TagRegistry.block(new Identifier(TooManyOrigins.MODID, "lightable"));
}
