package net.merchantpug.toomanyorigins.badge.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.merchantpug.apugli.platform.Services;

public interface IDefinedKeybindBadgeFactory extends IBadgeFactory {

    @Override
    default SerializableData getSerializableData() {
        return new SerializableData()
                .add("sprite", SerializableDataTypes.IDENTIFIER)
                .add("text", SerializableDataTypes.STRING)
                .add("key", Services.PLATFORM.getKeyDataType());
    }

}