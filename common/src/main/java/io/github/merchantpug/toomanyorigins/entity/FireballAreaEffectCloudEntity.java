package io.github.merchantpug.toomanyorigins.entity;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.merchantpug.toomanyorigins.registry.TMODamageSources;
import io.github.merchantpug.toomanyorigins.registry.TMOEntities;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.ParticleArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FireballAreaEffectCloudEntity extends Entity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TrackedData<Float> RADIUS;
    private static final TrackedData<Integer> COLOR;
    private static final TrackedData<Boolean> WAITING;
    private static final TrackedData<ParticleEffect> PARTICLE_ID;
    private final Map<Entity, Integer> affectedEntities;
    private int duration;
    private int waitTime;
    private int reapplicationDelay;
    private boolean customColor;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusGrowth;
    private LivingEntity owner;
    private UUID ownerUuid;
    private float damage;

    public FireballAreaEffectCloudEntity(EntityType<? extends FireballAreaEffectCloudEntity> entityType, World world) {
        super(entityType, world);
        this.affectedEntities = Maps.newHashMap();
        this.duration = 600;
        this.waitTime = 20;
        this.reapplicationDelay = 20;
        this.noClip = true;
        this.damage = 6.0F;
        this.setRadius(3.0F);
    }

    public FireballAreaEffectCloudEntity(World world, double x, double y, double z) {
        this(TMOEntities.FIREBALL_AREA_EFFECT_CLOUD, world);
        this.updatePosition(x, y, z);
    }

    protected void initDataTracker() {
        this.getDataTracker().startTracking(COLOR, 0);
        this.getDataTracker().startTracking(RADIUS, 0.5F);
        this.getDataTracker().startTracking(WAITING, false);
        this.getDataTracker().startTracking(PARTICLE_ID, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float radius) {
        if (!this.world.isClient) {
            this.getDataTracker().set(RADIUS, radius);
        }

    }

    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    public float getRadius() {
        return (Float)this.getDataTracker().get(RADIUS);
    }

    public int getColor() {
        return (Integer)this.getDataTracker().get(COLOR);
    }

    public void setColor(int rgb) {
        this.customColor = true;
        this.getDataTracker().set(COLOR, rgb);
    }

    public ParticleEffect getParticleType() {
        return this.getDataTracker().get(PARTICLE_ID);
    }

    public void setParticleType(ParticleEffect particle) {
        this.getDataTracker().set(PARTICLE_ID, particle);
    }

    protected void setWaiting(boolean waiting) {
        this.getDataTracker().set(WAITING, waiting);
    }

    public boolean isWaiting() {
        return (Boolean)this.getDataTracker().get(WAITING);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
            if (this.age >= this.waitTime + this.duration) {
                this.remove();
                return;
            }

            boolean bl2 = this.age < this.waitTime;
            if (bl != bl2) {
                this.setWaiting(bl2);
            }

            if (bl2) {
                return;
            }

            if (this.radiusGrowth != 0.0F) {
                f += this.radiusGrowth;
                if (f < 0.5F) {
                    this.remove();
                    return;
                }

                this.setRadius(f);
            }

            if (this.age % 5 == 0) {
                Iterator iterator = this.affectedEntities.entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<Entity, Integer> entry = (Map.Entry)iterator.next();
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
                            } while(this.affectedEntities.containsKey(livingEntity));

                            double d = livingEntity.getX() - this.getX();
                            double e = livingEntity.getZ() - this.getZ();
                            z = d * d + e * e;
                        } while(!(z <= (double)(f * f)));

                        this.affectedEntities.put(livingEntity, this.age + this.reapplicationDelay);

                        if (owner == null) {
                            livingEntity.damage(TMODamageSources.DRAGON_MAGIC, damage);
                        } else {
                            if (livingEntity != owner) {
                                livingEntity.damage(TMODamageSources.dragonMagic(this, getOwner()), damage);
                            }
                        }

                        if (this.radiusOnUse != 0.0F) {
                            f += this.radiusOnUse;
                            if (f < 0.5F) {
                                this.remove();
                                return;
                            }

                            this.setRadius(f);
                        }

                        if (this.durationOnUse != 0) {
                            this.duration += this.durationOnUse;
                            if (this.duration <= 0) {
                                this.remove();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setRadiusGrowth(float growth) {
        this.radiusGrowth = growth;
    }

    public void setWaitTime(int ticks) {
        this.waitTime = ticks;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    protected void readCustomDataFromTag(CompoundTag tag) {
        this.age = tag.getInt("Age");
        this.duration = tag.getInt("Duration");
        this.waitTime = tag.getInt("WaitTime");
        this.reapplicationDelay = tag.getInt("ReapplicationDelay");
        this.durationOnUse = tag.getInt("DurationOnUse");
        this.radiusOnUse = tag.getFloat("RadiusOnUse");
        this.radiusGrowth = tag.getFloat("RadiusPerTick");
        this.damage = tag.getFloat("Damage");
        this.setRadius(tag.getFloat("Radius"));
        if (tag.containsUuid("Owner")) {
            this.ownerUuid = tag.getUuid("Owner");
        }

        if (tag.contains("Particle", 8)) {
            try {
                this.setParticleType(ParticleArgumentType.readParameters(new StringReader(tag.getString("Particle"))));
            } catch (CommandSyntaxException var5) {
                LOGGER.warn("Couldn't load custom particle {}", tag.getString("Particle"), var5);
            }
        }

        if (tag.contains("Color", 99)) {
            this.setColor(tag.getInt("Color"));
        }
    }

    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putInt("Age", this.age);
        tag.putInt("Duration", this.duration);
        tag.putInt("WaitTime", this.waitTime);
        tag.putInt("ReapplicationDelay", this.reapplicationDelay);
        tag.putInt("DurationOnUse", this.durationOnUse);
        tag.putFloat("RadiusOnUse", this.radiusOnUse);
        tag.putFloat("RadiusPerTick", this.radiusGrowth);
        tag.putFloat("Damage", this.damage);
        tag.putFloat("Radius", this.getRadius());
        tag.putString("Particle", this.getParticleType().asString());
        if (this.ownerUuid != null) {
            tag.putUuid("Owner", this.ownerUuid);
        }

        if (this.customColor) {
            tag.putInt("Color", this.getColor());
        }
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (RADIUS.equals(data)) {
            this.calculateDimensions();
        }

        super.onTrackedDataSet(data);
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

    static {
        RADIUS = DataTracker.registerData(FireballAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.FLOAT);
        COLOR = DataTracker.registerData(FireballAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.INTEGER);
        WAITING = DataTracker.registerData(FireballAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        PARTICLE_ID = DataTracker.registerData(FireballAreaEffectCloudEntity.class, TrackedDataHandlerRegistry.PARTICLE);
    }
}
