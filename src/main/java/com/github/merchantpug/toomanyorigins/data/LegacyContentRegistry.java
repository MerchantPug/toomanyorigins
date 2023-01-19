package com.github.merchantpug.toomanyorigins.data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class LegacyContentRegistry {
    private static final Map<String, Boolean> REGISTRY = new HashMap<>();

    public static void disableAll() {
        REGISTRY.replaceAll((string, bool) -> false);
    }

    public static Stream<Map.Entry<String, Boolean>> asStream() {
        return REGISTRY.entrySet().stream();
    }

    public static boolean isContentDisabled(String contentName) {
        return !REGISTRY.get(contentName);
    }

    public static void enable(String contentName) {
        REGISTRY.put(contentName, true);
    }

    public static void init() {
        REGISTRY.put(LegacyContentModules.DRAGON_FIREBALL, false);
        REGISTRY.put(LegacyContentModules.WITHERED_CROPS, false);
        REGISTRY.put(LegacyContentModules.ZOMBIFYING, false);
    }
}
