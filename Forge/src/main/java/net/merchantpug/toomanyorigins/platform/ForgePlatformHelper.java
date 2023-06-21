package net.merchantpug.toomanyorigins.platform;

import io.github.apace100.apoli.util.AttributeUtil;
import net.merchantpug.apugli.condition.FabricDamageCondition;
import net.merchantpug.apugli.condition.factory.IConditionFactory;
import net.merchantpug.apugli.registry.ApugliRegisters;
import net.merchantpug.toomanyorigins.platform.services.IPlatformHelper;
import com.google.auto.service.AutoService;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.registry.TMORegisters;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
    public double applyModifiers(List<AttributeModifier> modifiers, double value) {
        return AttributeUtil.applyModifiers(modifiers, value);
    }

    public void registerDamage(String name, IConditionFactory<Tuple<DamageSource, Float>> condition) {
        TMORegisters.DAMAGE_CONDITIONS.register(name, () -> new FabricDamageCondition(condition.getSerializableData(), condition::check));
    }

}