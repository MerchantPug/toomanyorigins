[A] means an Apugli related change.

**Origins**
- Hiss-kin's Overheat power has been rewritten from the ground up.
- Overheat now applies more velocity in horizontal directions than it did before.
- Removed Photophobia from Hare.
  - The power still exists in the data of the mod, it's just not used on Hare.
- Swarm's Hover power now has an active badge alongside the toggle badge.
- Swarm's Hover power's resource bar is now hidden when it is full.
- Swarm's Hover power now has step assist.
- Swarm's Smoke Sensitivity power has been reworked to exhaust the player when they are around smoke.
  - This change was made for the sake of accessibility when it comes to players with impaired hearing or sight, as well as the risk of triggering epilepsy when the condition of this power flashes.
  - The old power still exists in the mod under the original id of `toomanyorigins:smoke_sensitivity`, this new one is under `toomanyorigins:smoke_exhaustion`.

**Textures**
- Updated TooManyOrigins resource bars. (Thanks Omniv)

**Datapacking**
- Added `min_radius_modifier`, `min_radius_modifiers`, `max_radius_modifier`, `max_radius_modifiers`, `duration_modifier` and `duration_modifiers` fields to the `toomanyorigins:modify_dragon_fireball` power type.
- Added `damage` field to the `toomanyorigin:modify_dragon_fireball` power type for consistency sake.
- The non modifier fields on `toomanyorigins:modify_dragon_fireball` now resolve to the max value instead of the first in the case of multiple powers.
- Added `toomanyorigins:defined_keybind` badge type.

**Rewrite**
- Similar to Apugli, the mod is now additionally available for Forge.
  - Fabric does not have to wait for Origins Forge to update, as the module can be detached at any time.

**Config**
- `shouldFireballDamageUndead` is now a serversided config option.

**Dependencies**
- Updated Apugli to v2.3.0.

**Bugfixes**
- Fixed the resource transfer between disabled Swarm Hover and enabled Swarm Hover referencing an invalid power.