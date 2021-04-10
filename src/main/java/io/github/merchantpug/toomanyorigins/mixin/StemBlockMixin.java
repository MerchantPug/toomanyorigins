package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin extends PlantBlock implements Fertilizable {
    protected StemBlockMixin(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer != null && TMOPowers.BLACK_THUMB.isActive(placer)) {
            if (this == Blocks.MELON_STEM) {
                world.setBlockState(pos, TMOBlocks.WITHERED_MELON_STEM.getDefaultState());
            } else if (this == Blocks.PUMPKIN_STEM) {
                world.setBlockState(pos, TMOBlocks.WITHERED_PUMPKIN_STEM.getDefaultState());
            }
        }
    }
}
