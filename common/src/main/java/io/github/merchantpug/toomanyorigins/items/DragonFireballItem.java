package io.github.merchantpug.toomanyorigins.items;

import io.github.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DragonFireballItem extends Item {
    public DragonFireballItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            SmallDragonFireballEntity smallDragonFireballEntity = new SmallDragonFireballEntity(world, user);
            smallDragonFireballEntity.setItem(itemStack);
            smallDragonFireballEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 0.5F, 1.0F);
            world.spawnEntity(smallDragonFireballEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
