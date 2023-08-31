[A] Notes an Apugli related change.

### Bugfixes
- [A] [FORGE] Fixed a start-up crash caused by a mixin compilation error.

### Dependencies
- Updated Apugli to 2.6.6. This version's changelog can be read [here](https://github.com/MerchantPug/apugli/releases/tag/2.6.6%2B1.20.1)

### Developer Notes
Depending on TooManyOrigins in a Common/Xplat module has become a lot easier as I have now removed the extra dependencies from the Fabric version of Origins (of which Common used to depend on).

# Full Changelog Compared to 0.x.x
### General
- Reworked every origin. If you would like to have the old origins, you are able to use the legacy content built-in data pack.
- Reworded many descriptions to open up more player interpretation.
- Every Origins' order in the origins menu has been changed, TooManyOrigins' origins should now be found later but should always be together.

### Datapacking
- Added built-in Legacy TooManyOrigins data pack.
- Added `legacy_content.json` file found in the data pack directory `data/toomanyorigins/legacy_content.json`, this is able to enable any legacy content that the pack may need by setting a value in the json file to `true`. This can enable `dragon_fireball`, `withered_crops` and `zombifying`. This will be synced with clients.

### Translations
- Updated Korean translation. (Snap_Lan)
- Updated Turkish translation. (Hexasan)
- Removed all translations that have not been listed here. They have become obsolete as a result of this update.

### Hiss-kin
- Renamed `Overheat` to `Buildup`
- `Buildup`'s self damage will only damage you if you use the power whilst it is on cooldown.
- Changed `Buildup` cooldown from 5 ticks (0.25 seconds) to 15 ticks (0.75 seconds).
- `Buildup` self damage type now counts as explosive damage instead of fire damage.
- `Buildup` deals 25% of the base explosion damage instead of 50%.
- Increased the `Buildup` explosion's knockback when compared to Overheat.
- The Charged status effect now reduces the user's fall damage instead of granting them speed.
- Added a 2.5% chance to redirect naturally spawned lightning strikes to the user as a hidden power.
- `Ailurophobia` is now purely a visual effect and hidden. Now comes into play when near a Feline origin.
- Removed `Blast Resistant Hide`.
- Added `Aerial Stability` power in place of Blast Resistant Hide, which lowers your falling velocity.
- Renamed `Conductor` to `Conductive`.
- `Conductive` now gives you 8 minutes of the Charged status effect (previously 20).
- Renamed `Sneaking Steps` to `Stubby Legs`, you are now 25% slower instead of 5% slower.

### Hare
- Renamed `Bunny Hop` to `Hot Hops`.
- `Hot Hops` now updates upon jumping instead of whilst in the air.
- Lowered `Hot Hops`' resource to reach maximum after 7 jumps.
- Removed `Moon Leap` and `Sugary Delicacy`.
- Replaced `Lightweight` with `Frail`, which makes the Hare take 1 more damage from non entity sources.
- Added `Waterskipper` power, which allows you to stand on the surface of water for a short time whilst at maximum hopping speed.
- Added `Small Appetite` power, which makes you exhaust less.
- Added `Drenchable Fur` power, which makes you unable to move as quickly whilst in water.
- Hare now has step assist as a hidden power.

### Dragonborn
- Removed `Carnivore`, `Zenith Dragonhide`, and `Loose Scales` powers.
- Overhauled `Draconic Breath` to have 3 different breath types. Fire, Frost and Thunder. These can swapped between by holding a respective item in your main/offhand and using the power.
  - **General**
    - The shared cooldown of each breath type has been reduced to 2 seconds (previously 4 seconds).
  - **Fire**
    - No longer damages entities within the area effect cloud, it instead makes entities that aren't you inside the effect cloud receive more damage from sources.
    - Fire Breath Cloud radius now starts at 2.5, lasts for 5 seconds, and decreases by 0.00375 per tick.
    - Removed `Wyrmsmith's Touch` power. It is now built into the Fire breath type projectile when it hits a lightable block.
    - The above effect no longer affects Brewing Stands.
    - A Dragonborn starts with this breath type but it is still able to be swapped to using Coal or Charcoal.
  - **Frost**
    - Frost gradually slows down entities within the area effect cloud.
    - Frost Breath Cloud radius starts at 3.5, lasts for 9 seconds, and decreases by 0.00375 per tick.
    - The projectile can freeze water surfaces, this is similar to the effects of Frost Walker.
    - Can be swapped to with Snowballs.
  - **Thunder**
    - Thunder deals damage to entities inside the AoE that aren't you upon an entity in the AoE taking damage.
    - Thunder Breath Cloud radius starts at 3, lasts for 7 seconds, and decreases by 0.00375 per tick.
    - The projectile can activate lightning rods or summon lightning on lightning rods if it is thundering.
    - Can be swapped to with Copper Ingots.
- Renamed `Steady Stance` to `Zenith Scales`, it now only reduces 20% of knockback dealt instead of complete immunity.
- Added `Block Synergistics`, which doesn't do anything other than have a description to explain that the projectile can interact with blocks.
- Added `Weaponry Pride`, which makes you unable to use ranged weapons such as Bows, Crossbows, and Tridents that don't have Riptide, and Snowballs.
- Added `Rough Claws`, which has a chance to make an item lose 1 extra durability upon durability usage.

### Swarm
- Changed to Impact 2.
- `Hover` now operates on hunger as opposed to resource bars, you now exhaust when using it instead.
- `Hover` is unable to be used when the Swarm's hunger is below sprinting level.
- Using items whilst hovering will exhaust you at an even quicker rate.
- `Calming Aura` no longer works on animals that can attack other players or hostile mobs. (Defined in the entity type tag `toomanyorigins:ignore_calming_aura`, Foxes, Llamas, Hoglins and Wolves by default).
- Removed `Expendable` power.
- Removed `Smoke Sensitivity` power.
- Removed hidden `Aerial Affinity` power.
- Added `Stinging Pains`, which exhausts you upon harming hostile mobs.
- Added `Unity` power, which makes you lose maximum health based on how much hunger you have.

### Withered
- Removed `Wither Toxins`, `Black Thumb`, `Weariness` `Soul Shield` and `Infused Veins` powers.
- Added `Affliction` power in place of Wither Toxins, which inflicts Wither II on entities hit with certain projectiles.
- Added `Soul Siphon` power, which feeds you upon killing entities or from damage dealt through affliction.
- Added` Hell-Raised` power, which gives you an alternative recipe for arrows.
- Added `Deathly Digestion` power, which makes food items less nourishing for you and exhausts you quicker whilst not in the Nether.