package io.github.merchantpug.toomanyorigins.mixin;

import com.mojang.datafixers.util.Pair;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.power.SetEntityGroupPower;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.access.ItemStackAccess;
import io.github.merchantpug.toomanyorigins.access.LivingEntityAccess;
import io.github.merchantpug.toomanyorigins.power.BunnyHopPower;
import io.github.merchantpug.toomanyorigins.power.EdibleItemStackPower;
import io.github.merchantpug.toomanyorigins.power.ModifySoulSpeedPower;
import io.github.merchantpug.toomanyorigins.power.SetTMOEntityGroupPower;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOEntityGroups;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import io.github.merchantpug.toomanyorigins.util.ItemStackFoodComponentAPI;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {
    @Shadow
    public abstract boolean isFallFlying();

    @Shadow public abstract boolean isOnSoulSpeedBlock();

    @Shadow public abstract boolean hasStatusEffect(StatusEffect statusEffect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect statusEffect);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "shouldDisplaySoulSpeedEffects", at = @At("HEAD"), cancellable = true)
    private void shouldDisplaySoulSpeedEffects(CallbackInfoReturnable<Boolean> cir) {
        if (OriginComponent.hasPower(this, ModifySoulSpeedPower.class)) {
            int soulSpeedValue = (int)OriginComponent.modify(this, ModifySoulSpeedPower.class, EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, (LivingEntity)(Object)this));
            cir.setReturnValue(this.age % 5 == 0 && this.getVelocity().x != 0.0D && this.getVelocity().z != 0.0D && !this.isSpectator() && soulSpeedValue > 0 && this.isOnSoulSpeedBlock());
        }
    }

    @Inject(method = "isOnSoulSpeedBlock", at = @At("HEAD"), cancellable = true)
    private void isOnSoulSpeedBlock(CallbackInfoReturnable<Boolean> cir) {
        OriginComponent.getPowers(this, ModifySoulSpeedPower.class).forEach(power -> {
            if (power.blockCondition != null) {
                cir.setReturnValue(power.blockCondition.test(new CachedBlockPosition(this.world, this.getVelocityAffectingPos(), true)));
            }
        });
    }

    @ModifyVariable(method = "addSoulSpeedBoostIfNeeded", at = @At("STORE"), ordinal = 0)
    private int replaceLevelOfSouLSpeed(int i) {
        return i = (int)OriginComponent.modify(this, ModifySoulSpeedPower.class, i);
    }

    @Inject(method = "getVelocityMultiplier", at = @At("HEAD"), cancellable = true)
    private void getVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (OriginComponent.hasPower(this, ModifySoulSpeedPower.class)) {
            int soulSpeedValue = (int)OriginComponent.modify(this, ModifySoulSpeedPower.class, EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, (LivingEntity)(Object)this));
            if (soulSpeedValue <= 0 || !this.isOnSoulSpeedBlock()) {
                cir.setReturnValue(super.getVelocityMultiplier());
            } else {
                cir.setReturnValue(1.0F);
            }
        }
    }

    @Inject(method = "addSoulSpeedBoostIfNeeded", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void itemStack(CallbackInfo ci, int i) {
        int baseValue = (int)OriginComponent.modify(this, ModifySoulSpeedPower.class, 0);
        if (OriginComponent.hasPower(this, ModifySoulSpeedPower.class) && i == baseValue) {
            ci.cancel();
        }
    }

    @Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
    public void getGroup(CallbackInfoReturnable<EntityGroup> cir) {
        List<SetEntityGroupPower> originsGroups = OriginComponent.getPowers(this, SetEntityGroupPower.class);
        List<SetTMOEntityGroupPower> apugliGroups = OriginComponent.getPowers(this, SetTMOEntityGroupPower.class);
        if(apugliGroups.size() > 0) {
            if(apugliGroups.size() > 1 || originsGroups.size() > 0) {
                TooManyOrigins.LOGGER.warn("Player " + this.getDisplayName().toString() + " has two or more instances of SetEntityGroupPower/SetTMOEntityGroupPower.");
            }
            cir.setReturnValue(apugliGroups.get(0).group);
        }
    }

    @Inject(method = "isUndead", at = @At("HEAD"), cancellable = true)
    private void isUndead(CallbackInfoReturnable<Boolean> cir) {
        if (((LivingEntity)(Object)this).getGroup() == TMOEntityGroups.PLAYER_UNDEAD) {
            cir.setReturnValue(true);
        }
    }

    @Unique private int apugli_amountOfEdiblePower = 0;
    @Unique
    private int apugli_framesOnGround;
    @Unique private int apugli_velocityMultiplier;

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (OriginComponent.getPowers(this, EdibleItemStackPower.class).size() != apugli_amountOfEdiblePower) {
            if ((LivingEntity)(Object)this instanceof PlayerEntity) {
                if (this.age % 10 == 0) {
                    for (int i = 0; i < ((PlayerEntity)(Object)this).inventory.main.size(); i++) {
                        ItemStack itemStack = ((PlayerEntity)(Object)this).inventory.main.get(i);
                        ItemStackFoodComponentAPI.removeStackFood(itemStack);
                    }
                    for (int i = 0; i < ((PlayerEntity)(Object)this).inventory.armor.size(); i++) {
                        ItemStack armorStack = ((PlayerEntity)(Object)this).inventory.getArmorStack(i);
                        ItemStackFoodComponentAPI.removeStackFood(armorStack);
                    }
                    ItemStack offHandStack = ((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.OFFHAND);
                    ItemStackFoodComponentAPI.removeStackFood(offHandStack);
                    apugli_amountOfEdiblePower = OriginComponent.getPowers(this, EdibleItemStackPower.class).size();
                }
            }
        }
        OriginComponent.getPowers(this, EdibleItemStackPower.class).forEach(EdibleItemStackPower::tempTick);
        if (OriginComponent.hasPower(this, BunnyHopPower.class)) {
            if (apugli_framesOnGround > 4) {
                this.apugli_velocityMultiplier = 0;
            }
            if (this.onGround || this.isTouchingWater() || this.isInLava() || this.hasVehicle() || this.isFallFlying() || (this.getVelocity().getX() == 0 && this.getVelocity().getZ() == 0)) {
                if (apugli_framesOnGround <= 4) {
                    apugli_framesOnGround += 1;
                }
            } else {
                this.apugli_framesOnGround = 0;
            }
        }
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void travel(Vec3d movementInput, CallbackInfo ci) {
        if (OriginComponent.hasPower(this, BunnyHopPower.class)) {
            if (this.apugli_framesOnGround <= 4) {
                if (this.apugli_framesOnGround == 0) {
                    if (this.age % OriginComponent.getPowers(this, BunnyHopPower.class).get(0).tickRate == 0) {
                        this.apugli_velocityMultiplier = (int)Math.min(apugli_velocityMultiplier + 1, OriginComponent.getPowers(this, BunnyHopPower.class).get(0).maxVelocity  / OriginComponent.getPowers(this, BunnyHopPower.class).get(0).increasePerTick);
                    }
                }
            }
            this.updateVelocity((float) OriginComponent.getPowers(this, BunnyHopPower.class).get(0).increasePerTick * apugli_velocityMultiplier, movementInput);
        }
    }

    @Unique
    public void addVelocityMultiplier(int value) {
        apugli_velocityMultiplier += value;
    }

    @Unique
    public int getApugliVelocityMultiplier() {
        return apugli_velocityMultiplier;
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void makeUndeadImmuneToEffects(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        StatusEffect statusEffect = effect.getEffectType();
        if (((LivingEntity)(Object)this).getGroup() == EntityGroup.UNDEAD) {
            if (statusEffect == TMOEffects.ZOMBIFYING) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "applyEnchantmentsToDamage", at = @At(value = "RETURN", opcode = 2),  cancellable = true)
    private void applyEnchantmentsToDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        int k;
        if (this.hasStatusEffect(TMOEffects.SOUL_SHIELD) && source != DamageSource.OUT_OF_WORLD) {
            k = (this.getStatusEffect(TMOEffects.SOUL_SHIELD).getAmplifier() + 1) * 5;
            int j = 25 - k;
            float f = amount * (float) j;
            float g = amount;
            amount = Math.max(f / 25.0F, 0.0F);
            float h = g - amount;
            if (h > 0.0F && h < 3.4028235E37F) {
                if ((Object) this instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) (Object) this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(h * 10.0F));
                } else if (source.getAttacker() instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(h * 10.0F));
                }
            }
        }
        cir.setReturnValue(amount);
    }

    @Inject(method = "applyFoodEffects", at = @At("HEAD"))
    private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
        if (((ItemStackAccess)(Object)stack).isItemStackFood()) {
            List<Pair<StatusEffectInstance, Float>> list = ((ItemStackAccess)(Object)stack).getItemStackFoodComponent().getStatusEffects();
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
                Pair<StatusEffectInstance, Float> pair = (Pair)var6.next();
                if (!world.isClient && pair.getFirst() != null && world.random.nextFloat() < pair.getSecond()) {
                    targetEntity.addStatusEffect(new StatusEffectInstance(pair.getFirst()));
                }
            }
        }
    }
}
