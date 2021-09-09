package io.github.merchantpug.toomanyorigins.mixin.forge;

import io.github.apace100.origins.power.AttributePower;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.util.AttributedEntityAttributeModifier;
import io.github.merchantpug.toomanyorigins.TooManyOrigins;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = AttributePower.class, remap = false)
public class AttributePowerMixin extends Power {
    @Shadow @Final private List<AttributedEntityAttributeModifier> modifiers;

    public AttributePowerMixin(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    @Override
    public void onRespawn() {
        player.setHealth(player.getMaxHealth());
    }

    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        if (!player.world.isClient) {
            modifiers.forEach(modifier -> {
                if (modifier.getAttribute().equals(EntityAttributes.GENERIC_MAX_HEALTH)) {
                    player.setHealth(player.getHealth() + (float)modifier.getModifier().getValue());
                }
            });
        }
    }
}
