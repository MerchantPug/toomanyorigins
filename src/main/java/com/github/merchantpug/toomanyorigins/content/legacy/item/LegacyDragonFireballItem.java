package com.github.merchantpug.toomanyorigins.content.legacy.item;

import com.github.merchantpug.toomanyorigins.data.LegacyContentModules;
import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import com.github.merchantpug.toomanyorigins.content.legacy.entity.LegacySmallDragonFireballEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LegacyDragonFireballItem extends Item {
    public LegacyDragonFireballItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (LegacyContentRegistry.isContentDisabled(LegacyContentModules.DRAGON_FIREBALL)) {
            if (!user.world.isClient) {
                user.sendMessage(Text.translatable("toomanyorigins.content.disabled_message", LegacyContentModules.DRAGON_FIREBALL).formatted(Formatting.RED));
            }
            user.getItemCooldownManager().set(this, 20);
            return TypedActionResult.pass(itemStack);
        }
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            LegacySmallDragonFireballEntity smallDragonFireballEntity = new LegacySmallDragonFireballEntity(world, user);
            smallDragonFireballEntity.setItem(itemStack);
            smallDragonFireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.5F, 1.0F);
            world.spawnEntity(smallDragonFireballEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentModules.DRAGON_FIREBALL))
            tooltip.add(Text.translatable("toomanyorigins.content.disabled").formatted(Formatting.GRAY));
    }
}
