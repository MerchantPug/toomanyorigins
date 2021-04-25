package io.github.merchantpug.toomanyorigins.util;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.EntityGroup;

public class TMOSerializableDataType {
    public static final SerializableDataType<EntityGroup> TMO_ENTITY_GROUP =
            SerializableDataType.mapped(EntityGroup.class, HashBiMap.create(ImmutableMap.of(
                    "smiteable", TMOEntityGroups.SMITEABLE
            )));
}
