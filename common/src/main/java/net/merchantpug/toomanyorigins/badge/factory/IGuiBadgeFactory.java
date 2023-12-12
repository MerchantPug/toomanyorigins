package net.merchantpug.toomanyorigins.badge.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.toomanyorigins.util.TMODataTypes;

public interface IGuiBadgeFactory extends IBadgeFactory {

    @Override
    default SerializableData getSerializableData() {
        return new SerializableData()
                .add("sprite", SerializableDataTypes.IDENTIFIER)
                .add("prefix", SerializableDataTypes.TEXT, null)
                .add("suffix", SerializableDataTypes.TEXT, null)
                .add("background", TMODataTypes.GUI_BACKGROUND)
                .add("content", SerializableDataType.list(TMODataTypes.GUI_CONTENT_LIST), null);
    }

}