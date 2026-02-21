package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.dragonloot.item.DragonToolMaterial;

@Mixin(DragonToolMaterial.class)
public class DragonToolMaterialMixin {
	@ModifyReturnValue(method = "getSpeed()F", at = @At("RETURN"), remap = true)
	public float modifyMiningSpeedMultiplier(float originalMiningSpeedMultiplier) {
		return DLANPConfigHolder.get().dragon_loot_tool_speed;
	}
}