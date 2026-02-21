package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import net.dragonloot.config.DragonLootConfig;

/**
 * @reason Adjusts the *default* config values so the progression is intact, even with default configs
 */
@Mixin(DragonLootConfig.class)
public class DragonLootConfigMixin {

	@ModifyConstant(
			method = "()V",
			constant = @Constant(floatValue = 3.0f),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_protection_boots:I"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_toughness:F")
				)
		)
	private static float modifyDefaultToughness(float original) {
		return 5.0f;
	}

	@ModifyConstant(
			method = "()V",
			constant = @Constant(floatValue = 1.0f),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_toughness:F"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_knockback_resistance:F")
				)
		)
	private static float modifyDefaultKnockbackResistance(float original) {
		return 1.5f;
	}

	@ModifyConstant(
			method = "()V",
			constant = @Constant(intValue = 15),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_knockback_resistance:F"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_enchantability:I")
				)
		)
	private static int modifyDefaultArmourEnchantability(int original) {
		return 18;
	}

	@ModifyConstant(
			method = "()V",
			constant = @Constant(intValue = 37),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_enchantability:I"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_durability_multiplier:I")
				)
		)
	private static int modifyDefaultArmourDurability(int original) {
		return 40;
	}

	@ModifyConstant(
			method = "()V",
			constant = @Constant(intValue = 37),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_armor_durability_multiplier:I"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_item_durability_multiplier:I")
				)
		)
	private static int modifyDefaultToolDurability(int original) {
		return 50;
	}


	@ModifyConstant(
			method = "()V",
			constant = @Constant(floatValue = 5.0f),
			slice = @Slice(
					from = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_item_durability_multiplier:I"),
					to = @At(value = "FIELD", target = "net.dragonloot.config.DragonLootConfig.dragon_item_base_damage:F")
				)
		)
	private static float modifyDefaultBaseDamage(float original) {
		return 8.0f;
	}
}