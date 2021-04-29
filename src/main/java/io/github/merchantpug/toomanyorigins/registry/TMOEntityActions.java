package io.github.merchantpug.toomanyorigins.registry;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;

public class TMOEntityActions {
    public static void register() {
        register(new ActionFactory<>(new Identifier(TooManyOrigins.MODID, "zombify_villager"), new SerializableData(),
            (data, entity) -> {
                if (entity instanceof VillagerEntity) {
                    VillagerEntity villagerEntity = (VillagerEntity)entity;
                    ZombieVillagerEntity zombieVillagerEntity = villagerEntity.method_29243(EntityType.ZOMBIE_VILLAGER, false);
                    if (zombieVillagerEntity != null) {
                        zombieVillagerEntity.initialize((ServerWorldAccess)entity.world, entity.world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), null);
                        zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
                        zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
                        zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toTag());
                        zombieVillagerEntity.setXp(villagerEntity.getExperience());
                    }
                    villagerEntity.damage(TMODamageSources.zombification(((VillagerEntity)entity).getAttacker()), 20.0F);
                    entity.world.syncWorldEvent(null, 1527, entity.getBlockPos(), 0);
                }
            }));
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
