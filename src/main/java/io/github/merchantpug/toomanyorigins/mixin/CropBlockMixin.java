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

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable {
    protected CropBlockMixin(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer != null && TMOPowers.BLACK_THUMB.isActive(placer)) {
            if (this == Blocks.WHEAT) {
                world.setBlockState(pos, TMOBlocks.WITHERED_WHEAT.getDefaultState());
            } else if (this == Blocks.POTATOES) {
                world.setBlockState(pos, TMOBlocks.WITHERED_POTATOES.getDefaultState());
            } else if (this == Blocks.CARROTS) {
                world.setBlockState(pos, TMOBlocks.WITHERED_CARROTS.getDefaultState());
            } else if (this == Blocks.BEETROOTS) {
                world.setBlockState(pos, TMOBlocks.WITHERED_BEETROOTS.getDefaultState());
            }
        }
    }
}
