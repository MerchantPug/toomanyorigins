package io.github.merchantpug.toomanyorigins.power;

import io.github.apace100.origins.power.ActiveCooldownPower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.HudRender;
import io.github.merchantpug.toomanyorigins.networking.packet.LightUpBlockPacket;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class LightUpBlockPower extends ActiveCooldownPower {
    private final int burnTime;
    private final ParticleType particle;
    private final int particleCount;
    private final SoundEvent soundEvent;

    public LightUpBlockPower(PowerType<?> type, PlayerEntity player, int cooldownDuration, HudRender hudRender, int burnTime, ParticleType particle, int particleCount, SoundEvent soundEvent) {
        super(type, player, cooldownDuration, hudRender, null);
        this.burnTime = burnTime;
        this.particle = particle;
        this.particleCount = particleCount;
        this.soundEvent = soundEvent;
    }

    public void onUse() {
        if (this.canUse()) {
            if (player.world.isClient) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
                    BlockState state = client.world.getBlockState(pos);
                    if (state.getBlock() instanceof AbstractFurnaceBlock || state.getBlock() instanceof CampfireBlock) {
                        LightUpBlockPacket.send(pos, particle, particleCount, burnTime, soundEvent);
                        this.use();
                    }
                }
            }
        }
    }
}
