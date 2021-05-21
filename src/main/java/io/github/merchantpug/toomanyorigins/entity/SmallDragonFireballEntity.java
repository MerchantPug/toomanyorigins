package io.github.merchantpug.toomanyorigins.entity;

import io.github.apace100.origins.component.OriginComponent;
import io.github.merchantpug.toomanyorigins.networking.packet.EntitySpawnPacket;
import io.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import io.github.merchantpug.toomanyorigins.registry.TMOEntities;
import io.github.merchantpug.toomanyorigins.registry.TMOItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
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
                List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2.5D, 1.0D, 2.5D), TMOEntityPredicates.EXCEPT_PLAYER);
                FireballAreaEffectCloudEntity areaEffectCloudEntity = new FireballAreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity) {
                    areaEffectCloudEntity.setOwner((LivingEntity)entity);
                }
                areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
                areaEffectCloudEntity.setWaitTime(0);
                float minRadius = 1.125F;
                float maxRadius = 2.25F;
                int duration = 60;
                float damage = 5.0F;
                if(this.getOwner() != null && OriginComponent.hasPower(this.getOwner(), ModifyDragonFireballPower.class)) {
                    minRadius = OriginComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMinRadius();
                    maxRadius = OriginComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMaxRadius();
                    duration = OriginComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getDuration();
                    damage = OriginComponent.modify(this.getOwner(), ModifyDragonFireballPower.class, 5.0F);
                }
                areaEffectCloudEntity.setRadius(minRadius);
                areaEffectCloudEntity.setDuration(duration);
                areaEffectCloudEntity.setDamage(damage);
                areaEffectCloudEntity.setRadiusGrowth((maxRadius - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
                if (!list.isEmpty()) {
                    Iterator var5 = list.iterator();
                    while (var5.hasNext()) {
                        LivingEntity livingEntity = (LivingEntity)var5.next();
                        double d = this.squaredDistanceTo(livingEntity);
                        if (d < 6.25D && list.size() == 1) {
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
