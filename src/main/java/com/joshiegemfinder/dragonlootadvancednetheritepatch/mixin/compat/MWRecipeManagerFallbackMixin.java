package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonElement;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.compat.AdvancedNetheriteMedievalWeaponsCompat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;

@Mixin(RecipeManager.class)
public class MWRecipeManagerFallbackMixin {

	@Unique
	private static final ThreadLocal<List<Optional<JsonElement>>> PROTECTED_RECIPES_BACKUP = ThreadLocal.withInitial(() -> new ArrayList<>(16));

	// This code should run before Medieval Weapons injects its recipes
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"), order = 900)
	private void beforeApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
		if(!AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			return;
		}
		
//		System.out.println("beforeApply runs now");
//		DragonLootAdvancedNetheritePatch.LOGGER.info("beforeApply runs now");
		if(AdvancedNetheriteMedievalWeaponsCompat.getCompatRecipesMixinApplied()) {
			// We've been replaced by the CompatRecipes mixin
			return;
		}
		
		List<Optional<JsonElement>> protectedRecipeBackup = PROTECTED_RECIPES_BACKUP.get();
		protectedRecipeBackup.clear();
		
		List<ResourceLocation> protectedRecipes = AdvancedNetheriteMedievalWeaponsCompat.getProtectedRecipeIDs();
		
		for(ResourceLocation recipeId : protectedRecipes) {
			if(!map.containsKey(recipeId)) {
				protectedRecipeBackup.add(Optional.empty());
			} else {
				protectedRecipeBackup.add(Optional.of(map.get(recipeId)));
			}
		}
	}

	// This code should run after Medieval Weapons injects its recipes
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"), order = 1100)
	private void afterApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller, CallbackInfo ci) {
		if(!AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			return;
		}
		
//		System.out.println("afterApply runs now");
//		DragonLootAdvancedNetheritePatch.LOGGER.info("afterApply runs now");
		if(AdvancedNetheriteMedievalWeaponsCompat.getCompatRecipesMixinApplied()) {
			// We've been replaced by the CompatRecipes mixin
			return;
		}
		
		List<Optional<JsonElement>> protectedRecipeBackup = PROTECTED_RECIPES_BACKUP.get();
		List<ResourceLocation> protectedRecipes = AdvancedNetheriteMedievalWeaponsCompat.getProtectedRecipeIDs();
		
		int size = protectedRecipes.size();
		
		if(protectedRecipeBackup.size() != size) {
			System.out.println("Warning! protectedRecipeBackup has a different size to protectedRecipes!");
			return;
		}
		
		for(int i = 0; i < size; ++i) {
			ResourceLocation recipeId = protectedRecipes.get(i);
			Optional<JsonElement> recipeBackup = protectedRecipeBackup.get(i);
			if(recipeBackup.isEmpty()) {
				map.remove(recipeId);
			} else {
				map.put(recipeId, recipeBackup.get());
			}
		}
	}
	
}
