package net.merchantpug.toomanyorigins.content.legacy.item;

import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class WitheredCropAliasedBlockItem extends ItemNameBlockItem {
    public WitheredCropAliasedBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    protected boolean place(BlockPlaceContext context, BlockState state) {
        if (!LegacyContentRegistry.areWitheredCropsEnabled()) {
            if (context.getPlayer() != null) {
                Player player = context.getPlayer();
                if (!player.level.isClientSide) {
                    player.sendSystemMessage(Component.translatable("toomanyorigins.content.disabled_message", LegacyContentRegistry.WITHERED_CROPS).withStyle(ChatFormatting.RED));
                }
                player.getCooldowns().addCooldown(this, 20);
            }
            return false;
        }
        return super.placeBlock(context, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        if (!LegacyContentRegistry.areWitheredCropsEnabled())
            tooltip.add(Component.translatable("toomanyorigins.content.disabled").withStyle(ChatFormatting.GRAY));
    }

}