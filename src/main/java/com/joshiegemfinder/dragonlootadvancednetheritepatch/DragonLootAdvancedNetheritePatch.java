package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autovw.advancednetherite.api.TooltipBuilder;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.compat.AdvancedNetheriteMedievalWeaponsCompat;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;

import net.fabricmc.api.ModInitializer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class DragonLootAdvancedNetheritePatch implements ModInitializer {
	public static final String MOD_ID = "dragon_loot_advanced_netherite_patch";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final MutableComponent DOUBLE_CROP_DROPS_TOOLTIP = TooltipBuilder.create(new ResourceLocation(MOD_ID, "hoe.double_crop_drops")).withStyle(ChatFormatting.AQUA);
	
	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
	
	@Override
	public void onInitialize() {
		DLANPConfigHolder.initializeConfig();
		AdvancedNetheriteMedievalWeaponsCompat.init();
		
		DLANPSwordLootTableModifiers.modifyTables();
		DLANPPickaxeLootTableModifiers.modifyTables();
		DLANPHoeLootTableModifiers.modifyTables();
		DLANPDataLoader.registerDatapacks();
	}
}