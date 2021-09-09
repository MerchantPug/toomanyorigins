package io.github.merchantpug.toomanyorigins.access;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.UseAction;

public interface ItemStackAccess {

    boolean isItemStackFood();

    FoodComponent getItemStackFoodComponent();

    void setItemStackFoodComponent(FoodComponent stackFoodComponent);

    UseAction getFoodUseAction();

    void setFoodUseAction(UseAction useAction);

    ItemStack getReturnStack();

    void setReturnStack(ItemStack stack);

    SoundEvent getStackEatSound();

    void setStackEatSound(SoundEvent sound);
}
