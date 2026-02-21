package com.joshiegemfinder.dragonlootadvancednetheritepatch.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "dragonloot-advancednetherite-patch")
@Config.Gui.Background("minecraft:textures/block/endstone.png")
public class DragonLootAdvancedNetheritePatchConfig implements ConfigData {

	@Comment(
			"Requires reload\n" +
			"Default value = 54.0\n" +
			"Original Dragon tool speed is 12.0\n" +
			"Default Netherite-Diamond tool speed is 39.0"
	)
	public float dragon_loot_tool_speed = 54.0F;

	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_pickaxe_extra_iron = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_pickaxe_extra_gold = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_pickaxe_extra_emerald = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_pickaxe_extra_diamond = true;

	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_sword_extra_phantom_drops = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_sword_extra_piglin_drops = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_sword_extra_zombified_piglin_drops = true;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_sword_extra_enderman_drops = true;

	@Comment("Requires reload\nDefault value = false")
	public boolean dragon_hoe_extra_crop_drops = false;
	@Comment("Requires reload\nDefault value = true")
	public boolean dragon_hoe_double_crop_drops = true;

	@Comment(
			"Requires game restart\n" +
			"Default value = true\n" +
			"Removes the forced recipes for Dragon-tier Medieval Weapons, and loads custom recipes requiring Netherite-Diamond.\n" +
			"If enabled, the recipes for Dragon-tier Medieval Weapons can be replaced via datapack"
	)
	@ConfigEntry.Gui.RequiresRestart
	public boolean advanced_netherite_medieval_weapons_compat = true;
	
}
