package net.merchantpug.toomanyorigins.badge.factory;

import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.badge.Badge;
import net.merchantpug.apugli.platform.Services;
import net.merchantpug.toomanyorigins.badge.IDefinedKeybindBadge;

import java.util.function.Function;

public interface IDefinedKeybindBadgeFactory extends IBadgeFactory {

    @Override
    default SerializableData getSerializableData() {
        return new SerializableData()
                .add("sprite", SerializableDataTypes.IDENTIFIER)
                .add("text", SerializableDataTypes.STRING)
                .add("key", Services.PLATFORM.getKeyDataType());
    }

}