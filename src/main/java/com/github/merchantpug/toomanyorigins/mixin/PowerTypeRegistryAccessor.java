package com.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = PowerTypeRegistry.class, remap = false)
public interface PowerTypeRegistryAccessor {
    @Invoker("update")
    static PowerType invokeUpdate(Identifier id, PowerType powerType) {
        throw new RuntimeException("");
    }
}
