package net.merchantpug.toomanyorigins.platform;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.AttributeUtil;
import net.merchantpug.apugli.Apugli;
import net.merchantpug.apugli.condition.factory.IConditionFactory;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.platform.services.IPlatformHelper;
import com.google.auto.service.AutoService;
import io.github.apace100.calio.data.SerializableDataType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

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
    public double applyModifiers(List<AttributeModifier> modifiers, double value) {
        return AttributeUtil.applyModifiers(modifiers, value);
    }

    @Override
    public void registerDamage(String name, IConditionFactory<Tuple<DamageSource, Float>> condition) {
        ResourceLocation id = TooManyOrigins.asResource(name);
        Registry.register(ApoliRegistries.DAMAGE_CONDITION, id, new ConditionFactory<>(id, condition.getSerializableData(), condition::check));
    }

}