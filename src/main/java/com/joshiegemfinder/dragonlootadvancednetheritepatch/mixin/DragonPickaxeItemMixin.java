package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;

import com.autovw.advancednetherite.config.ConfigHelper;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.DLANPPickaxeLootTableModifiers;

import net.dragonloot.item.DragonPickaxeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@Mixin(DragonPickaxeItem.class)
public class DragonPickaxeItemMixin extends PickaxeItem {

	public DragonPickaxeItemMixin(Tier tier, int i, float f, Properties properties) {
		super(tier, i, f, properties);
	}
	
	@Override
//	@Inject(method = "appendHoverText", at = @At("HEAD"))
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag)
	{
		if (ConfigHelper.get().getClient().showTooltips())
		{
			DLANPPickaxeLootTableModifiers.appendPickaxeOreHoverText(stack, world, tooltip, flag);
		}
		super.appendHoverText(stack, world, tooltip, flag);
	}

}
