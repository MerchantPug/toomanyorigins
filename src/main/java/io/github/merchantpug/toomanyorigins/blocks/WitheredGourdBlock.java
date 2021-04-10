package io.github.merchantpug.toomanyorigins.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.GourdBlock;
import net.minecraft.block.StemBlock;

public class WitheredGourdBlock extends GourdBlock {

    public WitheredGourdBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public StemBlock getStem() {
        return null;
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return null;
    }
}
