[A] means an Apugli related change.

**Origins**
- Swarm's Hover power's resource bar is now hidden when it is full.
- Swarm's Hover power now has step assist.
- Removed Photophobia from Hare.
  - The power still exists in the data of the mod, it's just not used on Hare.
- Smoke Sensitivity now exhausts the player when they're near smoke instead of the current effects.
  - The old power still exists in the mod under the old `toomanyorigins:smoke_sensitivity` power id.
  - The new power exists under the `toomanyorigins:smoke_exhaustion` power id.

**Datapacking**
- Added `min_radius_modifier`, `min_radius_modifiers`, `max_radius_modifier`, `max_radius_modifiers`, `duration_modifier` and `duration_modifiers` fields to the `toomanyorigins:modify_dragon_fireball` power type.
- Added `damage` field to the `toomanyorigin:modify_dragon_fireball` power type for consistency sake.
- The non modifier fields on `toomanyorigins:modify_dragon_fireball` now resolve to the max value instead of the first in the case of multiple powers.

**Rewrite**
- Similar to Apugli, the mod is now additionally available for Forge.
  - Fabric does not have to wait for Origins Forge to update, as the module can be detached at any time.

**Dependencies**
- Updated Apugli to v2.0.3.

**Bugfixes**
- Fixed the resource transfer between disabled Swarm Hover and enabled Swarm Hover referencing an invalid power.