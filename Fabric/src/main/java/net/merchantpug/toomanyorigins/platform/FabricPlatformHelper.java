package net.merchantpug.toomanyorigins.platform;

import net.merchantpug.toomanyorigins.platform.services.IPlatformHelper;
import com.google.auto.service.AutoService;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.apoli.util.modifier.ModifierUtil;
import io.github.apace100.calio.data.SerializableDataType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@SuppressWarnings("unchecked")
@AutoService(IPlatformHelper.class)
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public SerializableDataType<?> getModifierDataType() {
        return Modifier.DATA_TYPE;
    }

    @Override
    public SerializableDataType<?> getModifiersDataType() {
        return Modifier.LIST_TYPE;
    }

    @Override
    public double applyModifiers(LivingEntity entity, List<?> modifiers, double value) {
        return ModifierUtil.applyModifiers(entity, (List<Modifier>) modifiers, value);
    }

}