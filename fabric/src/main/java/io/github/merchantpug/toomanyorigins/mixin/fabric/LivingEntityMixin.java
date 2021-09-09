package io.github.merchantpug.toomanyorigins.mixin.fabric;

import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect statusEffect);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void zombifyVillagerOnDeath(DamageSource source, CallbackInfo ci) {
        if (!this.removed) {
            if ((LivingEntity)(Object)this instanceof VillagerEntity) {
                if ((this.hasStatusEffect(TMOEffects.ZOMBIFYING) && source.getName().equals("zombification")) || TMOPowers.DEATHLY_BITE.isActive(source.getAttacker()) && TMOPowers.DEATHLY_BITE.get(source.getAttacker()).canUse()) {
                    VillagerEntity villagerEntity = (VillagerEntity)(Object)this;
                    ZombieVillagerEntity zombieVillagerEntity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
                    if (zombieVillagerEntity != null) {
                        zombieVillagerEntity.initialize((ServerWorldAccess)villagerEntity.world, villagerEntity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
                        zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
                        zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
                        zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toTag());
                        zombieVillagerEntity.setXp(villagerEntity.getExperience());
                        villagerEntity.world.syncWorldEvent(null, 1026, zombieVillagerEntity.getBlockPos(), 0);
                    }
                }
            }
        }
    }
}
