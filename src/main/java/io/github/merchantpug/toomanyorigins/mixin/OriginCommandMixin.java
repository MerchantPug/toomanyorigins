package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.command.OriginCommand;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.power.Power;
import io.github.merchantpug.toomanyorigins.power.VisualTimerPower;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OriginCommand.class)
public class OriginCommandMixin {
    @Inject(method = "setOrigin", at = @At("TAIL"))
    private static void setOrigin(PlayerEntity player, OriginLayer layer, Origin origin, CallbackInfo ci) {
        OriginComponent.getPowers(player, VisualTimerPower.class).forEach(Power::onRespawn);
    }
}
