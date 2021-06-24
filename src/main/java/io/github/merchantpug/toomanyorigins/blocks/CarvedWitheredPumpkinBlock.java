package io.github.merchantpug.toomanyorigins.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.util.math.Direction;

public class CarvedWitheredPumpkinBlock extends CarvedPumpkinBlock {

    public CarvedWitheredPumpkinBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
}
