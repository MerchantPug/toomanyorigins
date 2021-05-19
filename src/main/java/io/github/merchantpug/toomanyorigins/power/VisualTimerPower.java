package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import net.minecraft.entity.player.PlayerEntity;

public class VisualTimerPower extends InvertedCooldownPower {
    private final boolean resetOnRespawn;

    public VisualTimerPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, boolean resetOnRespawn) {
        super(type, player, cooldownDuration, hudRender);
        this.resetOnRespawn = resetOnRespawn;
    }

    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        super.onChosen(isOrbOfOrigin);
        if (isOrbOfOrigin) {
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
