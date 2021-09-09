package io.github.merchantpug.toomanyorigins.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;


import static net.minecraft.state.property.Properties.LIT;

public class TMOBlockActions {
    public static void register() {
        register(new ActionFactory<>(TooManyOrigins.identifier("light_up"), new SerializableData()
                .add("burn_time", SerializableDataType.INT)
                .add("brew_time", SerializableDataType.INT)
                .add("light_campfire", SerializableDataType.BOOLEAN, true)
                .add("particle", SerializableDataType.PARTICLE_TYPE, null)
                .add("particle_count", SerializableDataType.INT, 0)
                .add("sound", SerializableDataType.SOUND_EVENT, null),
                (data, block) -> {
                    BlockState state = block.getLeft().getBlockState(block.getMiddle());
                    BlockEntity entity = block.getLeft().getBlockEntity(block.getMiddle());
                    if (state.getBlock() instanceof AbstractFurnaceBlock) {
                        block.getLeft().setBlockState(block.getMiddle(), state.with(LIT, true).with(LIT, true), 2);
                        if (data.isPresent("particle") && data.getInt("particle_count") > 0 && !block.getLeft().isClient()) {
                            ((ServerWorld)block.getLeft()).spawnParticles((DefaultParticleType)data.get("particle"), block.getMiddle().getX() + 0.5, block.getMiddle().getY() + 0.3, block.getMiddle().getZ() + 0.5, data.getInt("particle_count"), block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.1D, block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.05D);
                        }
                        if (data.isPresent("sound")) {
                            block.getLeft().playSound(null, block.getMiddle().getX(), block.getMiddle().getY(), block.getMiddle().getZ(), (SoundEvent)data.get("sound"), SoundCategory.NEUTRAL, 0.5F, (block.getLeft().getRandom().nextFloat() - block.getLeft().getRandom().nextFloat()) * 0.2F + 1.0F);
                        }
                    }
                    if (entity instanceof AbstractFurnaceBlockEntity) {
                        if (((AbstractFurnaceBlockEntity)entity).burnTime < data.getInt("burn_time")) {
                            ((AbstractFurnaceBlockEntity)entity).fuelTime = data.getInt("burn_time");
                            ((AbstractFurnaceBlockEntity)entity).burnTime = data.getInt("burn_time");
                        }
                    }
                    if (state.getBlock() instanceof CampfireBlock && data.getBoolean("light_campfire")) {
                        block.getLeft().setBlockState(block.getMiddle(), state.with(LIT, true).with(LIT, true), 2);
                        if (data.isPresent("particle") && data.getInt("particle_count") > 0 && !block.getLeft().isClient()) {
                            ((ServerWorld)block.getLeft()).spawnParticles((DefaultParticleType)data.get("particle"), block.getMiddle().getX() + 0.5, block.getMiddle().getY() + 0.3, block.getMiddle().getZ() + 0.5, data.getInt("particle_count"), block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.1D, block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.05D);
                        }
                        if (data.isPresent("sound")) {
                            block.getLeft().playSound(null, block.getMiddle().getX(), block.getMiddle().getY(), block.getMiddle().getZ(), (SoundEvent)data.get("sound"), SoundCategory.NEUTRAL, 0.5F, (block.getLeft().getRandom().nextFloat() - block.getLeft().getRandom().nextFloat()) * 0.2F + 1.0F);
                        }
                    }
                    if (entity instanceof BrewingStandBlockEntity) {
                        if (((BrewingStandBlockEntity)entity).fuel < data.getInt("brew_time")) {
                            ((BrewingStandBlockEntity)entity).fuel = data.getInt("brew_time");
                        }
                        if (data.isPresent("particle") && data.getInt("particle_count") > 0 && !block.getLeft().isClient()) {
                            ((ServerWorld)block.getLeft()).spawnParticles((DefaultParticleType)data.get("particle"), block.getMiddle().getX() + 0.5, block.getMiddle().getY() + 0.3, block.getMiddle().getZ() + 0.5, data.getInt("particle_count"), block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.1D, block.getLeft().getRandom().nextDouble() * 0.2D - 0.1D, 0.05D);
                        }
                        if (data.isPresent("sound")) {
                            block.getLeft().playSound(null, block.getMiddle().getX(), block.getMiddle().getY(), block.getMiddle().getZ(), (SoundEvent)data.get("sound"), SoundCategory.NEUTRAL, 0.5F, (block.getLeft().getRandom().nextFloat() - block.getLeft().getRandom().nextFloat()) * 0.2F + 1.0F);
                        }
                    }
                }));
        register(new ActionFactory<>(TooManyOrigins.identifier("destroy"), new SerializableData(),
                (data, block) -> {
                    CachedBlockPosition cachedBlockPosition = new CachedBlockPosition(block.getLeft(), block.getMiddle(), true);
                    block.getLeft().syncWorldEvent(2001, block.getMiddle(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
                }));
    }

    @ExpectPlatform
    private static void register(ActionFactory<Triple<World, BlockPos, Direction>> actionFactory) {
        throw new AssertionError();
    }
}
