package net.merchantpug.toomanyorigins.mixin.fabric;

import net.merchantpug.toomanyorigins.registry.TMODamageTypes;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasEffect(MobEffect effect);

    @Shadow public abstract MobType getMobType();

    public LivingEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "die", at = @At("HEAD"))
    private void zombifyVillagerOnDeath(DamageSource source, CallbackInfo ci) {
        if (!this.level.isClientSide && !this.isRemoved() && (LivingEntity) (Object) this instanceof Villager villager && this.hasEffect(TMOEffects.ZOMBIFYING.get()) && source.is(TMODamageTypes.ZOMBIFICATION)) {
            ZombieVillager zombieVillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            if (zombieVillager != null) {
                zombieVillager.finalizeSpawn(((ServerLevel) level), level.getCurrentDifficultyAt(zombieVillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), null);
                zombieVillager.setVillagerData(villager.getVillagerData());
                zombieVillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                zombieVillager.setTradeOffers(villager.getOffers().createTag());
                zombieVillager.setVillagerXp(villager.getVillagerXp());
            }
        }
    }

    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void makeUndeadImmuneToEffects(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        MobEffect statusEffect = effect.getEffect();
        if (this.getMobType() == MobType.UNDEAD && statusEffect.equals(TMOEffects.ZOMBIFYING.get())) {
            cir.setReturnValue(false);
        }
    }

}
