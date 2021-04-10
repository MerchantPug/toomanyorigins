package io.github.merchantpug.toomanyorigins.blocks;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.StemBlock;

public class WitheredMelonBlock extends WitheredGourdBlock {

    public WitheredMelonBlock(Settings settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    public StemBlock getStem() {
        return (WitheredStemBlock) TMOBlocks.WITHERED_MELON_STEM;
    }

    @Environment(EnvType.CLIENT)
    public AttachedStemBlock getAttachedStem() {
        return (AttachedWitheredStemBlock) TMOBlocks.ATTACHED_WITHERED_MELON_STEM;
    }
}
