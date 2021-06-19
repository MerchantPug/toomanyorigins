package io.github.merchantpug.toomanyorigins.blocks;

import io.github.merchantpug.toomanyorigins.registry.TMOBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

public class WitheredStemBlock extends StemBlock {

    private final WitheredGourdBlock witheredGourdBlock;
    private final Supplier<Item> pickBlockItem;

    public WitheredStemBlock(WitheredGourdBlock witheredGourdBlock, Supplier<Item> pickBlockItem, Settings settings) {
        super(witheredGourdBlock, pickBlockItem, settings);
        this.witheredGourdBlock = witheredGourdBlock;
        this.pickBlockItem = pickBlockItem;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    }

    public WitheredGourdBlock getGourdBlock() {
        return this.witheredGourdBlock;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
        Vec3d vec3d = voxelShape.getBoundingBox().getCenter();
        double d = (double)pos.getX() + vec3d.x;
        double e = (double)pos.getZ() + vec3d.z;

        for(int i = 0; i < 1; ++i) {
            if (random.nextBoolean()) {
                world.addParticle(ParticleTypes.SMOKE, d + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), e + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack((ItemConvertible)this.pickBlockItem.get());
    }
}
