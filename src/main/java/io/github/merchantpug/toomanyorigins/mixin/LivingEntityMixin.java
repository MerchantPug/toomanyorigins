package io.github.merchantpug.toomanyorigins.mixin;

import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract EntityGroup getGroup();

    @Shadow public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean isDead();

    @Shadow public abstract float getHealth();

    @Shadow public abstract float getMaxHealth();

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void zombifyVillagerOnDeath(DamageSource source, CallbackInfo ci) {
        if (!this.isRemoved()) {
            if ((LivingEntity)(Object)this instanceof VillagerEntity) {
                if (this.hasStatusEffect(TMOEffects.ZOMBIFYING) && source.getName().equals("zombification")) {
                    VillagerEntity villagerEntity = (VillagerEntity)(Object)this;
                    ZombieVillagerEntity zombieVillagerEntity = villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
                    if (zombieVillagerEntity != null) {
                        zombieVillagerEntity.initialize((ServerWorldAccess)villagerEntity.world, villagerEntity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
                        zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
                        zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
                        zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
                        zombieVillagerEntity.setXp(villagerEntity.getExperience());
                        villagerEntity.world.syncWorldEvent(null, WorldEvents.ZOMBIE_INFECTS_VILLAGER, zombieVillagerEntity.getBlockPos(), 0);
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
}
