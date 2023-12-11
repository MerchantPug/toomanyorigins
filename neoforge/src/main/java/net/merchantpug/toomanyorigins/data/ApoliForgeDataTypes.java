package net.merchantpug.toomanyorigins.data;

import io.github.apace100.calio.data.SerializableDataType;
import io.github.edwinmindcraft.apoli.api.power.configuration.*;
import net.minecraft.core.Holder;

public class ApoliForgeDataTypes {

    public static final SerializableDataType<Holder<ConfiguredPower<?, ?>>> POWER_TYPE = new SerializableDataType<>(castClass(Holder.class), ConfiguredPower.CODEC_SET.holderRef());

    public static final SerializableDataType<ConfiguredBiEntityCondition<?, ?>> BIENTITY_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredBiEntityCondition.class), ConfiguredBiEntityCondition.CODEC);
    public static final SerializableDataType<ConfiguredBiomeCondition<?, ?>> BIOME_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredBiomeCondition.class), ConfiguredBiomeCondition.CODEC);
    public static final SerializableDataType<ConfiguredBlockCondition<?, ?>> BLOCK_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredBlockCondition.class), ConfiguredBlockCondition.CODEC);
    public static final SerializableDataType<ConfiguredDamageCondition<?, ?>> DAMAGE_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredDamageCondition.class), ConfiguredDamageCondition.CODEC);
    public static final SerializableDataType<ConfiguredEntityCondition<?, ?>> ENTITY_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredEntityCondition.class), ConfiguredEntityCondition.CODEC);
    public static final SerializableDataType<ConfiguredFluidCondition<?, ?>> FLUID_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredFluidCondition.class), ConfiguredFluidCondition.CODEC);
    public static final SerializableDataType<ConfiguredItemCondition<?, ?>> ITEM_CONDITION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredItemCondition.class), ConfiguredItemCondition.CODEC);

    public static final SerializableDataType<ConfiguredBiEntityAction<?, ?>> BIENTITY_ACTION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredBiEntityAction.class), ConfiguredBiEntityAction.CODEC);
    public static final SerializableDataType<ConfiguredBlockAction<?, ?>> BLOCK_ACTION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredBlockAction.class), ConfiguredBlockAction.CODEC);
    public static final SerializableDataType<ConfiguredEntityAction<?, ?>> ENTITY_ACTION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredEntityAction.class), ConfiguredEntityAction.CODEC);
    public static final SerializableDataType<ConfiguredItemAction<?, ?>> ITEM_ACTION = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredItemAction.class), ConfiguredItemAction.CODEC);

    public static final SerializableDataType<ConfiguredModifier<?>> MODIFIER = new SerializableDataType<>(ApoliForgeDataTypes.castClass(ConfiguredModifier.class), ConfiguredModifier.CODEC);

    @SuppressWarnings("unchecked")
    private static <T> Class<T> castClass(Class<?> cls) {
        return (Class<T>)cls;
    }
    
}