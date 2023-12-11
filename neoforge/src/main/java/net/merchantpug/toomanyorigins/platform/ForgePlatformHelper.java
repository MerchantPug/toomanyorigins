package net.merchantpug.toomanyorigins.platform;

import net.merchantpug.toomanyorigins.platform.services.IPlatformHelper;
import com.google.auto.service.AutoService;
import io.github.apace100.apoli.util.modifier.ModifierUtil;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredModifier;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.extensions.IForgePlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("unchecked")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@AutoService(IPlatformHelper.class)
public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public SerializableDataType<?> getModifierDataType() {
        return SerializableDataTypes.ATTRIBUTE_MODIFIER;
    }

    @Override
    public SerializableDataType<?> getModifiersDataType() {
        return SerializableDataTypes.ATTRIBUTE_MODIFIERS;
    }

    @Override
    public double applyModifiers(LivingEntity living, List<?> modifiers, double value) {
        return ModifierUtil.applyModifiers(living, (List<ConfiguredModifier<?>>) modifiers, value);
    }

}