package io.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.power.CooldownPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CooldownPower.class)
public interface CooldownPowerAccess {
    @Accessor
    long getLastUseTime();
}
