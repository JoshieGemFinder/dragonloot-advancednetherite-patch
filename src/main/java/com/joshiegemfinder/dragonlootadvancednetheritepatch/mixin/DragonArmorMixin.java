package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.autovw.advancednetherite.api.impl.IAdvancedHooks;
import com.autovw.advancednetherite.config.ConfigHelper;

import net.dragonloot.item.DragonArmor;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

@Mixin(DragonArmor.class)
public class DragonArmorMixin extends ArmorItem implements IAdvancedHooks {
	public DragonArmorMixin(ArmorMaterial armorMaterial, Type type, Properties properties) {
		super(armorMaterial, type, properties);
	}

	@Override
	public boolean pacifyEndermen(ItemStack stack) {
		return ConfigHelper.get().getCommon().getArmor().isDiamondEndermanPassiveArmor();
	}
	
	@Override
	public boolean pacifyPiglins(ItemStack stack) {
		return ConfigHelper.get().getCommon().getArmor().isDiamondPiglinPassiveArmor();
	}
	
	@Override
	public boolean pacifyPhantoms(ItemStack stack) {
		return ConfigHelper.get().getCommon().getArmor().isDiamondPhantomPassiveArmor();
	}
}