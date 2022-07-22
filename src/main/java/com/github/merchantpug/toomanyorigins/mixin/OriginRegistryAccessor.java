package com.github.merchantpug.toomanyorigins.mixin;

import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.HashMap;

@Mixin(OriginRegistry.class)
public interface OriginRegistryAccessor {
    @Invoker("update")
    static Origin invokeUpdate(Identifier id, Origin origin) {
        throw new RuntimeException("");
    }
}
