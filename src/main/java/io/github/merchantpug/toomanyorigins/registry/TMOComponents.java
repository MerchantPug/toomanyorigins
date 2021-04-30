package io.github.merchantpug.toomanyorigins.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.component.*;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class TMOComponents implements EntityComponentInitializer {
    public static final ComponentKey<ExtraHealthComponent> EXTRA_HEALTH = ComponentRegistry.getOrCreate(new Identifier(TooManyOrigins.MODID, "extra_health"), ExtraHealthComponent.class);
    public static final ComponentKey<TimeAliveComponent> TIME_ALIVE = ComponentRegistry.getOrCreate(new Identifier(TooManyOrigins.MODID, "time_alive"), TimeAliveComponent.class);

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(EXTRA_HEALTH, player -> new PlayerExtraHealthComponent(0, true), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(TIME_ALIVE, player -> new PlayerTimeAliveComponent(0), RespawnCopyStrategy.ALWAYS_COPY);
    }

    public static void setExtraHealth(ServerPlayerEntity serverPlayerEntity, int value) {
        EXTRA_HEALTH.get(serverPlayerEntity).setExtraHealth(value);
    }
    public static int getExtraHealth(ServerPlayerEntity serverPlayerEntity) {
        return EXTRA_HEALTH.get(serverPlayerEntity).getExtraHealth();
    }
    public static void setNewPlayer(ServerPlayerEntity serverPlayerEntity, boolean value) {
        EXTRA_HEALTH.get(serverPlayerEntity).setNewPlayer(value);
    }
    public static boolean getNewPlayer(ServerPlayerEntity serverPlayerEntity) {
        return EXTRA_HEALTH.get(serverPlayerEntity).getNewPlayer();
    }
    public static void setTimeAlive(ServerPlayerEntity serverPlayerEntity, int value) {
        TIME_ALIVE.get(serverPlayerEntity).setTimeAlive(value);
    }

    public static int getTimeAlive(ServerPlayerEntity serverPlayerEntity) {
        return TIME_ALIVE.get(serverPlayerEntity).getTimeAlive();
    }
}
