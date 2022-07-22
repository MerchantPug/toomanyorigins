package com.github.merchantpug.toomanyorigins.integration;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.github.merchantpug.toomanyorigins.mixin.OriginLayerAccessor;
import com.github.merchantpug.toomanyorigins.mixin.OriginRegistryAccessor;
import com.github.merchantpug.toomanyorigins.util.PowerTypeReadingUtil;
import com.github.merchantpug.toomanyorigins.util.TooManyOriginsConfig;
import com.google.gson.JsonParser;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeRegistry;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.origin.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.util.List;

public class LegacyContentRegistrationEvent {
    /*
     MODDERS HATE HER, CLICK HERE TO FIND OUT HOW
    */
    public static final String LEGACY_POWERS_PATH = "/data/toomanyorigins/legacy_powers/";

    public static void onDataLoaded(boolean isClient) {
        if (isClient) return;
        if (TooManyOrigins.legacyDragonbornContentRegistered) {
            loadLegacyDragonborn();
        }
        if (TooManyOriginsConfig.legacyDragonbornEnabled && !TooManyOrigins.legacyDragonbornContentRegistered) {
            TooManyOrigins.LOGGER.warn("Attempted to load the Legacy Dragonborn Origin without the required content. Either restart your game or turn the option off.");
        }

        if (TooManyOriginsConfig.legacyHareEnabled) {
            loadLegacyHare();
        }

        if (TooManyOriginsConfig.legacyHisskinEnabled) {
            loadLegacyHisskin();
        }

        if (TooManyOriginsConfig.legacySwarmEnabled) {
            loadLegacySwarm();
        }

        if (TooManyOrigins.legacyUndeadContentRegistered) {
            loadLegacyUndead();
        }
        if (TooManyOriginsConfig.legacyUndeadEnabled && !TooManyOrigins.legacyUndeadContentRegistered) {
            TooManyOrigins.LOGGER.warn("Attempted to load the Legacy Undead Origin without the required content. Either restart your game or turn the option off.");
        }

        if (TooManyOrigins.legacyWitheredContentRegistered) {
            loadLegacyWithered();
        }
        if (TooManyOriginsConfig.legacyWitheredEnabled && !TooManyOrigins.legacyWitheredContentRegistered) {
            TooManyOrigins.LOGGER.warn("Attempted to load the Legacy Withered Origin without the required content. Either restart your game or turn the option off.");
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacyDragonborn() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("dragonborn")) || OriginRegistry.get(TooManyOrigins.identifier("dragonborn")).getLoadingPriority() == 0) {
            Origin dragonbornOrigin = new Origin(TooManyOrigins.identifier("dragonborn"), new ItemStack(Items.DRAGON_BREATH), Impact.LOW, 8, 0).setDescription("origin.legacy_toomanyorigins.dragonborn.description");

            InputStreamReader dragonBreathReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "dragon_breath.json"));
            InputStreamReader lightUpReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "light_up.json"));
            InputStreamReader noKnockbackReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "no_knockback.json"));
            InputStreamReader looseScalesReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "loose_scales.json"));
            InputStreamReader zenithDragonhideReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "zenith_dragonhide.json"));

            dragonbornOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("dragon_breath"), JsonParser.parseReader(dragonBreathReader)));
            dragonbornOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("light_up"), JsonParser.parseReader(lightUpReader)));
            dragonbornOrigin.add(PowerTypeRegistry.get(Origins.identifier("carnivore")));
            dragonbornOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("no_knockback"), JsonParser.parseReader(noKnockbackReader)));
            dragonbornOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("loose_scales"), JsonParser.parseReader(looseScalesReader)));
            dragonbornOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("zenith_dragonhide"), JsonParser.parseReader(zenithDragonhideReader)));

            // Temporary code until the origin gets reworked
            if (OriginLayers.getLayers().stream().noneMatch(originLayer -> originLayer.contains(dragonbornOrigin))) {
                ((OriginLayerAccessor)OriginLayers.getLayer(Origins.identifier("origin"))).getConditionedOrigins().add(new OriginLayer.ConditionedOrigin(null, List.of(TooManyOrigins.identifier("dragonborn"))));
            }

            OriginRegistryAccessor.invokeUpdate(dragonbornOrigin.getIdentifier(), dragonbornOrigin);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacyHare() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("hare")) || OriginRegistry.get(TooManyOrigins.identifier("hare")).getLoadingPriority() == 0) {
            Origin hareOrigin = new Origin(TooManyOrigins.identifier("hare"), new ItemStack(Items.RABBIT_FOOT), Impact.LOW, 5, 0).setDescription("origin.legacy_toomanyorigins.hare.description");

            InputStreamReader bunnyHopReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "bunny_hop.json"));
            InputStreamReader bunnyHopActiveReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "bunny_hop_active.json"));
            InputStreamReader photophobiaReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "photophobia.json"));
            InputStreamReader sugaryDelicacyReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "sugary_delicacy.json"));
            InputStreamReader moonLeapReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "moon_leap.json"));
            InputStreamReader lightweightReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "lightweight.json"));
            InputStreamReader luckyFootingReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "lucky_footing.json"));

            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("bunny_hop"), JsonParser.parseReader(bunnyHopReader)));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("bunny_hop_active"), JsonParser.parseReader(bunnyHopActiveReader)));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("photophobia"), JsonParser.parseReader(photophobiaReader)));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("sugary_delicacy"), JsonParser.parseReader(sugaryDelicacyReader)));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("moon_leap"), JsonParser.parseReader(moonLeapReader)));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("lightweight"), JsonParser.parseReader(lightweightReader)));
            hareOrigin.add(PowerTypeRegistry.get(Origins.identifier("vegetarian")));
            hareOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("lucky_footing"), JsonParser.parseReader(luckyFootingReader)));

            // Temporary code until the origin gets reworked
            if (OriginLayers.getLayers().stream().noneMatch(originLayer -> originLayer.contains(hareOrigin))) {
                ((OriginLayerAccessor)OriginLayers.getLayer(Origins.identifier("origin"))).getConditionedOrigins().add(new OriginLayer.ConditionedOrigin(null, List.of(TooManyOrigins.identifier("hare"))));
            }
            OriginRegistryAccessor.invokeUpdate(hareOrigin.getIdentifier(), hareOrigin);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacyHisskin() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("hisskin")) || OriginRegistry.get(TooManyOrigins.identifier("hisskin")).getLoadingPriority() == 0) {
            Origin hisskinOrigin = new Origin(TooManyOrigins.identifier("hisskin"), new ItemStack(Items.GUNPOWDER), Impact.LOW, 6, 0).setDescription("origin.legacy_toomanyorigins.hisskin.description");

            InputStreamReader overheatReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "overheat.json"));
            InputStreamReader conductorReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "conductor.json"));
            InputStreamReader blastImmunityReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "blast_immunity.json"));
            InputStreamReader sneakingStepsReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "sneaking_steps.json"));
            InputStreamReader ailurophobiaReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "ailurophobia.json"));

            hisskinOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("overheat"), JsonParser.parseReader(overheatReader)));
            hisskinOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("conductor"), JsonParser.parseReader(conductorReader)));
            hisskinOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("blast_immunity"), JsonParser.parseReader(blastImmunityReader)));
            hisskinOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("sneaking_steps"), JsonParser.parseReader(sneakingStepsReader)));
            hisskinOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("ailurophobia"), JsonParser.parseReader(ailurophobiaReader)));

            OriginRegistryAccessor.invokeUpdate(hisskinOrigin.getIdentifier(), hisskinOrigin);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacySwarm() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("swarm")) || OriginRegistry.get(TooManyOrigins.identifier("swarm")).getLoadingPriority() == 0) {
            Origin swarmOrigin = new Origin(TooManyOrigins.identifier("swarm"), new ItemStack(Items.HONEYCOMB), Impact.LOW, 7, 0).setDescription("origin.legacy_toomanyorigins.swarm.description");

            InputStreamReader hoverReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "hover.json"));
            InputStreamReader hoverToggleReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "hover_toggle.json"));
            InputStreamReader smokeSensitivityReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "smoke_sensitivity.json"));
            InputStreamReader expendableReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "expendable.json"));
            InputStreamReader beekeeperReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "beekeeper.json"));
            InputStreamReader aerialAffinityReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "aerial_affinity.json"));

            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("hover"), JsonParser.parseReader(hoverReader)));
            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("hover_toggle"), JsonParser.parseReader(hoverToggleReader)));
            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("smoke_sensitivity"), JsonParser.parseReader(smokeSensitivityReader)));
            swarmOrigin.add(PowerTypeRegistry.get(TooManyOrigins.identifier("pollination")));
            swarmOrigin.add(PowerTypeRegistry.get(TooManyOrigins.identifier("calming_aura")));
            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("expendable"), JsonParser.parseReader(expendableReader)));
            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("beekeeper"), JsonParser.parseReader(beekeeperReader)));
            swarmOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("aerial_affinity"), JsonParser.parseReader(aerialAffinityReader)));
            swarmOrigin.add(PowerTypeRegistry.get(Origins.identifier("arthropod")));

            OriginRegistryAccessor.invokeUpdate(swarmOrigin.getIdentifier(), swarmOrigin);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacyUndead() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("undead")) || OriginRegistry.get(TooManyOrigins.identifier("undead")).getLoadingPriority() == 0) {
            Origin undeadOrigin = new Origin(TooManyOrigins.identifier("undead"), new ItemStack(Items.ROTTEN_FLESH), Impact.MEDIUM, 3, 0).setDescription("origin.legacy_toomanyorigins.undead.description");

            InputStreamReader burnTimerReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "burn_timer.json"));
            InputStreamReader deathlyBiteReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "deathly_bite.json"));
            InputStreamReader noHelmetBurnReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "no_helmet_burn.json"));
            InputStreamReader oppositeDayReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "opposite_day.json"));
            InputStreamReader cannibalismReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "cannibalism.json"));
            InputStreamReader laceratedLiverReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "lacerated_liver.json"));
            InputStreamReader damageHelmetWhenInSunReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "damage_helmet_when_in_sun.json"));
            InputStreamReader unholyReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "unholy.json"));
            InputStreamReader zombifyHitReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "zombify_hit.json"));
            InputStreamReader undeadImmunitiesReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "undead_immunities.json"));
            InputStreamReader undeadCallbackReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "undead_callback.json"));

            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("burn_timer"), JsonParser.parseReader(burnTimerReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("deathly_bite"), JsonParser.parseReader(deathlyBiteReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("no_helmet_burn"), JsonParser.parseReader(noHelmetBurnReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("opposite_day"), JsonParser.parseReader(oppositeDayReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("cannibalism"), JsonParser.parseReader(cannibalismReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("lacerated_liver"), JsonParser.parseReader(laceratedLiverReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("damage_helmet_when_in_sun"), JsonParser.parseReader(damageHelmetWhenInSunReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("unholy"), JsonParser.parseReader(unholyReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("zombify_hit"), JsonParser.parseReader(zombifyHitReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("undead_immunities"), JsonParser.parseReader(undeadImmunitiesReader)));
            undeadOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("undead_callback"), JsonParser.parseReader(undeadCallbackReader)));

            // Temporary code until the origin gets reworked
            if (OriginLayers.getLayers().stream().noneMatch(originLayer -> originLayer.contains(undeadOrigin))) {
                ((OriginLayerAccessor)OriginLayers.getLayer(Origins.identifier("origin"))).getConditionedOrigins().add(new OriginLayer.ConditionedOrigin(null, List.of(TooManyOrigins.identifier("undead"))));
            }
            OriginRegistryAccessor.invokeUpdate(undeadOrigin.getIdentifier(), undeadOrigin);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void loadLegacyWithered() {
        if (!OriginRegistry.contains(TooManyOrigins.identifier("withered")) || OriginRegistry.get(TooManyOrigins.identifier("withered")).getLoadingPriority() == 0) {
            Origin witheredOrigin = new Origin(TooManyOrigins.identifier("withered"), new ItemStack(Items.WITHER_ROSE), Impact.HIGH, 4, 0).setDescription("origin.legacy_toomanyorigins.withered.description");

            InputStreamReader witherToxinsReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "wither_toxins.json"));
            InputStreamReader blackThumbReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "black_thumb.json"));
            InputStreamReader wearinessReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "weariness.json"));
            InputStreamReader spiritStriderReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "spirit_strider.json"));
            InputStreamReader soulShieldReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "soul_shield.json"));
            InputStreamReader soulShieldOverlayReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "soul_shield_overlay.json"));
            InputStreamReader witherImmunityReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "wither_immunity.json"));
            InputStreamReader unholyReader = new InputStreamReader(TooManyOrigins.class.getResourceAsStream(LEGACY_POWERS_PATH + "unholy.json"));

            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("wither_toxins"), JsonParser.parseReader(witherToxinsReader)));
            witheredOrigin.add(PowerTypeRegistry.get(Origins.identifier("nether_spawn")));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("black_thumb"), JsonParser.parseReader(blackThumbReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("weariness"), JsonParser.parseReader(wearinessReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("spirit_strider"), JsonParser.parseReader(spiritStriderReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("soul_shield"), JsonParser.parseReader(soulShieldReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("soul_shield_overlay"), JsonParser.parseReader(soulShieldOverlayReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("wither_immunity"), JsonParser.parseReader(witherImmunityReader)));
            witheredOrigin.add(PowerTypeReadingUtil.getPowersFromJson(legacyIdentifer("unholy"), JsonParser.parseReader(unholyReader)));

            // Temporary code until the origin gets reworked
            if (OriginLayers.getLayers().stream().noneMatch(originLayer -> originLayer.contains(witheredOrigin))) {
                ((OriginLayerAccessor)OriginLayers.getLayer(Origins.identifier("origin"))).getConditionedOrigins().add(new OriginLayer.ConditionedOrigin(null, List.of(TooManyOrigins.identifier("withered"))));
            }
            OriginRegistryAccessor.invokeUpdate(witheredOrigin.getIdentifier(), witheredOrigin);
        }
    }

    private static Identifier legacyIdentifer(String path) {
        return new Identifier("legacy_toomanyorigins", path);
    }
}
