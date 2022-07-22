package com.github.merchantpug.toomanyorigins.util;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.mixin.PowerTypeRegistryAccessor;
import com.github.merchantpug.toomanyorigins.mixin.PowerTypesAccessor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.integration.PostPowerLoadCallback;
import io.github.apace100.apoli.integration.PrePowerLoadCallback;
import io.github.apace100.apoli.power.MultiplePowerType;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeRegistry;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.NamespaceAlias;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class PowerTypeReadingUtil {
    public static PowerType getPowersFromJson(Identifier id, JsonElement je) {
        SerializableData.CURRENT_NAMESPACE = id.getNamespace();
        SerializableData.CURRENT_PATH = id.getPath();
        JsonObject jo = je.getAsJsonObject();

        PrePowerLoadCallback.EVENT.invoker().onPrePowerLoad(id, jo);

        Identifier factoryId = Identifier.tryParse(JsonHelper.getString(jo, "type"));
        if (isMultiple(factoryId)) {
            List<Identifier> subPowers = new LinkedList<>();
            for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
                if (entry.getKey().equals("type")
                        || entry.getKey().equals("loading_priority")
                        || entry.getKey().equals("name")
                        || entry.getKey().equals("description")
                        || entry.getKey().equals("hidden")
                        || entry.getKey().equals("condition")
                        || PowerTypesAccessor.getAdditionalData().containsKey(entry.getKey())) {
                    continue;
                }
                Identifier subId = new Identifier(id.toString() + "_" + entry.getKey());
                try {
                    readPower(subId, entry.getValue(), true, PowerType::new);
                    subPowers.add(subId);
                } catch (Exception e) {
                    TooManyOrigins.LOGGER.error("There was a problem reading sub-power \"" +
                            subId.toString() + "\" in power file \"" + id.toString() + "\": " + e.getMessage());
                }
            }
            MultiplePowerType superPower = (MultiplePowerType)readPower(id, je, false, MultiplePowerType::new);
            superPower.setSubPowers(subPowers);
            handleAdditionalData(id, factoryId, false, jo, superPower);
            PostPowerLoadCallback.EVENT.invoker().onPostPowerLoad(id, factoryId, false, jo, superPower);
            return superPower;
        } else {
            return readPower(id, je, false, PowerType::new);
        }
    }

    public static PowerType readPower(Identifier id, JsonElement je, boolean isSubPower,
                                      BiFunction<Identifier, PowerFactory.Instance, PowerType> powerTypeFactory) {
        JsonObject jo = je.getAsJsonObject();

        Identifier factoryId = Identifier.tryParse(JsonHelper.getString(jo, "type"));
        if(isMultiple(factoryId)) {
            factoryId = Apoli.identifier("simple");
            if(isSubPower) {
                throw new JsonSyntaxException("Power type \"apoli:multiple\" may not be used for a sub-power of "
                        + "another \"apoli:multiple\" power.");
            }
        }

        Optional<PowerFactory> optionalFactory = ApoliRegistries.POWER_FACTORY.getOrEmpty(factoryId);
        if(optionalFactory.isEmpty()) {
            if(NamespaceAlias.hasAlias(factoryId)) {
                optionalFactory = ApoliRegistries.POWER_FACTORY.getOrEmpty(NamespaceAlias.resolveAlias(factoryId));
            }
            if(optionalFactory.isEmpty()) {
                throw new JsonSyntaxException("Power type \"" + factoryId + "\" is not defined.");
            }
        }
        PowerFactory.Instance factoryInstance = optionalFactory.get().read(jo);
        PowerType type = powerTypeFactory.apply(id, factoryInstance);
        int priority = JsonHelper.getInt(jo, "loading_priority", 0);
        String name = JsonHelper.getString(jo, "name", "");
        String description = JsonHelper.getString(jo, "description", "");
        boolean hidden = JsonHelper.getBoolean(jo, "hidden", false);
        if(hidden || isSubPower) {
            type.setHidden();
        }
        type.setTranslationKeys(name, description);
        if(!PowerTypeRegistry.contains(id)) {
            PowerTypeRegistry.register(id, type);
            PowerTypesAccessor.getLoadingPriorities().put(id, priority);
            if(!(type instanceof MultiplePowerType<?>)) {
                handleAdditionalData(id, factoryId, isSubPower, jo, type);
                PostPowerLoadCallback.EVENT.invoker().onPostPowerLoad(id, factoryId, isSubPower, jo, type);
            }
        } else {
            if(PowerTypesAccessor.getLoadingPriorities().get(id) < priority) {
                PowerTypeRegistryAccessor.invokeUpdate(id, type);
                PowerTypesAccessor.getLoadingPriorities().put(id, priority);
                if(!(type instanceof MultiplePowerType<?>)) {
                    handleAdditionalData(id, factoryId, isSubPower, jo, type);
                    PostPowerLoadCallback.EVENT.invoker().onPostPowerLoad(id, factoryId, isSubPower, jo, type);
                }
            }
        }
        return type;
    }

    private static void handleAdditionalData(Identifier powerId, Identifier factoryId, boolean isSubPower, JsonObject json, PowerType<?> powerType) {
        PowerTypesAccessor.getAdditionalData().forEach((dataFieldName, callback) -> {
            if(json.has(dataFieldName)) {
                callback.readAdditionalPowerData(powerId, factoryId, isSubPower, json.get(dataFieldName), powerType);
            }
        });
    }

    private static boolean isMultiple(Identifier id) {
        if(id.equals(Apoli.identifier("multiple"))) {
            return true;
        }
        if(NamespaceAlias.hasAlias(id)) {
            return NamespaceAlias.resolveAlias(id).equals(Apoli.identifier("multiple"));
        }
        return false;
    }
}
