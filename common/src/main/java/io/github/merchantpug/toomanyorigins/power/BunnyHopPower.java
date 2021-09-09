package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.toomanyorigins.access.LivingEntityAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class BunnyHopPower extends ActiveCooldownPower {
    private Key key;
    public final double increasePerTick;
    private final int abilityVelocity;
    public final double maxVelocity;
    private final SoundEvent soundEvent;
    public final int tickRate;

    public BunnyHopPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, double increasePerTick, int abilityVelocity, double maxVelocity, SoundEvent soundEvent, int tickRate) {
        super(type, player, cooldownDuration, hudRender, null);
        this.increasePerTick = increasePerTick;
        this.abilityVelocity = abilityVelocity;
        this.maxVelocity = maxVelocity;
        this.soundEvent = soundEvent;
        this.tickRate = tickRate;
    }

    @Override
    public void onUse() {
        if (((LivingEntityAccess)player).getApugliVelocityMultiplier() < this.maxVelocity / this.increasePerTick) {
            if (canUse()) {
                ((LivingEntityAccess)player).addVelocityMultiplier(this.abilityVelocity);
                if (soundEvent != null) {
                    player.world.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, SoundCategory.PLAYERS, 1.0F, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + 1.0F);
                }
                float f = MathHelper.sin(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
                float h = -MathHelper.cos(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
                if (!player.world.isClient) {
                    ((ServerWorld) player.world).spawnParticles(ParticleTypes.CLOUD, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + player.getX(), player.getY() + 0.5, (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + player.getZ(), 8, f * 0.25D, 0.0D, h * 0.25D, 0.2);
                }
                this.use();
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
