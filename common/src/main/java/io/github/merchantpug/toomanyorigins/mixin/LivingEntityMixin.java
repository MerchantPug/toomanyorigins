package io.github.merchantpug.toomanyorigins.mixin;

import com.mojang.datafixers.util.Pair;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.ModifyDamageTakenPower;
import io.github.apace100.origins.power.SetEntityGroupPower;
import io.github.merchantpug.apugli.Apugli;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.access.ItemStackAccess;
import io.github.merchantpug.toomanyorigins.access.LivingEntityAccess;
import io.github.merchantpug.toomanyorigins.power.SimpleValueModifierPower;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOPowers;
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
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect statusEffect);

    @Shadow public abstract EntityGroup getGroup();

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
                        villagerEntity.world.syncWorldEvent(null, 1026, this.getBlockPos(), 0);
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
}
