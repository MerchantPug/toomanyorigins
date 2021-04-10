package io.github.merchantpug.toomanyorigins.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.Random;

public class AttachedWitheredStemBlock extends AttachedStemBlock {

    private final WitheredGourdBlock witheredGourdBlock;

    public AttachedWitheredStemBlock(WitheredGourdBlock witheredGourdBlock, AbstractBlock.Settings settings) {
        super(witheredGourdBlock, settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
        this.witheredGourdBlock = witheredGourdBlock;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        VoxelShape voxelShape = this.getOutlineShape(state, world, pos, ShapeContext.absent());
        Vec3d vec3d = voxelShape.getBoundingBox().getCenter();
        double d = (double)pos.getX() + vec3d.x;
        double e = (double)pos.getZ() + vec3d.z;

        for(int i = 0; i < 3; ++i) {
            if (random.nextBoolean()) {
                world.addParticle(ParticleTypes.SMOKE, d + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), e + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
