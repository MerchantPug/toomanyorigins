package net.merchantpug.toomanyorigins.data;

public class LegacyContentRegistry {
    public static final String DRAGON_FIREBALL = "dragon_fireball";
    public static final String WITHERED_CROPS = "withered_crops";
    public static final String ZOMBIFYING = "zombifying";

    private static LegacyContentRecord REGISTRY = new LegacyContentRecord(false, false, false);

    public static void disableAll() {
        REGISTRY = new LegacyContentRecord(false, false, false);
    }

    public static void setRecord(boolean dragonFireball, boolean witheredCrops, boolean zombifying) {
        REGISTRY = new LegacyContentRecord(dragonFireball, witheredCrops, zombifying);
    }

    public static void updateRecord(boolean dragonFireball, boolean witheredCrops, boolean zombifying) {
        var oldRecord = REGISTRY;
        REGISTRY = new LegacyContentRecord(dragonFireball || oldRecord.dragonFireballEnabled(), witheredCrops || oldRecord.witheredCropsEnabled(), zombifying || oldRecord.zombifyingEnabled());
    }

    public static boolean isDragonFireballEnabled() {
        return REGISTRY.dragonFireballEnabled();
    }

    public static boolean areWitheredCropsEnabled() {
        return REGISTRY.witheredCropsEnabled();
    }

    public static boolean isZombifyingEffectEnabled() {
        return REGISTRY.zombifyingEnabled();
    }

}
