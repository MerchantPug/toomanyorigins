package com.github.merchantpug.toomanyorigins.mixin.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import com.github.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import com.github.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;getEntityTypeId()Lnet/minecraft/entity/EntityType;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci, double x, double y, double z, EntityType<?> type) {
        Entity entity = null;
        if (type == TMOEntities.SMALL_DRAGON_FIREBALL) {
            entity = new SmallDragonFireballEntity(this.world, x, y, z);
        }
        if (type == TMOEntities.FIREBALL_AREA_EFFECT_CLOUD) {
            entity = new FireballAreaEffectCloudEntity(this.world, x, y, z);
        }
        if (entity != null) {
            int i = packet.getId();
            entity.setVelocity(Vec3d.ZERO); // entities always spawn standing still. We may change this later
            entity.updatePosition(x, y, z);
            entity.updateTrackedPosition(x, y, z);
            entity.pitch = (float) (packet.getPitch() * 360) / 256.0F;
            entity.yaw = (float) (packet.getYaw() * 360) / 256.0F;
            entity.setEntityId(i);
            entity.setUuid(packet.getUuid());
            this.world.addEntity(i, entity);
            ci.cancel();
        }
    }
}