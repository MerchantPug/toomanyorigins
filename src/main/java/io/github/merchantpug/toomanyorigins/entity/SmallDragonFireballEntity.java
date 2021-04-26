package io.github.merchantpug.toomanyorigins.entity;

import io.github.merchantpug.toomanyorigins.networking.packet.EntitySpawnPacket;
import io.github.merchantpug.toomanyorigins.registry.TMOEffects;
import io.github.merchantpug.toomanyorigins.registry.TMOEntities;
import io.github.merchantpug.toomanyorigins.registry.TMOItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import io.github.merchantpug.toomanyorigins.TooManyOriginsClient;

import java.util.Iterator;
import java.util.List;

public class SmallDragonFireballEntity extends ThrownItemEntity {

    public SmallDragonFireballEntity(EntityType<? extends SmallDragonFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public SmallDragonFireballEntity(World world, LivingEntity owner) {
        super(TMOEntities.SMALL_DRAGON_FIREBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public SmallDragonFireballEntity(World world, double x, double y, double z) {
        super(TMOEntities.SMALL_DRAGON_FIREBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return TMOItems.DRAGON_FIREBALL;
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        Entity entity = this.getOwner();
        if (hitResult.getType() != HitResult.Type.ENTITY) {
            if (!this.world.isClient) {
                List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2.0D, 1.0D, 2.0D));
                FireballAreaEffectCloudEntity areaEffectCloudEntity = new FireballAreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity) {
                    areaEffectCloudEntity.setOwner((LivingEntity)entity);
                }
                areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
                areaEffectCloudEntity.setRadius(1.0F);
                areaEffectCloudEntity.setDuration(60);
                areaEffectCloudEntity.setWaitTime(0);
                areaEffectCloudEntity.setRadiusGrowth((1.5F - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(TMOEffects.END_FIRE, 1, 0));
                if (!list.isEmpty()) {
                    Iterator var5 = list.iterator();

                    while (var5.hasNext()) {
                        LivingEntity livingEntity = (LivingEntity) var5.next();
                        double d = this.squaredDistanceTo(livingEntity);
                        if (d < 4.0D && !(livingEntity instanceof PlayerEntity) && list.size() == 1) {
                            areaEffectCloudEntity.updatePosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            break;
                        }
                    }
                }

                this.world.syncWorldEvent(1520, this.getBlockPos(), this.isSilent() ? -1 : 1);
                this.world.spawnEntity(areaEffectCloudEntity);
                this.remove();
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.remove();
        } else {
            super.tick();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, TooManyOriginsClient.PacketID);
    }
}
