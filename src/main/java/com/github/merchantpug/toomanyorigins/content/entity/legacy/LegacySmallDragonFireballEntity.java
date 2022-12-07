package com.github.merchantpug.toomanyorigins.content.entity.legacy;

import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.power.ModifyDragonFireballPower;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import com.github.merchantpug.toomanyorigins.registry.TMOItems;
import io.github.apace100.apoli.component.PowerHolderComponent;
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

public class LegacySmallDragonFireballEntity extends ThrownItemEntity {

    public LegacySmallDragonFireballEntity(EntityType<? extends LegacySmallDragonFireballEntity> entityType, World world) {
        super(entityType, world);
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.DRAGON_FIREBALL))
            this.discard();
    }

    public LegacySmallDragonFireballEntity(World world, LivingEntity owner) {
        super(TMOEntities.SMALL_DRAGON_FIREBALL, owner, world);
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.DRAGON_FIREBALL)) {
            this.discard();
        }
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
                List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2.5D, 1.0D, 2.5D), otherEntity -> !(otherEntity instanceof PlayerEntity));
                LegacyFireballAreaEffectCloudEntity areaEffectCloudEntity = new LegacyFireballAreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity) {
                    areaEffectCloudEntity.setOwner((LivingEntity)entity);
                }
                areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
                areaEffectCloudEntity.setWaitTime(0);
                float minRadius = 1.125F;
                float maxRadius = 2.25F;
                int duration = 60;
                float damage = 6.0F;
                if(this.getOwner() != null && PowerHolderComponent.hasPower(this.getOwner(), ModifyDragonFireballPower.class)) {
                    minRadius = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMinRadius();
                    maxRadius = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getMaxRadius();
                    duration = PowerHolderComponent.getPowers(this.getOwner(), ModifyDragonFireballPower.class).get(0).getDuration();
                    damage = PowerHolderComponent.modify(this.getOwner(), ModifyDragonFireballPower.class, 6.0F);
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
                this.discard();
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof PlayerEntity && !entity.isAlive()) {
            this.discard();
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
