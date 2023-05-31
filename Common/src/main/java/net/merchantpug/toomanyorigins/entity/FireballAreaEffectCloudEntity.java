package net.merchantpug.toomanyorigins.entity;

import net.merchantpug.toomanyorigins.mixin.AreaEffectCloudEntityAccessor;
import net.merchantpug.toomanyorigins.registry.TMODamageTypes;
import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FireballAreaEffectCloudEntity extends AreaEffectCloud {
    private float damage;

    public FireballAreaEffectCloudEntity(EntityType<? extends FireballAreaEffectCloudEntity> entityType, Level world) {
        super(entityType, world);
        this.noPhysics = true;
        this.damage = 6.0F;
        this.setRadius(3.0F);
    }

    public FireballAreaEffectCloudEntity(Level world, double x, double y, double z) {
        this(TMOEntityTypes.FIREBALL_AREA_EFFECT_CLOUD.get(), world);
        this.setPos(x, y, z);
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void tick() {
        super.tick();
        boolean bl = this.isWaiting();
        float f = this.getRadius();
        if (this.level.isClientSide) {
            ParticleOptions particleEffect = this.getParticle();
            float h;
            float j;
            float k;
            int m;
            int n;
            int o;
            if (bl) {
                if (this.random.nextBoolean()) {
                    for(int i = 0; i < 2; ++i) {
                        float g = this.random.nextFloat() * 6.2831855F;
                        h = Mth.sqrt(this.random.nextFloat()) * 0.2F;
                        j = Mth.cos(g) * h;
                        k = Mth.sin(g) * h;
                        if (particleEffect.getType() == ParticleTypes.ENTITY_EFFECT) {
                            int l = this.random.nextBoolean() ? 16777215 : this.getColor();
                            m = l >> 16 & 255;
                            n = l >> 8 & 255;
                            o = l & 255;
                            this.level.addAlwaysVisibleParticle(particleEffect, this.getX() + (double)j, this.getY(), this.getZ() + (double)k, (double)((float)m / 255.0F), (double)((float)n / 255.0F), (double)((float)o / 255.0F));
                        } else {
                            this.level.addAlwaysVisibleParticle(particleEffect, this.getX() + (double)j, this.getY(), this.getZ() + (double)k, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            } else {
                float p = 3.1415927F * f * f;

                for(int q = 0; (float)q < p; ++q) {
                    h = this.random.nextFloat() * 6.2831855F;
                    j = Mth.sqrt(this.random.nextFloat()) * f;
                    k = Mth.cos(h) * j;
                    float u = Mth.sin(h) * j;
                    if (particleEffect.getType() == ParticleTypes.ENTITY_EFFECT) {
                        m = this.getColor();
                        n = m >> 16 & 255;
                        o = m >> 8 & 255;
                        int y = m & 255;
                        this.level.addAlwaysVisibleParticle(particleEffect, this.getX() + (double)k, this.getY(), this.getZ() + (double)u, (double)((float)n / 255.0F), (double)((float)o / 255.0F), (double)((float)y / 255.0F));
                    } else {
                        this.level.addAlwaysVisibleParticle(particleEffect, this.getX() + (double)k, this.getY(), this.getZ() + (double)u, (0.5D - this.random.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.random.nextDouble()) * 0.15D);
                    }
                }
            }
        } else {
            if (this.tickCount >= this.getWaitTime() + this.getDuration()) {
                this.discard();
                return;
            }

            boolean bl2 = this.tickCount < this.getWaitTime();
            if (bl != bl2) {
                this.setWaiting(bl2);
            }

            if (bl2) {
                return;
            }

            if (this.getRadiusPerTick() != 0.0F) {
                f += this.getRadiusPerTick();
                if (f < 0.5F) {
                    this.discard();
                    return;
                }

                this.setRadius(f);
            }

            if (this.tickCount % 5 == 0) {
                Iterator iterator = ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<Entity, Integer> entry = (Map.Entry)iterator.next();
                    if (this.tickCount >= (Integer)entry.getValue()) {
                        iterator.remove();
                    }
                }

                List<LivingEntity> list2 = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
                if (!list2.isEmpty()) {
                    Iterator var25 = list2.iterator();
                    while(true) {
                        LivingEntity livingEntity;
                        double z;
                        do {
                            do {
                                if (!var25.hasNext()) {
                                    return;
                                }

                                livingEntity = (LivingEntity)var25.next();
                            } while(((AreaEffectCloudEntityAccessor)this).getAffectedEntities().containsKey(livingEntity));

                            double d = livingEntity.getX() - this.getX();
                            double e = livingEntity.getZ() - this.getZ();
                            z = d * d + e * e;
                        } while(!(z <= (double)(f * f)));

                        ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().put(livingEntity, this.tickCount + ((AreaEffectCloudEntityAccessor)this).getReapplicationDelay());

                        if (getOwner() == null) {
                            livingEntity.hurt(this.level.damageSources().magic(), damage);
                        } else {
                            if (livingEntity != getOwner()) {
                                livingEntity.hurt(this.level.damageSources().indirectMagic(this, getOwner()), damage);
                            }
                        }

                        if (this.getRadiusOnUse() != 0.0F) {
                            f += this.getRadiusOnUse();
                            if (f < 0.5F) {
                                this.discard();
                                return;
                            }

                            this.setRadius(f);
                        }

                        if (this.getDurationOnUse() != 0) {
                            this.setDuration(this.getDuration() + this.getDurationOnUse());
                            if (this.getDuration() <= 0) {
                                this.discard();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.damage = nbt.getFloat("Damage");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putFloat("Damage", this.damage);
    }

}
