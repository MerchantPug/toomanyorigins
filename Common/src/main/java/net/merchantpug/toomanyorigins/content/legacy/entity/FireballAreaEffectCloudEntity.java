package net.merchantpug.toomanyorigins.content.legacy.entity;

import net.merchantpug.toomanyorigins.data.LegacyContentRegistry;
import net.merchantpug.toomanyorigins.mixin.AreaEffectCloudEntityAccessor;
import net.merchantpug.toomanyorigins.registry.TMODamageTypes;
import net.merchantpug.toomanyorigins.registry.TMOEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void setOwner(@Nullable LivingEntity owner) {
        super.setOwner(owner);
        if (LegacyContentRegistry.isDragonFireballEnabled() && owner != null) {
            owner.sendSystemMessage(Component.translatable("toomanyorigins.content.disabled_message", LegacyContentRegistry.DRAGON_FIREBALL).withStyle(ChatFormatting.RED));
            this.discard();
        }
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void tick() {
        super.baseTick();
        boolean bl = this.isWaiting();
        float f = this.getRadius();
        if (this.level.isClientSide) {
            ParticleOptions particleOptions = this.getParticle();
            int i;
            float g;
            if (bl) {
                i = 2;
                g = 0.2F;
            } else {
                i = Mth.ceil((float) Math.PI * f * f);
                g = f;
            }

            for (int j = 0; j < i; ++j) {
                float h = this.random.nextFloat() * (float) (Math.PI * 2);
                float k = Mth.sqrt(this.random.nextFloat()) * g;
                double d = this.getX() + (double) (Mth.cos(h) * k);
                double e = this.getY();
                double l = this.getZ() + (double) (Mth.sin(h) * k);
                double n;
                double o;
                double p;
                if (particleOptions.getType() == ParticleTypes.ENTITY_EFFECT) {
                    int m = bl && this.random.nextBoolean() ? 16777215 : this.getColor();
                    n = (double) ((float) (m >> 16 & 0xFF) / 255.0F);
                    o = (double) ((float) (m >> 8 & 0xFF) / 255.0F);
                    p = (double) ((float) (m & 0xFF) / 255.0F);
                } else if (bl) {
                    n = 0.0;
                    o = 0.0;
                    p = 0.0;
                } else {
                    n = (0.5 - this.random.nextDouble()) * 0.15;
                    o = 0.01F;
                    p = (0.5 - this.random.nextDouble()) * 0.15;
                }

                this.level.addAlwaysVisibleParticle(particleOptions, d, e, l, n, o, p);
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
                ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().entrySet().removeIf(entry -> this.tickCount >= entry.getValue());
                List<LivingEntity> list2 = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), living -> living != this.getOwner());
                if (!list2.isEmpty()) {
                    for (LivingEntity livingEntity : list2) {
                        if (!((AreaEffectCloudEntityAccessor)this).getAffectedEntities().containsKey(livingEntity) && livingEntity.isAffectedByPotions()) {
                            double q = livingEntity.getX() - this.getX();
                            double r = livingEntity.getZ() - this.getZ();
                            double s = q * q + r * r;
                            if (s <= (double) (f * f)) {
                                ((AreaEffectCloudEntityAccessor)this).getAffectedEntities().put(livingEntity, this.tickCount + ((AreaEffectCloudEntityAccessor)this).getReapplicationDelay());

                                livingEntity.hurt(TMODamageTypes.indirectDragonMagic(this, this.getOwner()), damage);

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
