*As always these features are subject to change.*

**Dependencies**
- Updated to Minecraft 1.19.3.
- Dependencies have been updated accordingly.

---

## Full Changelog Compared to 0.x.x
**General**
- Reworked every origin. If you would like to have the old origins, you are able to use the legacy content built-in data pack.
- Reworded many descriptions to open up more player interpretation.

**Datapacking**
- Added built-in Legacy TooManyOrigins data pack.
- Added `legacy_content.json` file found in the data pack directory `data/toomanyorigins/legacy_content.json`, this is able to enable any legacy content that the pack may need by setting a value in the json file to `true`. This can enable `dragon_fireball`, `withered_crops` and `zombifying`. This will be synced with clients.

**Translations**
- Updated Korean translation. (Snap_Lan)
- Updated Turkish translation. (Hexasan)
- Removed all translations that have not been listed here. They have become obsolete as a result of this update.

**Withered**
- Removed `Wither Toxins`, `Black Thumb`, `Weariness` `Soul Shield` and `Infused Veins` powers.
- Added `Affliction` power in place of Wither Toxins, which inflicts Wither II on entities hit with certain projectiles.
- Added `Drainage` power, which feeds you upon killing entities.
- Added` Hell-Raised` power, which gives you an alternative recipe for arrows.
- Added `Deathly Digestion` power, which makes food items less nourishing for you and exhausts you quicker whilst not in the Nether.

**Hiss-kin**
- Overheat is now less effective when the user is touching water.
- Overheat self damage type now counts as explosive damage instead of fire damage.
- Overheat deals 25% of the base explosion damage instead of 50%.
- The Charged status effect now reduces the user's fall damage instead of granting them speed.
- Added a 2.5% chance to redirect naturally spawned lightning strikes to the user as a hidden power.
- `Ailurophobia` is now purely a visual effect and hidden. Now comes into play when near a Feline origin.
- Removed `Blast Resistant Hide`.
- Added `Aerial Stability` power in place of Blast Resistant Hide, which lowers your falling velocity.
- Renamed `Conductor` to `Conductive`.
- `Conductive` now gives you 8 minutes of the Charged status effect (previously 20).
- Renamed `Sneaking Steps` to `Stubby Legs`, you are now 25% slower instead of 5% slower.

**Swarm**
- Changed to Impact 2.
- `Hover` now operates on hunger as opposed to resource bars, you now exhaust when using it instead.
- `Hover` is unable to be used when the Swarm's hunger is below sprinting level. 
- You are unable to use ranged weapons that aren't enchanted with Riptide whilst hovering.
- `Calming Aura` no longer works on animals that can attack other players or hostile mobs. (Defined in the entity type tag `toomanyorigins:ignore_calming_aura`, Foxes, Llamas, Hoglins and Wolves by default).
- `Beekeeper` is now a hidden power.
- Removed `Expendable` power.
- Removed `Smoke Sensitivity` power.
- Removed hidden `Aerial Affinity` power.
- Added `Stinging Pains`, which exhausts you upon harming hostile mobs.
- Added `Unity` power, which makes you lose maximum health based on how much hunger you have.