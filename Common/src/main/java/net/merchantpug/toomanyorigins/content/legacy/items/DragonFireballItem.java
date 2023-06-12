package net.merchantpug.toomanyorigins.content.legacy.items;

import net.merchantpug.toomanyorigins.content.legacy.entity.SmallDragonFireballEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DragonFireballItem extends Item {
    public DragonFireballItem(Properties settings) {
        super(settings);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            SmallDragonFireballEntity smallDragonFireballEntity = new SmallDragonFireballEntity(world, user);
            smallDragonFireballEntity.setItem(itemStack);
            smallDragonFireballEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 0.5F, 1.0F);
            world.addFreshEntity(smallDragonFireballEntity);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }
}
