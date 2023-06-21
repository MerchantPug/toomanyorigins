package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID)
public class TooManyOriginsEventHandler {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!event.getEntity().level.isClientSide && !event.getEntity().isRemoved() && event.getEntity() instanceof Villager villager && ((Villager) event.getEntity()).hasEffect(TMOEffects.ZOMBIFYING.get()) && event.getSource().msgId.equals("zombification")) {
            ZombieVillager zombieVillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            if (zombieVillager != null) {
                zombieVillager.finalizeSpawn(((ServerLevel) villager.level), villager.level.getCurrentDifficultyAt(zombieVillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), null);
                zombieVillager.setVillagerData(villager.getVillagerData());
                zombieVillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE).getValue());
                zombieVillager.setTradeOffers(villager.getOffers().createTag());
                zombieVillager.setVillagerXp(villager.getVillagerXp());
            }
        }
    }

    @SubscribeEvent
    public static void modifyEffectApplicability(PotionEvent.PotionApplicableEvent event) {
        MobEffect statusEffect = event.getPotionEffect().getEffect();
        if (event.getEntityLiving().getMobType() == MobType.UNDEAD && statusEffect.equals(TMOEffects.ZOMBIFYING.get())) {
            event.setResult(Event.Result.DENY);
        }
    }

}
