/*
MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.merchantpug.toomanyorigins.mixin;

import com.google.common.collect.Multimap;
import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.access.ItemStackAccess;
import io.github.merchantpug.toomanyorigins.power.EdibleItemStackPower;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackAccess {

    @Shadow public abstract Item getItem();

    @Unique
    protected FoodComponent nibbles_stackFoodComponent;
    @Unique
    protected UseAction nibbles_useAction;
    @Unique
    protected ItemStack nibbles_returnStack;
    @Unique
    protected SoundEvent nibbles_eatSound;

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (this.isItemStackFood()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.canConsume(this.getItemStackFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                if (this.getItem() instanceof BucketItem) {
                    BlockHitResult blockHitResult = Item.raycast(world, user, ((BucketItem)this.getItem()).fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
                    if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                        cir.setReturnValue(((ItemStack)(Object)this).getItem().use(world, user, hand));
                    }
                }
                cir.setReturnValue(TypedActionResult.consume(itemStack));
            }
        }
    }

    @Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
    private void finishUsing(World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (this.isItemStackFood()) {
            ItemStack itemStack = user.eatFood(world, (ItemStack)(Object)this);
            if (this.getReturnStack() != null) {
                cir.setReturnValue(user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? itemStack : this.getReturnStack());
            } else {
                cir.setReturnValue(itemStack);
            }
        }
    }

    @Inject(method = "getUseAction", at = @At("HEAD"), cancellable = true)
    private void getUseAction(CallbackInfoReturnable<UseAction> cir) {
        if (this.isItemStackFood()) {
            UseAction useAction = this.getFoodUseAction() != null ? this.getFoodUseAction() : UseAction.EAT;
            cir.setReturnValue(useAction);
        }
    }

    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    private void getMaxUseTime(CallbackInfoReturnable<Integer> cir) {
        if (isItemStackFood()) {
            cir.setReturnValue(this.getItemStackFoodComponent().isSnack() ? 16 : 32);
        }
    }

    @Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
    private void isFood(CallbackInfoReturnable<Boolean> cir) {
        if (this.isItemStackFood()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getDrinkSound", at = @At("HEAD"), cancellable = true)
    private void getDrinkSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.getStackEatSound() != null) {
            cir.setReturnValue(this.getStackEatSound());
        }
    }

    @Inject(method = "getEatSound", at = @At("HEAD"), cancellable = true)
    private void getEatSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.getStackEatSound() != null) {
            cir.setReturnValue(this.getStackEatSound());
        }
    }

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void executeEntityActions(World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if(user != null) {
            OriginComponent.getPowers(user, EdibleItemStackPower.class).stream().filter(p -> p.doesApply((ItemStack)(Object)this)).forEach(EdibleItemStackPower::eat);
        }
    }

    @Override
    public FoodComponent getItemStackFoodComponent() {
        return this.nibbles_stackFoodComponent;
    }

    @Override
    public void setItemStackFoodComponent(FoodComponent stackFoodComponent) {
        this.nibbles_stackFoodComponent = stackFoodComponent;
    }

    @Override
    public boolean isItemStackFood() {
        return this.nibbles_stackFoodComponent != null;
    }

    @Override
    public UseAction getFoodUseAction() {
        return this.nibbles_useAction;
    }

    @Override
    public void setFoodUseAction(UseAction useAction) {
        if (useAction == UseAction.EAT || useAction == UseAction.DRINK) {
            this.nibbles_useAction = useAction;
        }
    }

    @Override
    public ItemStack getReturnStack() {
        return this.nibbles_returnStack;
    }

    @Override
    public void setReturnStack(ItemStack stack) {
        this.nibbles_returnStack = stack;
    }

    @Override
    public SoundEvent getStackEatSound() {
        return this.nibbles_eatSound;
    }

    @Override
    public void setStackEatSound(SoundEvent sound) {
        this.nibbles_eatSound = sound;
    }
}
