package com.github.merchantpug.toomanyorigins.forge;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.power.SimpleValueModifierPower;
import com.github.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TooManyOrigins.MODID)
public class TooManyOriginsForgeEventHandler {
    @SubscribeEvent
    public static void modifyDamageDealt(LivingHurtEvent event) {
        LivingEntity living = event.getEntityLiving();
        DamageSource source = event.getSource();
        if (TMOPowers.LOOSE_SCALES.isActive(living)) {
            Vec3d vector3d2 = source.getPosition();
            if (vector3d2 != null) {
                Vec3d vector3d = living.getRotationVec(1.0F);
                Vec3d vector3d1 = vector3d2.reverseSubtract(living.getPos()).normalize();
                vector3d1 = new Vec3d(vector3d1.x, 0.0D, vector3d1.z);
                if (vector3d1.dotProduct(vector3d) > 0.0D) {
                    event.setAmount((float) SimpleValueModifierPower.modify(living, TMOPowers.LOOSE_SCALES, event.getAmount()));
                }
            }
        }
    }
}
