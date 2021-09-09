package io.github.merchantpug.toomanyorigins.power;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class RocketJumpPower extends ActiveCooldownPower {
    private Key key;
    private DamageSource source;
    private float amount;
    private double speed;
    private boolean useCharged;

    public RocketJumpPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, DamageSource source, float amount, double speed, boolean useCharged) {
        super(type, player, cooldownDuration, hudRender, null);
        this.source = source;
        this.amount = amount;
        this.speed = speed;
        this.useCharged = useCharged;
    }

    @Override
    public void onUse() {
        if (canUse()) {
            if (handleRocketJump(player, source, amount, speed, useCharged)) {
                use();
            }
        }
    }

    @ExpectPlatform
    public static boolean handleRocketJump(PlayerEntity player, DamageSource source, float amount, double speed, boolean useCharged) {
        throw new AssertionError();
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}
