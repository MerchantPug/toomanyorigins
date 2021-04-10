package io.github.merchantpug.toomanyorigins.networking.packet;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class EntitySpawnPacket {
    public static Packet<?> create(Entity e, Identifier packetID) {
        if (e.world.isClient)
            throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
        byteBuf.writeUuid(e.getUuid());
        byteBuf.writeVarInt(e.getEntityId());

        PacketBufUtil.writeVec3d(byteBuf, e.getPos());
        PacketBufUtil.writeAngle(byteBuf, e.pitch);
        PacketBufUtil.writeAngle(byteBuf, e.yaw);
        return ServerSidePacketRegistry.INSTANCE.toPacket(packetID, byteBuf);
    }
    public static final class PacketBufUtil {

        public static byte packAngle(float angle) {
            return (byte) MathHelper.floor(angle * 256 / 360);
        }

        public static float unpackAngle(byte angleByte) {
            return (angleByte * 360) / 256f;
        }

        public static void writeAngle(PacketByteBuf byteBuf, float angle) {
            byteBuf.writeByte(packAngle(angle));
        }

        public static float readAngle(PacketByteBuf byteBuf) {
            return unpackAngle(byteBuf.readByte());
        }

        public static void writeVec3d(PacketByteBuf byteBuf, Vec3d vec3d) {
            byteBuf.writeDouble(vec3d.x);
            byteBuf.writeDouble(vec3d.y);
            byteBuf.writeDouble(vec3d.z);
        }

        public static Vec3d readVec3d(PacketByteBuf byteBuf) {
            double x = byteBuf.readDouble();
            double y = byteBuf.readDouble();
            double z = byteBuf.readDouble();
            return new Vec3d(x, y, z);
        }
    }
}
