package com.github.merchantpug.toomanyorigins.content.legacy.entity;

import com.github.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import com.github.merchantpug.toomanyorigins.mixin.AreaEffectCloudEntityAccessor;
import com.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import com.github.merchantpug.toomanyorigins.registry.TMOEntities;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LegacyFireballAreaEffectCloudEntity extends AreaEffectCloudEntity {
    private float damage;

    public LegacyFireballAreaEffectCloudEntity(EntityType<? extends LegacyFireballAreaEffectCloudEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        this.damage = 6.0F;
        this.setRadius(3.0F);
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.DRAGON_FIREBALL))
            this.discard();
    }

    public LegacyFireballAreaEffectCloudEntity(World world, double x, double y, double z) {
        this(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD, world);
        this.updatePosition(x, y, z);
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.DRAGON_FIREBALL))
            this.discard();
    }

    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        if (LegacyContentRegistry.isContentDisabled(LegacyContentRegistry.DRAGON_FIREBALL) && owner != null) {
            owner.sendMessage(Text.translatable("toomanyorigins.content.disabled_message", LegacyContentRegistry.DRAGON_FIREBALL).formatted(Formatting.RED));
        }
        super.setOwner(owner);
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
        if (this.world.isClient) {
            ParticleEffect particleEffect = this.getParticleType();
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
                        h = MathHelper.sqrt(this.random.nextFloat()) * 0.2F;
                        j = MathHelper.cos(g) * h;
                        k = MathHelper.sin(g) * h;
                        if (particleEffect.getType() == ParticleTypes.ENTITY_EFFECT) {
                            int l = this.random.nextBoolean() ? 16777215 : this.getColor();
                            m = l >> 16 & 255;
                            n = l >> 8 & 255;
                            o = l & 255;
                            this.world.addImportantParticle(particleEffect, this.getX() + (double)j, this.getY(), this.getZ() + (double)k, (double)((float)m / 255.0F), (double)((float)n / 255.0F), (double)((float)o / 255.0F));
                        } else {
                            this.world.addImportantParticle(particleEffect, this.getX() + (double)j, this.getY(), this.getZ() + (double)k, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            } else {
                float p = 3.1415927F * f * f;

                for(int q = 0; (float)q < p; ++q) {
                    h = this.random.nextFloat() * 6.2831855F;
                    j = MathHelper.sqrt(this.random.nextFloat()) * f;
                    k = MathHelper.cos(h) * j;
                    float u = MathHelper.sin(h) * j;
                    if (particleEffect.getType() == ParticleTypes.ENTITY_EFFECT) {
                        m = this.getColor();
                        n = m >> 16 & 255;
                        o = m >> 8 & 255;
                        int y = m & 255;
                        this.world.addImportantParticle(particleEffect, this.getX() + (double)k, this.getY(), this.getZ() + (double)u, (double)((float)n / 255.0F), (double)((float)o / 255.0F), (double)((float)y / 255.0F));
                    } else {
                        this.world.addImportantParticle(particleEffect, this.getX() + (double)k, this.getY(), this.getZ() + (double)u, (0.5D - this.random.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.random.nextDouble()) * 0.15D);
                    }
                }
            }
        } else {
            if (this.age >= this.getWaitTime() + this.getDuration()) {
                this.discard();
                return;
            }

            boolean bl2 = this.age < this.getWaitTime();
            if (bl != bl2) {
                this.setWaiting(bl2);
            }

            if (bl2) {
                return;
            }

            if (this.getRadiusGrowth() != 0.0F) {
                f += this.getRadiusGrowth();
                if (f < 0.5F) {
                    this.discard();
                    return;
                }

                this.setRadius(f);
            }

            if (this.age % 5 == 0) {
                Iterator iterator = ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry)iterator.next();
                    if (this.age >= (Integer)entry.getValue()) {
                        iterator.remove();
                    }
                }

                List<LivingEntity> list2 = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
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

                        ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().put(livingEntity, this.age + ((AreaEffectCloudEntityAccessor)this).getReapplicationDelay());

                        if (getOwner() == null) {
                            livingEntity.damage(TMODamageSources.DRAGON_MAGIC, damage);
                        } else {
                            if (livingEntity != getOwner()) {
                                livingEntity.damage(TMODamageSources.dragonMagic(this, getOwner()), damage);
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


    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.damage = nbt.getFloat("Damage");
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Damage", this.damage);
    }

    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.changing(this.getRadius() * 2.0F, 0.5F);
    }
}
