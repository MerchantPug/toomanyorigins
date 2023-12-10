package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.merchantpug.toomanyorigins.network.TMOPacketHandler;
import net.merchantpug.toomanyorigins.network.s2c.SyncLegacyContentPacket;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.merchantpug.toomanyorigins.registry.TMOItems;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID)
public class TooManyOriginsEventHandler {

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        TMOPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(event::getPlayer), new SyncLegacyContentPacket(LegacyContentRegistry.isDragonFireballEnabled(), LegacyContentRegistry.areWitheredCropsEnabled(), LegacyContentRegistry.isZombifyingEffectEnabled()));
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide && !event.getEntity().isRemoved() && event.getEntity() instanceof Villager villager && event.getEntity().hasEffect(TMOEffects.ZOMBIFYING.get()) && event.getSource().getMsgId().equals("zombification")) {
            ZombieVillager zombieVillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            if (zombieVillager != null) {
                zombieVillager.finalizeSpawn(((ServerLevel) villager.level()), villager.level().getCurrentDifficultyAt(zombieVillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), null);
                zombieVillager.setVillagerData(villager.getVillagerData());
                zombieVillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                zombieVillager.setTradeOffers(villager.getOffers().createTag());
                zombieVillager.setVillagerXp(villager.getVillagerXp());
            }
        }
    }

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT && LegacyContentRegistry.isDragonFireballEnabled()) {
            event.accept(TMOItems.DRAGON_FIREBALL.get());
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS && LegacyContentRegistry.areWitheredCropsEnabled()) {
            event.accept(TMOItems.WITHERED_CROP_SEEDS.get());
            event.accept(TMOItems.WITHERED_STEM_SEEDS.get());
        }
    }

    @SubscribeEvent
    public static void modifyEffectApplicability(MobEffectEvent.Applicable event) {
        MobEffect statusEffect = event.getEffectInstance().getEffect();
        if (event.getEntity().getMobType() == MobType.UNDEAD && statusEffect.equals(TMOEffects.ZOMBIFYING.get())) {
            event.setResult(Event.Result.DENY);
        }
    }

}
