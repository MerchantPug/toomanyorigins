package net.merchantpug.toomanyorigins.platform;

import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.data.ApoliForgeDataTypes;
import net.merchantpug.toomanyorigins.mixin.forge.FabricPowerFactoryAccessor;
import net.merchantpug.toomanyorigins.platform.services.IPowerHelper;
import net.merchantpug.toomanyorigins.power.factory.SimplePowerFactory;
import net.merchantpug.toomanyorigins.power.factory.SpecialPowerFactory;
import net.merchantpug.toomanyorigins.registry.TMORegisters;
import net.merchantpug.toomanyorigins.registry.services.RegistryObject;
import com.google.auto.service.AutoService;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.edwinmindcraft.apoli.api.ApoliAPI;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.fabric.FabricPowerFactory;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@AutoService(IPowerHelper.class)
public class ForgePowerHelper implements IPowerHelper<ConfiguredPower<?, ?>> {

    @Override
    public io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory<?> unwrapSimpleFactory(PowerFactory<?> factory) {
        return factory.getWrapped();
    }

    @Override
    public <F extends SimplePowerFactory<?>> Supplier<F> registerFactory(F factory) {
        TMORegisters.POWER_FACTORIES.register(factory.getSerializerId().getPath(), factory::getWrapped);
        return () -> factory;
    }

    @Override
    public <F extends SpecialPowerFactory<?>> RegistryObject<F> registerFactory(String name, Class<F> factoryClass) {
        return (RegistryObject<F>) (Object) TMORegisters.POWER_FACTORIES.register(name, () -> (io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory<?>) Services.load(factoryClass));
    }

    @Override
    public <P extends Power> List<P> getPowers(LivingEntity entity, SimplePowerFactory<P> factory) {
        return IPowerContainer.getPowers(entity, (FabricPowerFactory<P>) factory.getWrapped()).stream()
                .map(power -> ((FabricPowerFactoryAccessor<P>) power.getFactory()).invokeGetPower(power, entity))
                .collect(Collectors.toList());
    }

    @Override
    public <P> List<P> getPowers(LivingEntity entity, SpecialPowerFactory<P> factory) {
        return IPowerContainer.getPowers(entity, (io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory<?>) factory)
                .stream().map(power -> (P) power).collect(Collectors.toList());
    }

    @Override
    public <P extends Power> boolean hasPower(LivingEntity entity, SimplePowerFactory<P> factory) {
        return IPowerContainer.hasPower(entity, factory.getWrapped());
    }

    @Override
    public <P> boolean hasPower(LivingEntity entity, SpecialPowerFactory<P> factory) {
        return IPowerContainer.hasPower(entity, (io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory<?>) factory);
    }

    @Override
    public SerializableDataType<ConfiguredPower<?, ?>> getPowerTypeDataType() {
        return ApoliForgeDataTypes.POWER_TYPE.get();
    }

    @Override
    public OptionalInt getResource(LivingEntity entity, ConfiguredPower<?, ?> power) {
        var powerId = power.getRegistryName();
        if (IPowerContainer.get(entity).resolve().map(container -> container.hasPower(powerId)).orElse(false)) {
            return power.getValue(entity);
        }
        TooManyOrigins.LOG.warn("Failed to get resource for power [{}], because it doesn't hold any resource!", powerId);
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt setResource(LivingEntity entity, ConfiguredPower<?, ?> power, int value) {
        var powerId = power.getRegistryName();
        if (IPowerContainer.get(entity).resolve().map(container -> container.hasPower(powerId)).orElse(false)) {
            OptionalInt result = power.assign(entity, value);
            if (result.isPresent()) {
                ApoliAPI.synchronizePowerContainer(entity);
                return result;
            }
        }
        TooManyOrigins.LOG.warn("Failed to set resource for power [{}], because it doesn't hold any resource!", powerId);
        return OptionalInt.empty();
    }

}