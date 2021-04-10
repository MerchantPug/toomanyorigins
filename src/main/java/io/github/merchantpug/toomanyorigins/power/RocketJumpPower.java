package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.toomanyorigins.networking.packet.RocketJumpPacket;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;

public class RocketJumpPower extends ActiveCooldownPower {
    private Key key;
    private final DamageSource damageSource;
    private final float damageAmount;
    private final double speed;

    public RocketJumpPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, DamageSource damageSource, float damageAmount, double speed) {
        super(type, player, cooldownDuration, hudRender, null);
        this.damageSource = damageSource;
        this.damageAmount = damageAmount;
        this.speed = speed;

    }

    @Override
    public void onUse() {
        if (canUse()) {
            if (player.world.isClient) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK || client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    RocketJumpPacket.send(damageSource, damageAmount, speed);
                    this.use();
                }
            }
        }
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
