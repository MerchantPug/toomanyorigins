package net.merchantpug.toomanyorigins.data;

import io.github.apace100.calio.data.SerializableDataType;
import io.github.edwinmindcraft.apoli.api.power.configuration.*;
import io.github.edwinmindcraft.apoli.api.registry.ApoliBuiltinRegistries;
import io.github.edwinmindcraft.apoli.api.registry.ApoliDynamicRegistries;
import io.github.edwinmindcraft.calio.api.CalioAPI;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.util.Lazy;

public class ApoliForgeDataTypes {

    public static final Lazy<SerializableDataType<ConfiguredPower<?, ?>>> POWER_TYPE = Lazy.of(() -> new SerializableDataType<>(castClass(ConfiguredPower.class), ConfiguredPower.HOLDER.xmap(Holder::value, power -> getHolder(ApoliDynamicRegistries.CONFIGURED_POWER_KEY, power))));

    @SuppressWarnings("unchecked")
    private static <T> Class<T> castClass(Class<?> cls) {
        return (Class<T>)cls;
    }

    private static <T> Holder<T> getHolder(ResourceKey<Registry<T>> registryKey, T original) {
        Registry<T> registry = CalioAPI.getDynamicRegistries().get(registryKey);
        ResourceKey<T> resourceKey = registry.getResourceKey(original).orElseThrow();
        return registry.getHolderOrThrow(resourceKey);
    }
    
}