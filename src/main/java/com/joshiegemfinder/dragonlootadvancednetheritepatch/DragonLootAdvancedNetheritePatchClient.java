package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import java.util.ArrayList;
import java.util.List;

import com.autovw.advancednetherite.api.impl.IAdvancedHooks;
import com.autovw.advancednetherite.config.ConfigHelper;
import com.autovw.advancednetherite.core.util.ModTooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DragonLootAdvancedNetheritePatchClient implements ClientModInitializer {

	public static final TagKey<Item> EXTERNAL_ARMOUR_TOOLTIP = TagKey.create(Registries.ITEM, DragonLootAdvancedNetheritePatch.prefix("external_armour_tooltip"));
	
	@Override
	public void onInitializeClient() {
		// Armour ability tooltips
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			if (ConfigHelper.get().getClient().showTooltips() && stack.is(EXTERNAL_ARMOUR_TOOLTIP) && stack.getItem() instanceof IAdvancedHooks hooks)
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
				} else if (hooks.pacifyEndermen(stack) || hooks.pacifyPiglins(stack) || hooks.pacifyPhantoms(stack)) {
						lines.add(1, ModTooltips.SHIFT_KEY_TOOLTIP);
				}
			}
		});
		
		// Tool ability tooltips
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			// Make sure advanced netherite ability tooltips are enabled
			if (!ConfigHelper.get().getClient().showTooltips()) {
				return;
			}
			
			// Check what types of tooltips it has
			boolean displaySwordTooltips   = DLANPSwordLootTableModifiers.hasExternalTooltipTag(stack)   && DLANPSwordLootTableModifiers.hasAbilityTooltips(stack);
			boolean displayPickaxeTooltips = DLANPPickaxeLootTableModifiers.hasExternalTooltipTag(stack) && DLANPPickaxeLootTableModifiers.hasAbilityTooltips(stack);
			boolean displayHoeTooltips     = DLANPHoeLootTableModifiers.hasExternalTooltipTag(stack)     && DLANPHoeLootTableModifiers.hasAbilityTooltips(stack);
			
			// If it has no special tooltips, don't append anything
			if(!displaySwordTooltips && !displayPickaxeTooltips && !displayHoeTooltips) {
				return;
			}
			
			// Add all the ability tooltips to a list
			List<Component> tooltips = new ArrayList<>();
			if (Screen.hasShiftDown()) {
				if(displaySwordTooltips) {
					DLANPSwordLootTableModifiers.appendSwordLootHoverText(stack, null, tooltips, context);
				}
				if(displayPickaxeTooltips) {
					DLANPPickaxeLootTableModifiers.appendPickaxeOreHoverText(stack, null, tooltips, context);
				}
				if(displayHoeTooltips) {
					DLANPHoeLootTableModifiers.appendHoeCropHoverText(stack, null, tooltips, context);
				}
			} else {
				tooltips.add(ModTooltips.SHIFT_KEY_TOOLTIP);
			}

			// Add all the tooltips to the item
			lines.addAll(1, tooltips);
		});
	}
}