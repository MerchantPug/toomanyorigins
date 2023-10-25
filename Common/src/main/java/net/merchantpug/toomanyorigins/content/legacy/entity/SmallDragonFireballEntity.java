package net.merchantpug.toomanyorigins.entity;

import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.merchantpug.toomanyorigins.registry.TMOItems;
import net.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SmallDragonFireballEntity extends ThrowableItemProjectile {

    public SmallDragonFireballEntity(EntityType<? extends SmallDragonFireballEntity> entityType, Level world) {
        super(entityType, world);
    }

    public SmallDragonFireballEntity(Level world, LivingEntity owner) {
        super(TMOEntityTypes.SMALL_DRAGON_FIREBALL.get(), owner, world);
    }

    public SmallDragonFireballEntity(Level world, double x, double y, double z) {
        super(TMOEntityTypes.SMALL_DRAGON_FIREBALL.get(), x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return TMOItems.DRAGON_FIREBALL.get();
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        Entity entity = this.getOwner();
        if (hitResult.getType() != HitResult.Type.ENTITY) {
            if (!this.level().isClientSide) {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5D, 1.0D, 2.5D), (e) -> !(e instanceof Player));
                FireballAreaEffectCloudEntity areaEffectCloudEntity = new FireballAreaEffectCloudEntity(this.level(), this.getX(), this.getY(), this.getZ());
                if (entity instanceof LivingEntity living) {
                    areaEffectCloudEntity.setOwner(living);
                }
                areaEffectCloudEntity.setParticle(ParticleTypes.DRAGON_BREATH);
                areaEffectCloudEntity.setWaitTime(0);
                float minRadius = TMOPowers.MODIFY_DRAGON_FIREBALL.get().modifyMinRadius(this.getOwner(), 1.125F);
                float maxRadius = TMOPowers.MODIFY_DRAGON_FIREBALL.get().modifyMaxRadius(this.getOwner(), 2.25F);
                int duration = (int) TMOPowers.MODIFY_DRAGON_FIREBALL.get().modifyDuration(this.getOwner(), 60);
                float damage = TMOPowers.MODIFY_DRAGON_FIREBALL.get().modifyDamage(this.getOwner(), 6.0F);
                areaEffectCloudEntity.setRadius(minRadius);
                areaEffectCloudEntity.setDuration(duration);
                areaEffectCloudEntity.setDamage(damage);
                areaEffectCloudEntity.setRadiusPerTick((maxRadius - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
                if (!list.isEmpty()) {
                    for (LivingEntity livingEntity : list) {
                        double d = this.distanceToSqr(livingEntity);
                        if (d < 6.25D && list.size() == 1) {
                            areaEffectCloudEntity.absMoveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            break;
                        }
                    }
                }

                this.level().globalLevelEvent(1520, this.blockPosition(), this.isSilent() ? -1 : 1);
                this.level().addFreshEntity(areaEffectCloudEntity);
                this.discard();
            }
        }
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
            Vec3 vec3d = this.getDeltaMovement();
            double d = this.getX() + vec3d.x;
            double e = this.getY() + (this.getBbHeight() / 2);
            double f = this.getZ() + vec3d.z;
            this.level().addParticle(ParticleTypes.DRAGON_BREATH, d, e, f, (this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D, Math.abs(this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D, (this.random.nextDouble() - this.random.nextDouble() + 0.2D) * 0.1D);
        }
    }
}
