package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {

    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onPlaced", at = @At("HEAD"), cancellable = true)
    private void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (placer != null && TMOPowers.BLACK_THUMB.isActive(placer)) {
            if ((Block)(Object)this == Blocks.WHEAT) {
                world.setBlockState(pos, TMOBlocks.WITHERED_WHEAT.getDefaultState());
            }
            if ((Block)(Object)this == Blocks.POTATOES) {
                world.setBlockState(pos, TMOBlocks.WITHERED_POTATOES.getDefaultState());
            }
            if ((Block)(Object)this == Blocks.CARROTS) {
                world.setBlockState(pos, TMOBlocks.WITHERED_CARROTS.getDefaultState());
            }
            if ((Block)(Object)this == Blocks.BEETROOTS) {
                world.setBlockState(pos, TMOBlocks.WITHERED_BEETROOTS.getDefaultState());
            }
            if ((Block)(Object)this == Blocks.MELON_STEM) {
                world.setBlockState(pos, TMOBlocks.WITHERED_MELON_STEM.getDefaultState());
            }
            if ((Block)(Object)this == Blocks.PUMPKIN_STEM) {
                world.setBlockState(pos, TMOBlocks.WITHERED_PUMPKIN_STEM.getDefaultState());
            }
        }
    }
}