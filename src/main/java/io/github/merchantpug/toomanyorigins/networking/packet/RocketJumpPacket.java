package io.github.merchantpug.toomanyorigins.networking.packet;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.mixin.DamageSourceAccessor;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import io.github.merchantpug.toomanyorigins.power.RocketJumpPower;
import io.github.merchantpug.toomanyorigins.registry.TMOComponents;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.explosion.Explosion;


public class RocketJumpPacket {
    public static final Identifier ID = new Identifier(TooManyOrigins.MODID, "rocket_jump");

    public static void send(DamageSource damageSource, float damageAmount, double speed) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(damageSource.getName());
        buf.writeBoolean(damageSource.bypassesArmor());
        buf.writeBoolean(damageSource.isFire());
        buf.writeBoolean(damageSource.isUnblockable());
        buf.writeBoolean(damageSource.getMagic());
        buf.writeBoolean(damageSource.isOutOfWorld());
        buf.writeFloat(damageAmount);
        buf.writeDouble(speed);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        DamageSource damageSource = DamageSourceAccessor.createDamageSource(buf.readString());
        DamageSourceAccessor accessor = (DamageSourceAccessor)damageSource;
        if (buf.readBoolean()) {
            accessor.callSetBypassesArmor();
        }
        if (buf.readBoolean()) {
            accessor.callSetFire();
        }
        if (buf.readBoolean()) {
            accessor.callSetUnblockable();
        }
        if (buf.readBoolean()) {
            accessor.callSetUsesMagic();
        }
        if (buf.readBoolean()) {
            accessor.callSetOutOfWorld();
        }
        float damageAmount = buf.readFloat();
        double speed = buf.readDouble();
        server.execute(() -> {
            double d = player.hasStatusEffect(TMOEffects.CHARGED) ? 2.0D : 1.0D;
            float e = player.hasStatusEffect(TMOEffects.CHARGED) ? 2.0F : 1.5F;
            float f = MathHelper.sin(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
            float g = MathHelper.sin(player.pitch * 0.017453292F);
            float h = -MathHelper.cos(player.yaw * 0.017453292F) * MathHelper.cos(player.pitch * 0.017453292F);
            player.damage((DamageSource)accessor, damageAmount);
            player.world.createExplosion(player, TMODamageSources.jumpExplosion(player), null, player.getX(), player.getY(), player.getZ(), f, false, Explosion.DestructionType.NONE);
            player.addVelocity(f * speed * d, g * speed * d * 0.8, h * speed * d);
            player.velocityModified = true;
            if (player.hasStatusEffect(TMOEffects.CHARGED)) {
                player.removeStatusEffect(TMOEffects.CHARGED);
            }
        });
    }
}
