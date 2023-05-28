package net.merchantpug.toomanyorigins.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.function.Supplier;

public class WitheredStemBlock extends BushBlock {
    private final Supplier<Item> pickBlockItem;

    public WitheredStemBlock(Supplier<Item> pickBlockItem, Properties settings) {
        super(settings);
        this.pickBlockItem = pickBlockItem;
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D);
    }

    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getShape(state, world, pos, CollisionContext.empty());
        Vec3 vec3d = voxelShape.bounds().getCenter();
        double d = (double)pos.getX() + vec3d.x;
        double e = (double)pos.getZ() + vec3d.z;

        for(int i = 0; i < 1; ++i) {
            if (random.nextBoolean()) {
                world.addParticle(ParticleTypes.SMOKE, d + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), e + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState floor, LevelReader world, BlockPos pos) {
        return floor.is(Blocks.FARMLAND);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(this.pickBlockItem.get());
    }
}
