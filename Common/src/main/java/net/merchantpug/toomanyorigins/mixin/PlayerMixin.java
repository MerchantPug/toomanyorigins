package net.merchantpug.toomanyorigins.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.merchantpug.toomanyorigins.platform.Services;
import net.merchantpug.toomanyorigins.registry.TMOPowers;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {

    @WrapWithCondition(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setSprinting(Z)V"))
    private boolean cancelOutSprintDisableOnHit(Player player, boolean value) {
        return !Services.POWER.hasPower(player, TMOPowers.CANCEL_OUT_SPRINT_DISABLE_ON_HIT.get());
    }

}
