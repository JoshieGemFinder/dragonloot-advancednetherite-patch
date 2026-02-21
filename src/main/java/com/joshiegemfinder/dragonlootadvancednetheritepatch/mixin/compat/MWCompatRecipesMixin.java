package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat;

import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonObject;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.compat.AdvancedNetheriteMedievalWeaponsCompat;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.medievalweapons.compat.CompatRecipes;
import net.minecraft.resources.ResourceLocation;

@Mixin(value = CompatRecipes.class, remap = false)
public class MWCompatRecipesMixin {

	@ModifyExpressionValue(method = "createRecipes()V", at = @At(value = "FIELD", target = "net.medievalweapons.compat.CompatItems.isDragonLootLoaded:Ljava/lang/Boolean;", opcode = Opcodes.GETSTATIC), remap = false)
	private static Boolean preventDragonLootRecipes(Boolean isDragonLootLoaded) { // why must you use Boolean?
		return !AdvancedNetheriteMedievalWeaponsCompat.isEnabled() && isDragonLootLoaded;
	}
	
	
//	@Shadow(remap = false)
//	private static HashMap<String, ?> SMITHING_RECIPES;
//	
//	@Inject(method = "createRecipes()V", at = @At("TAIL"), remap = false)
//	private static void preventProtectedRecipes() {
//		if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
//			List<String> protectedRecipeNames = AdvancedNetheriteMedievalWeaponsCompat.getProtectedRecipeNames();
//			for(String recipeName: protectedRecipeNames) {
//				SMITHING_RECIPES.remove(recipeName);
//			}
//		}
//	}
	
	@Shadow(remap = false)
	private static HashMap<ResourceLocation, JsonObject> RECIPES;

	@Inject(method = "loadRecipes()V", at = @At("RETURN"), remap = false)
	private static void removeProtectedRecipes(CallbackInfo ci) {
		if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			List<ResourceLocation> protectedRecipes = AdvancedNetheriteMedievalWeaponsCompat.getProtectedRecipeIDs();
			for(ResourceLocation recipeID : protectedRecipes) {
				RECIPES.remove(recipeID);
			}
		}
	}
	
}
