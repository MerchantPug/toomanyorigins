package com.github.merchantpug.toomanyorigins.content.block.legacy;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.function.Supplier;

public class WitheredCropBlock extends PlantBlock {
    private final Supplier<Item> pickBlockItem;

    public WitheredCropBlock(Supplier<Item> pickBlockItem, Settings settings) {
        super(settings);
        this.pickBlockItem = pickBlockItem;
        this.setDefaultState(this.stateManager.getDefaultState());
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
        Vec3d vec3d = voxelShape.getBoundingBox().getCenter();
        double d = (double)pos.getX() + vec3d.x;
        double e = (double)pos.getZ() + vec3d.z;

        for(int i = 0; i < 2; ++i) {
            if (random.nextBoolean()) {
                world.addParticle(ParticleTypes.SMOKE, d + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), e + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.pickBlockItem.get());
    }
}
