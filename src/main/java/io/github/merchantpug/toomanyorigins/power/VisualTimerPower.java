package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.CooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.toomanyorigins.registry.TMOComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class VisualTimerPower extends CooldownPower {
    private final boolean resetOnRespawn;

    public VisualTimerPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, boolean resetOnRespawn) {
        super(type, player, cooldownDuration, hudRender);
        this.resetOnRespawn = resetOnRespawn;
    }

    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        super.onChosen(isOrbOfOrigin);
        if (isOrbOfOrigin) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
            TMOComponents.setTimeAlive(serverPlayer, 0);
            this.use();
        }
    }

    @Override
    public void onRespawn() {
        if (resetOnRespawn) {
            this.use();
        }
    }
}
