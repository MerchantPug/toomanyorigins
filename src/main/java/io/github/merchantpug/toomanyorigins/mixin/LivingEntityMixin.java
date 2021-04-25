package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.SetEntityGroupPower;
import io.github.apace100.origins.registry.ModComponents;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.power.ExtraSoulSpeedPower;
import io.github.merchantpug.toomanyorigins.power.SetTMOEntityGroupPower;
import io.github.merchantpug.toomanyorigins.power.UnenchantedSoulSpeedPower;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "shouldDisplaySoulSpeedEffects", at = @At("HEAD"), cancellable = true)
    private void shouldDisplaySoulSpeedEffects(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.age % 5 == 0 && this.getVelocity().x != 0.0D && this.getVelocity().z != 0.0D && !this.isSpectator() && (EnchantmentHelper.hasSoulSpeed((LivingEntity) (Object) this) || OriginComponent.hasPower(this, UnenchantedSoulSpeedPower.class) || OriginComponent.hasPower(this, ExtraSoulSpeedPower.class)) && ((LivingEntityInvoker) this).invokeIsOnSoulSpeedBlock());
    }

    @ModifyVariable(method = "addSoulSpeedBoostIfNeeded", at = @At("STORE"), ordinal = 0)
    private int replaceLevelOfSouLSpeed(int i) {
        if (OriginComponent.hasPower(this, UnenchantedSoulSpeedPower.class) && i == 0) {
            return i = (OriginComponent.getPowers(this, UnenchantedSoulSpeedPower.class).get(0).getModifier());
        }
        if (OriginComponent.hasPower(this, ExtraSoulSpeedPower.class)) {
            return i += (OriginComponent.getPowers(this, ExtraSoulSpeedPower.class).get(0).getModifier());
        }
        return i;
    }

    @Inject(method = "addSoulSpeedBoostIfNeeded", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void itemStack(CallbackInfo ci, int i) {
        if (OriginComponent.hasPower(this, UnenchantedSoulSpeedPower.class) && i <= (OriginComponent.getPowers(this, UnenchantedSoulSpeedPower.class).get(0).getModifier())) {
            ci.cancel();
        }
        if (OriginComponent.hasPower(this, ExtraSoulSpeedPower.class) && i == (OriginComponent.getPowers(this, ExtraSoulSpeedPower.class).get(0).getModifier())) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "getGroup", cancellable = true)
    public void getGroup(CallbackInfoReturnable<EntityGroup> cir) {
        if((Object)this instanceof PlayerEntity) {
            OriginComponent component = ModComponents.ORIGIN.get(this);
            List<SetEntityGroupPower> originsGroups = component.getPowers(SetEntityGroupPower.class);
            List<SetTMOEntityGroupPower> tmoGroups = component.getPowers(SetTMOEntityGroupPower.class);
            if(tmoGroups.size() > 0) {
                if(tmoGroups.size() > 1 || tmoGroups.size() > 0 && originsGroups.size() > 0) {
                    TooManyOrigins.LOGGER.warn("Player " + this.getDisplayName().toString() + " has two instances of SetEntityGroupPower/SetTMOEntityGroupPower.");
                }
                cir.setReturnValue(tmoGroups.get(0).group);
            }
        }
    }
}
