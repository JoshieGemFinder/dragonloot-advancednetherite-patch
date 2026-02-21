package com.joshiegemfinder.dragonlootadvancednetheritepatch.compat;

import java.util.List;

import com.joshiegemfinder.dragonlootadvancednetheritepatch.DragonLootAdvancedNetheritePatch;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat.MWRecipeManagerFallbackMixin;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.medievalweapons.compat.CompatRecipes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AdvancedNetheriteMedievalWeaponsCompat {

	// Early-loading (can be accessed during mixin phase)
	// If we access the main class during the mixin phase, then the game will crash because we load some classes earlier than expected
	public static final class MedievalWeaponsCompatMixinAccess {

		public static final boolean MODS_LOADED = FabricLoader.getInstance().isModLoaded("medievalweapons") && FabricLoader.getInstance().isModLoaded("advanced_netherite_medieval_weapons");

		// If the mixin to net.medievalweapons.compat.CompatRecipes was applied
		// If so, we don't need to use the fallback mixin
		public static boolean compatRecipesMixinApplied = false;

	}
	
	public static boolean areModsLoaded() {
		return MedievalWeaponsCompatMixinAccess.MODS_LOADED;
	}
	
	private static boolean canEnable() {
		return areModsLoaded() && DLANPConfigHolder.get().advanced_netherite_medieval_weapons_compat;
	}
	
	private static boolean enableResolved = false;
	private static boolean isEnabled = false;
	
	public static boolean isEnabled() {
		if(!enableResolved) {
			isEnabled = canEnable();
			enableResolved = true;
		}
		return isEnabled;
	}
	
	public static void init() {
		if(isEnabled() || canEnable()) {
			isEnabled = true;
			
			removeProtectedRecipes();
		}
	}
	
	// ======== DATAPACKS ========

	public static final ResourceLocation MEDIEVAL_WEAPONS_RECIPES_PACK_ID = DragonLootAdvancedNetheritePatch.prefix("medievalweapons_compat");
	
	public static void registerDatapacks(ModContainer modContainer) {
		if(isEnabled()) {
			ResourceManagerHelper.registerBuiltinResourcePack(
					MEDIEVAL_WEAPONS_RECIPES_PACK_ID, modContainer, 
					Component.translatable(MEDIEVAL_WEAPONS_RECIPES_PACK_ID.toLanguageKey("dataPack", "name")),
					ResourcePackActivationType.ALWAYS_ENABLED
				);
		}
	}

	// ======== RECIPE DATA ========
	
	// If the mixin to net.medievalweapons.compat.CompatRecipes was applied
	// If so, we don't need to use the fallback mixin
	/**
	 * If true, {@linkplain MWRecipeManagerFallbackMixin} doesn't need to do anything
	 * @return
	 */
	public static boolean getCompatRecipesMixinApplied() {
		return MedievalWeaponsCompatMixinAccess.compatRecipesMixinApplied;
	}
	
	private static final ResourceLocation medievalWeaponsId(String path) {
		return new ResourceLocation("medievalweapons", path);
	}

	// These recipe names should be prevented from even being created
	private static final List<String> PROTECTED_RECIPE_NAMES = List.of(
			"dragon_small_axe", "dragon_long_sword", "dragon_dagger",
			"dragon_francisca", "dragon_big_axe", "dragon_javelin",
			"dragon_lance", "dragon_healing_staff", "dragon_mace",
			"dragon_ninjato", "dragon_sickle", "dragon_rapier"
	);
	
	public static List<String> getProtectedRecipeNames() {
		return PROTECTED_RECIPE_NAMES;
	}
	
	// These recipe ids will be removed from the Medieval Weapons forced recipe list
	private static final List<ResourceLocation> PROTECTED_RECIPE_IDS = List.copyOf(PROTECTED_RECIPE_NAMES.stream().map(name -> medievalWeaponsId(name)).toList());

	public static List<ResourceLocation> getProtectedRecipeIDs() {
		return PROTECTED_RECIPE_IDS;
	}
	
	// TODO could remove this now that isEnabled() can be decided at any time
	private static void removeProtectedRecipes() {
		try {
			Class.forName("net.medievalweapons.compat.CompatRecipes");
		} catch (ClassNotFoundException e) {
			DragonLootAdvancedNetheritePatch.LOGGER.warn("Cannot find Medieval Weapons CompatRecipes class to remove protected recipes");
			return;
		}
		
		MedievalWeaponsCompatInternal.removeProtectedRecipes();
	}
	
	private static final class MedievalWeaponsCompatInternal {
		
		private static void removeProtectedRecipes() {
			if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
				List<ResourceLocation> protectedRecipes = AdvancedNetheriteMedievalWeaponsCompat.getProtectedRecipeIDs();
				for(ResourceLocation recipeID : protectedRecipes) {
					CompatRecipes.RECIPES.remove(recipeID);
				}
			}
		}
		
	}
}
