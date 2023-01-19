package com.github.merchantpug.toomanyorigins.content.legacy.item;

import com.github.merchantpug.toomanyorigins.data.LegacyContentModules;
import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitheredCropAliasedBlockItem extends AliasedBlockItem {
    public WitheredCropAliasedBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    protected boolean place(ItemPlacementContext context, BlockState state) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentModules.WITHERED_CROPS)) {
            if (context.getPlayer() != null) {
                PlayerEntity player = context.getPlayer();
                if (!player.world.isClient) {
                    player.sendMessage(Text.translatable("toomanyorigins.content.disabled_message", LegacyContentModules.WITHERED_CROPS).formatted(Formatting.RED));
                }
                player.getItemCooldownManager().set(this, 20);
            }
            return false;
        }
        return super.place(context, state);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentModules.WITHERED_CROPS))
            tooltip.add(Text.translatable("toomanyorigins.content.disabled").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GRAY))));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentModules.WITHERED_CROPS) || !this.isIn(group)) return;
        stacks.add(new ItemStack(this));
    }
}
