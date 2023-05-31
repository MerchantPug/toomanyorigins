package net.merchantpug.toomanyorigins.badge.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.origins.badge.Badge;

import java.util.function.Function;

public interface IBadgeFactory {

    SerializableData getSerializableData();

    Function<SerializableData.Instance, Badge> getFactoryCreator();

}