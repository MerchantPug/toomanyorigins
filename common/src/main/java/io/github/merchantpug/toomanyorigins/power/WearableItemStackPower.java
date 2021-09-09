package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class WearableItemStackPower extends Power {
    private final ItemStack itemStack;
    private final float scale;

    public WearableItemStackPower(PowerType<?> type, PlayerEntity player, ItemStack itemStack, float scale) {
        super(type, player);
        this.itemStack = itemStack;
        this.scale = scale;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public float getScale() {
        return scale;
    }
}
