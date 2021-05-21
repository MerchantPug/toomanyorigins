package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.*;
import io.github.apace100.origins.registry.ModComponents;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.power.ExtraSoulSpeedPower;
import io.github.merchantpug.toomanyorigins.power.SetTMOEntityGroupPower;
import io.github.merchantpug.toomanyorigins.power.UnenchantedSoulSpeedPower;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOEntityGroups;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract EntityGroup getGroup();

    @Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "shouldDisplaySoulSpeedEffects", at = @At("HEAD"), cancellable = true)
    private void shouldDisplaySoulSpeedEffects(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.age % 5 == 0 && this.getVelocity().x != 0.0D && this.getVelocity().z != 0.0D && !this.isSpectator() && (EnchantmentHelper.hasSoulSpeed((LivingEntity) (Object) this) || OriginComponent.hasPower(this, UnenchantedSoulSpeedPower.class) || OriginComponent.hasPower(this, ExtraSoulSpeedPower.class)) && ((LivingEntityAccess)this).invokeIsOnSoulSpeedBlock());
    }

    @ModifyVariable(method = "addSoulSpeedBoostIfNeeded", at = @At("STORE"), ordinal = 0)
    private int replaceLevelOfSouLSpeed(int i) {
        if (OriginComponent.hasPower(this, UnenchantedSoulSpeedPower.class) && i <= OriginComponent.getPowers(this, UnenchantedSoulSpeedPower.class).get(0).getModifier()) {
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

    @Inject(method = "getGroup", at = @At("HEAD"), cancellable = true)
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

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void zombifyVillagerOnDeath(DamageSource source, CallbackInfo ci) {
        if (!this.removed && !((LivingEntityAccess)this).getDead()) {
            if ((Object)this instanceof VillagerEntity) {
                if (this.hasStatusEffect(TMOEffects.ZOMBIFYING) && source.getName().equals("zombification")) {
                    VillagerEntity villagerEntity = (VillagerEntity)(Object)this;
                    ZombieVillagerEntity zombieVillagerEntity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
                    if (zombieVillagerEntity != null) {
                        zombieVillagerEntity.initialize((ServerWorldAccess)villagerEntity.world, villagerEntity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
                        zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
                        zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
                        zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toTag());
                        zombieVillagerEntity.setXp(villagerEntity.getExperience());
                        zombieVillagerEntity.world.playSound(zombieVillagerEntity.getX(), zombieVillagerEntity.getY(), zombieVillagerEntity.getZ(), SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0F,(zombieVillagerEntity.getRandom().nextFloat() - zombieVillagerEntity.getRandom().nextFloat()) * 0.2F + 1.0F, false);
                        villagerEntity.world.syncWorldEvent(null, 1526, zombieVillagerEntity.getBlockPos(), 0);
                    }
                }
            }
        }
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void makeUndeadImmuneToEffects(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        StatusEffect statusEffect = effect.getEffectType();
        if (this.getGroup() == EntityGroup.UNDEAD) {
            if (statusEffect == TMOEffects.ZOMBIFYING) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "isUndead", at = @At("HEAD"), cancellable = true)
    private void isUndead(CallbackInfoReturnable<Boolean> cir) {
        if (this.getGroup() == TMOEntityGroups.PLAYER_UNDEAD) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "applyEnchantmentsToDamage", at = @At("HEAD"),  cancellable = true)
    private void applyEnchantmentsToDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (!source.isUnblockable()) {
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
        }
    }
}
