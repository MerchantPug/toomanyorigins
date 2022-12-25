### Re-added Withered origin.
This nether bound origin focuses on gaining food in the nether by draining hunger from any unfortunate soul that may cross their path. This origin is applicable to players who enjoy their combat at a distance and then going all in against their targets.

**Config**
- Fireball Damages Undead Entities now defaults to false. Replicating vanilla behavior.

**Internal Changes**
- The Legacy TooManyOrigins Data Pack's description is now translatable text.
- Moved legacy content to their own files.
- Stubby Legs modifier name has been updated to reflect the power name change.

*Older betas can be found inside the Pug's Modzone Discord*

---

## Full Changelog Compared to 0.x.x
**General**
- Reworked every origin. If you would like to have the old origins, you are able to use the legacy content built-in data pack.
- Reworded many descriptions to open up more player interpretation.

**Datapacking**
- Added built-in Legacy TooManyOrigins data pack.
- Added `legacy_content.json` file found in the data pack directory `data/toomanyorigins/legacy_content.json`, this is able to enable any legacy content that the pack may need by setting a value in the json file to `true`. This can enable `dragon_fireball`, `withered_crops` and `zombifying`. This will be synced with clients.

**Translations**
- Updated Korean translation. (Thanks Snap_Lan)
- Updated Turkish translation. (Thanks Hexasan)
- Removed all translations that have not been listed here. They have become obsolete as a result of this update.

**Withered**
- Tl;dr now has a new set of powers as well as a new gameplay focus.
- Removed `Wither Toxins`, `Black Thumb`, `Weariness` `Spirit Strider`, `Soul Shield` and `Infused Veins` powers.
- Added `Affliction` power in place of Wither Toxins, of which inflicts Wither II on entities hit with certain projectiles.
- Added `Drainage` power, of which fills your hunger upon killing entities through physical contact.
- Added` Hell-Raised` power, of which gives you alternative recipes for bow related items.
- Added `Rush of Life` power, of which allows you to utilise one level of soul speed outside of normal conditions when your current target falls under a certain health threshold.
- Added `Heterotroph` power, of which makes food items less nourishing for you.

**Hiss-kin**
- Overheat is now less effective when the user is touching water.
- Overheat self damage type now counts as explosive damage instead of fire damage.
- Overheat deals 25% of the base explosion damage instead of 50%.
- The Charged status effect now reduces the user's fall damage instead of granting them speed.
- Added a 2.5% chance to redirect naturally spawned lightning strikes to the user as a hidden power.
- `Ailurophobia` is now purely a visual effect and hidden. Now comes into play when near a Feline origin.
- Removed `Blast Resistant Hide`.
- Added `Aerial Stability` power in place of Blast Resistant Hide, of which
- Renamed `Conductor` to `Conductive`.
- Renamed `Sneaking Steps` to `Stubby Legs`, you are now 25% slower instead of 5% slower.

**Swarm**
- Changed to Impact 2.
- Made internal changes to the Hover power.
- You can no longer recharge hover touching water or lava.
- `Beekeeper` is now a hidden power.
- Removed `Expendable` power.
- Removed `Smoke Sensitivity` power.
- Removed hidden `Aerial Affinity` power.
- Added `Stinging Pains`, of which exhausts you upon killing hostile mobs.
- Added `Unity` power, of which makes you lose maximum health based on how much hunger you have.