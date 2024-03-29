package net.merchantpug.toomanyorigins.platform;

import io.github.apace100.origins.badge.BadgeFactory;
import io.github.edwinmindcraft.origins.common.registry.OriginRegisters;
import net.merchantpug.toomanyorigins.TooManyOrigins;
import net.merchantpug.toomanyorigins.badge.factory.IBadgeFactory;
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
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@AutoService(IPowerHelper.class)
public class ForgePowerHelper implements IPowerHelper<Holder<ConfiguredPower<?, ?>>> {

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
                .map(holder -> ((FabricPowerFactoryAccessor<P>) holder.get().getFactory()).invokeGetPower(holder.get(), entity))
                .collect(Collectors.toList());
    }

    @Override
    public <P> List<P> getPowers(LivingEntity entity, SpecialPowerFactory<P> factory) {
        return IPowerContainer.getPowers(entity, (io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory<?>) factory)
                .stream().map(holder -> (P) holder.get()).collect(Collectors.toList());
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
    public SerializableDataType<Holder<ConfiguredPower<?, ?>>> getPowerTypeDataType() {
        return ApoliForgeDataTypes.POWER_TYPE;
    }

    @Override
    public OptionalInt getResource(LivingEntity entity, Holder<ConfiguredPower<?, ?>> holder) {
        var powerId = holder.unwrapKey();
        if (holder.isBound()) {
            ConfiguredPower<?, ?> power = holder.get();
            if (IPowerContainer.get(entity).resolve().flatMap(container -> {
                if (container == null) return Optional.empty();
                return powerId.map(container::hasPower);
            }).orElse(false)) {
                return power.getValue(entity);
            }
        }
        TooManyOrigins.LOG.warn("Failed to get resource for power [{}], because it doesn't hold any resource!", powerId.orElse(null));
        return OptionalInt.empty();
    }

    @Override
    public OptionalInt setResource(LivingEntity entity, Holder<ConfiguredPower<?, ?>> holder, int value) {
        var powerId = holder.unwrapKey();
        if (holder.isBound()) {
            ConfiguredPower<?, ?> power = holder.get();
            if (IPowerContainer.get(entity).resolve().flatMap(container -> {
                if (container == null) return Optional.empty();
                return powerId.map(container::hasPower);
            }).orElse(false)) {
                OptionalInt result = power.assign(entity, value);
                if (result.isPresent()) {
                    ApoliAPI.synchronizePowerContainer(entity);
                    return result;
                }
            }
        }
        TooManyOrigins.LOG.warn("Failed to set resource for power [{}], because it doesn't hold any resource!", powerId.orElse(null));
        return OptionalInt.empty();
    }

    @Override
    public <F extends IBadgeFactory> Supplier<BadgeFactory> registerBadge(String name, Class<F> badgeClass) {
        F factory = Services.load(badgeClass);
        return TMORegisters.BADGE_FACTORIES.register(name, () -> new BadgeFactory(TooManyOrigins.asResource(name), factory.getSerializableData(), factory.getFactoryCreator()));
    }

}