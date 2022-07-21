package com.github.merchantpug.toomanyorigins.mixin.forge;

import com.github.merchantpug.toomanyorigins.entity.SmallDragonFireballEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SmallDragonFireballEntity.class)
abstract class SmallDragonFireballEntityMixin extends ThrownItemEntity {
	public SmallDragonFireballEntityMixin(EntityType<? extends ThrownItemEntity> arg, World arg2) { super(arg, arg2); }

	public Packet<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}