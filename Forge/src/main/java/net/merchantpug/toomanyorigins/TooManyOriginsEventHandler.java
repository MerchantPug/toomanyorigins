package net.merchantpug.toomanyorigins;

import net.merchantpug.toomanyorigins.data.LegacyContentManager;
import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.merchantpug.toomanyorigins.data.ModFilePackResources;
import net.merchantpug.toomanyorigins.network.TMOPacketHandler;
import net.merchantpug.toomanyorigins.network.s2c.SyncLegacyContentPacket;
import net.merchantpug.toomanyorigins.registry.TMOEffects;
import net.merchantpug.toomanyorigins.registry.TMOItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.network.PacketDistributor;

public class TooManyOriginsEventHandler {
    @Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventBusHandler {
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
        public static void addReloadListeners(AddReloadListenerEvent event) {
            event.addListener(new LegacyContentManager());
        }

        @SubscribeEvent
        public static void modifyEffectApplicability(MobEffectEvent.Applicable event) {
            MobEffect statusEffect = event.getEffectInstance().getEffect();
            if (event.getEntity().getMobType() == MobType.UNDEAD && statusEffect.equals(TMOEffects.ZOMBIFYING.get())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = TooManyOrigins.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusHandler {
        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.SERVER_DATA) {
                IModFileInfo info = ModList.get().getModFileById(TooManyOrigins.MOD_ID);
                if (info == null) {
                    return;
                }
                IModFile file = info.getFile();
                event.addRepositorySource(consumer -> consumer.accept(Pack.readMetaAndCreate(TooManyOrigins.asResource("legacytoomanyorigins").toString(), Component.translatable("dataPack.toomanyorigins.legacytoomanyorigins.name"), false, (s) -> new ModFilePackResources("toomanyorigins/legacytoomanyorigins", file, "resourcepacks/legacytoomanyorigins"), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.create(c -> Component.translatable("pack.nameAndSource", c, Component.translatable("pack.source.toomanyorigins")).withStyle(ChatFormatting.GRAY), false))));
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
    }

}
