package com.github.merchantpug.toomanyorigins.mixin.forge;

import com.github.merchantpug.toomanyorigins.entity.FireballAreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireballAreaEffectCloudEntity.class)
abstract class FireballAreaEffectCloudMixin extends Entity {
	public FireballAreaEffectCloudMixin(EntityType<? extends Entity> arg, World arg2) {
		super(arg, arg2);
	}

	public Packet<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}