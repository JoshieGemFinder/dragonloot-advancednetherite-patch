package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import java.util.ArrayList;
import java.util.List;

import com.autovw.advancednetherite.api.impl.IAdvancedHooks;
import com.autovw.advancednetherite.config.ConfigHelper;
import com.autovw.advancednetherite.core.util.ModTooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class DragonLootAdvancedNetheritePatchClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			if (ConfigHelper.get().getClient().showTooltips() && stack.getItem() instanceof IAdvancedHooks hooks)
			{
				if (Screen.hasShiftDown())
				{
					List<Component> tooltips = new ArrayList<>(3);
					if (hooks.pacifyEndermen(stack))
						tooltips.add(ModTooltips.ENDERMAN_PASSIVE_TOOLTIP);
					if (hooks.pacifyPiglins(stack))
						tooltips.add(ModTooltips.PIGLIN_PASSIVE_TOOLTIP);
					if (hooks.pacifyPhantoms(stack))
						tooltips.add(ModTooltips.PHANTOM_PASSIVE_TOOLTIP);
					if(tooltips.size() > 0)
						lines.addAll(1, tooltips);
				}
				else
				{
					if (hooks.pacifyEndermen(stack) || hooks.pacifyPiglins(stack) || hooks.pacifyPhantoms(stack))
						lines.add(1, ModTooltips.SHIFT_KEY_TOOLTIP);
				}
			}
		});
	}
}