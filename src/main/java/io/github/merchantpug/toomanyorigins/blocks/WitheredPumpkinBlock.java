package io.github.merchantpug.toomanyorigins.blocks;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WitheredPumpkinBlock extends WitheredGourdBlock {

    public WitheredPumpkinBlock(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!world.isClient) {
                Direction direction = hit.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
                world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, (BlockState) TMOBlocks.CARVED_WITHERED_PUMPKIN.getDefaultState().with(CarvedWitheredPumpkinBlock.FACING, direction2), 11);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D + (double)direction2.getOffsetX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction2.getOffsetZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                itemEntity.setVelocity(0.05D * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02D);
                world.spawnEntity(itemEntity);
                itemStack.damage(1, (LivingEntity)player, ((playerEntity) -> {
                    playerEntity.sendToolBreakStatus(hand);
                }));
            }

            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Environment(EnvType.CLIENT)
    public StemBlock getStem() {
        return (WitheredStemBlock) TMOBlocks.WITHERED_PUMPKIN_STEM;
    }

    @Environment(EnvType.CLIENT)
    public AttachedStemBlock getAttachedStem() {
        return (AttachedWitheredStemBlock) TMOBlocks.ATTACHED_WITHERED_PUMPKIN_STEM;
    }
}
