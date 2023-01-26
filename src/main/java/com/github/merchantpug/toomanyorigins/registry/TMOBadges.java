package com.github.merchantpug.toomanyorigins.registry;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.badge.DefinedKeybindBadge;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.apace100.origins.badge.BadgeFactory;
import io.github.apace100.origins.badge.BadgeManager;

public class TMOBadges {
    public static final BadgeFactory DEFINED_KEYBIND = new BadgeFactory(TooManyOrigins.identifier("defined_keybind"),
            new SerializableData()
                    .add("sprite",SerializableDataTypes.IDENTIFIER)
                    .add("text", SerializableDataTypes.STRING)
                    .add("key",ApoliDataTypes.KEY),
            DefinedKeybindBadge::new);

    public static void register() {
        register(DEFINED_KEYBIND);
    }

    public static void register(BadgeFactory badgeFactory) {
        BadgeManager.register(badgeFactory);
    }
}
