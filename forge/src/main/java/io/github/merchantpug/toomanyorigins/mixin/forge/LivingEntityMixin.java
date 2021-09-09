package io.github.merchantpug.toomanyorigins.mixin.forge;

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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect p_70644_1_);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void zombifyVillagerOnDeath(DamageSource source, CallbackInfo ci) {
        if (!this.removed) {
            if ((LivingEntity)(Object)this instanceof VillagerEntity) {
                if ((this.hasStatusEffect(TMOEffects.ZOMBIFYING) && source.getName().equals("zombification")) || TMOPowers.DEATHLY_BITE.isActive(source.getAttacker()) && TMOPowers.DEATHLY_BITE.get(source.getAttacker()).canUse()) {
                    VillagerEntity villagerEntity = (VillagerEntity)(Object)this;
                    ServerWorld serverWorld = (ServerWorld)villagerEntity.getEntityWorld();
                    ZombieVillagerEntity zombievillagerentity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
                    zombievillagerentity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombievillagerentity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
                    zombievillagerentity.setVillagerData(villagerEntity.getVillagerData());
                    zombievillagerentity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
                    zombievillagerentity.setOfferData(villagerEntity.getOffers().toTag());
                    zombievillagerentity.setXp(villagerEntity.getExperience());
                    ForgeEventFactory.onLivingConvert(villagerEntity, zombievillagerentity);
                    if (!villagerEntity.isSilent()) {
                        serverWorld.syncWorldEvent(null, 1026, villagerEntity.getBlockPos(), 0);
                    }
                }
            }
        }
    }
}
