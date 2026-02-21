## Dragon Loot Advanced Netherite Patch

This mod patches all of the equipment from DragonLoot to require Netherite-Diamond (from the Advanced Netherite mod) to craft.

<img width="534" height="372" alt="An image of the smithing recipes for DragonLoot items requiring Netherite-Diamond base items to craft instead of Netherite base items" src="https://github.com/user-attachments/assets/4a598e7d-c7a1-4aff-a7e7-315a1c444358" />

To compensate for the increased costs, Dragon items have increased attack damage, durability, mining speed, armour,
and gain all of the benefits of equivalent Advanced Netherite items (Note: this mod changes the default values, but
it may be necessary to manually update the DragonLoot config under certain circumstances; see "Updating DragonLoot Configuration" below).

<img width="1815" height="750" alt="An image of several DragonLoot items with improved stats, and Advanced Netherite item abilities" src="https://github.com/user-attachments/assets/629b1b39-d480-4677-8f99-018a11b7dfe7" />

### Configuration

This mod is highly configurable, and lets you change:
* The base mining speed of DragonLoot tools
* The special abilities that DragonLoot tools recieve
  * Dragon pickaxes get extra drops from iron, gold, emerald, and diamond ores by default
  * Dragon swords get extra drops from phantoms, piglins, zombified piglins, and endermen by default
  * Dragon hoes get double drops from all crops by default
* Compatibility with Medieval Weapons + Advanced Netherite Medieval Weapons (if installed)
  * Changes the recipes for Dragon tier Medieval Weapons to require Netherite-Diamond tier Medieval Weapons
  * Removes the forced recipe lock on Dragon tier Medieval Weapons (allows Dragon tier Medieval Weapons to have their recipe changed via datapack)
  * Increases the attack damage of Dragon tier Medieval Weapons, and gives them all of the Dragon Sword abilities
  * Increases the special ability values of the Dragon Healing Staff, Dragon Mace, and Dragon Rapier to be above the Netherite-Diamond special ability values

#### Updating DragonLoot Configuration

This mod updates some of the default values in the DragonLoot config.  
However, it **will not overwrite existing config values**, so if the game created a DragonLoot config without this mod then it will have the incorrect values.

If you notice that your DragonLoot tools or armour have less durability, attack damage, or armour toughness than Netherite-Diamond,
then you can either delete the DragonLoot config and let the game regenerate it with the right values, or manually change the values.

Here is a list of all the default values changed in the DragonLoot config:
* `dragon_armor_toughness`: Default `3.0` --> `5.0`
  * This is so Dragon armour has a higher toughness value than Netherite-Diamond (`4.0`)
* `dragon_armor_knockback_resistance`: Default `1.0` --> `1.5`
* `dragon_armor_enchantability`: Default `15` --> `18`
  * This is so Dragon armour doesn't have the same enchantability as Netherite-Diamond (`15`)
* `dragon_armor_durability_multiplier`: Default `37` --> `40`
  * This is so Dragon armour has a higher durability than Netherite-Diamond
* `dragon_item_durability_multiplier`: Default `37` --> `50`
  * This is so Dragon tools have a higher durability (total `3350`) than Netherite-Diamond (total `3092`)
* `dragon_item_base_damage`: Default `5.0` --> `8.0`
  * This is so Dragon tools have a higher base damage than Netherite-Diamond tools (`7.0`)

## Maintainance Info

This mod was commissioned for a minecraft server, and there are currently no plans for updating it past Fabric 1.20.1.

If you're interesting in maintaining this mod or updating it to later versions or different modloaders, reach out to me and I'll try to work something out.

## Usage

You can use this mod in modpacks and on servers freely and without permission.
