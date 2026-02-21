package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import com.joshiegemfinder.dragonlootadvancednetheritepatch.compat.AdvancedNetheriteMedievalWeaponsCompat;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class DLANPDataLoader {

	public static final ResourceLocation DRAGONLOOT_RECIPES_PACK_ID = DragonLootAdvancedNetheritePatch.prefix("dragonloot_recipes");
	
	private static void registerDatapack(ResourceLocation id, ModContainer modContainer) {
		ResourceManagerHelper.registerBuiltinResourcePack(id, modContainer, Component.translatable(id.toLanguageKey("dataPack", "name")), ResourcePackActivationType.DEFAULT_ENABLED);
	}
	
	public static void registerRecipeOverrides(ModContainer modContainer) {
		registerDatapack(DRAGONLOOT_RECIPES_PACK_ID, modContainer);
		
		AdvancedNetheriteMedievalWeaponsCompat.registerDatapacks(modContainer);
	}
	
	public static void registerDatapacks() {
		FabricLoader.getInstance()
			.getModContainer(DragonLootAdvancedNetheritePatch.MOD_ID)
			.ifPresent(
					modContainer -> {
						registerRecipeOverrides(modContainer);
					}
				);
	}
	
}
