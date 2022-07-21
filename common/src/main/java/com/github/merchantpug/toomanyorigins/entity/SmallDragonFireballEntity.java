package com.github.merchantpug.toomanyorigins.entity;

import com.github.merchantpug.toomanyorigins.registry.TMOItems;
import dev.architectury.injectables.annotations.ExpectPlatform;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class SmallDragonFireballEntity extends ThrownItemEntity {

    public SmallDragonFireballEntity(EntityType<? extends SmallDragonFireballEntity> type, World world) {
        super(type, world);
    }

    public SmallDragonFireballEntity(World world) {
        super(TMOEntities.SMALL_DRAGON_FIREBALL, world);
    }

    public SmallDragonFireballEntity(World world, LivingEntity owner) {
        this(TMOEntities.SMALL_DRAGON_FIREBALL, world);
        this.updatePosition(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
        this.setOwner(owner);
    }

    @Environment(EnvType.CLIENT)
    public SmallDragonFireballEntity(World world, double x, double y, double z) {
        this(world);
        this.updatePosition(x, y, z);
    }

    @Override
    protected Item getDefaultItem() {
        return TMOItems.DRAGON_FIREBALL;
    }

    @ExpectPlatform
    public static float getModifiedMinRadius(LivingEntity owner, float originalValue) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getModifiedMaxRadius(LivingEntity owner, float originalValue) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getModifiedDuration(LivingEntity owner, int originalValue) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getModifiedDamage(LivingEntity owner, float originalValue) {
        throw new AssertionError();
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
                float damage = 6.0F;
                if(this.getOwner() != null) {
                    minRadius = getModifiedMinRadius((LivingEntity)this.getOwner(), minRadius);
                    maxRadius = getModifiedMaxRadius((LivingEntity)this.getOwner(), maxRadius);
                    duration = getModifiedDuration((LivingEntity)this.getOwner(), duration);
                    damage = getModifiedDamage((LivingEntity)this.getOwner(), damage);
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
            Vec3d vec3d = this.getVelocity();
            double d = this.getX() + vec3d.x;
            double e = this.getY() + (this.getHeight() / 2);
            double f = this.getZ() + vec3d.z;
            this.world.addParticle(ParticleTypes.DRAGON_BREATH, d, e, f, (this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D, Math.abs(this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D, (this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D);
        }
    }
}
