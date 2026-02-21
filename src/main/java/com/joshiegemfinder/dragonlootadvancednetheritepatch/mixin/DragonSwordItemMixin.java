package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import com.autovw.advancednetherite.config.ConfigHelper;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.DLANPSwordLootTableModifiers;

import net.dragonloot.item.DragonSwordItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(DragonSwordItem.class)
public class DragonSwordItemMixin extends SwordItem {

	public DragonSwordItemMixin(Tier tier, int i, float f, Properties properties) {
		super(tier, i, f, properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag)
	{
		if (ConfigHelper.get().getClient().showTooltips()) {
			DLANPSwordLootTableModifiers.appendSwordLootHoverText(stack, world, tooltip, flag);
		}
		super.appendHoverText(stack, world, tooltip, flag);
	}

}
