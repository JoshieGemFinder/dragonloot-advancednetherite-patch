package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import java.util.List;

import com.autovw.advancednetherite.common.ModLootTableModifiers;
import com.autovw.advancednetherite.config.ConfigHelper;
import com.autovw.advancednetherite.core.util.ModTooltips;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DragonLootAdvancedNetheritePatchConfig;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * Original author Autovw
 * Modified version of {@linkplain ModLootTableModifiers}
 */
public final class DLANPPickaxeLootTableModifiers
{
	private static final ResourceLocation IRON_ORE = new ResourceLocation("blocks/iron_ore");
	private static final ResourceLocation DEEPSLATE_IRON_ORE = new ResourceLocation("blocks/deepslate_iron_ore");
	private static final ResourceLocation GOLD_ORE = new ResourceLocation("blocks/gold_ore");
	private static final ResourceLocation DEEPSLATE_GOLD_ORE = new ResourceLocation("blocks/deepslate_gold_ore");
	private static final ResourceLocation EMERALD_ORE = new ResourceLocation("blocks/emerald_ore");
	private static final ResourceLocation DEEPSLATE_EMERALD_ORE = new ResourceLocation("blocks/deepslate_emerald_ore");
	private static final ResourceLocation DIAMOND_ORE = new ResourceLocation("blocks/diamond_ore");
	private static final ResourceLocation DEEPSLATE_DIAMOND_ORE = new ResourceLocation("blocks/deepslate_diamond_ore");
	private static final ResourceLocation NETHER_GOLD_ORE = new ResourceLocation("blocks/netherite_gold_ore");

	public static final TagKey<Item> EXTERNAL_TOOLTIP = createKey("external_pickaxe_tooltip");
	
	public static final TagKey<Item> ADDITIONAL_IRON_DROPS = createKey("additional_iron_drops");
	public static final TagKey<Item> ADDITIONAL_GOLD_DROPS = createKey("additional_gold_drops");
	public static final TagKey<Item> ADDITIONAL_EMERALD_DROPS = createKey("additional_emerald_drops");
	public static final TagKey<Item> ADDITIONAL_DIAMOND_DROPS = createKey("additional_diamond_drops");

	private static TagKey<Item> createKey(String name) {
		return TagKey.create(Registries.ITEM, DragonLootAdvancedNetheritePatch.prefix(name));
	}

	public static void modifyTables()
	{
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) ->
		{
			final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
			
			// ADDITIONAL ORE DROPS START //
			if (source.isBuiltin() && (id.equals(IRON_ORE) || id.equals(DEEPSLATE_IRON_ORE)) && config.dragon_pickaxe_extra_iron)
			{
				LootPool.Builder pool = oreDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalRawIronDropChance(), Items.RAW_IRON, 1, 2, ADDITIONAL_IRON_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && (id.equals(GOLD_ORE) || id.equals(DEEPSLATE_GOLD_ORE)) && config.dragon_pickaxe_extra_gold)
			{
				LootPool.Builder pool = oreDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalRawGoldDropChance(), Items.RAW_GOLD, 1, 1, ADDITIONAL_GOLD_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && (id.equals(EMERALD_ORE) || id.equals(DEEPSLATE_EMERALD_ORE)) && config.dragon_pickaxe_extra_emerald)
			{
				LootPool.Builder pool = oreDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalEmeraldDropChance(), Items.EMERALD, 1, 1, ADDITIONAL_EMERALD_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && (id.equals(DIAMOND_ORE) || id.equals(DEEPSLATE_DIAMOND_ORE)) && config.dragon_pickaxe_extra_diamond)
			{
				LootPool.Builder pool = oreDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalDiamondDropChance(), Items.DIAMOND, 1, 1, ADDITIONAL_DIAMOND_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && id.equals(NETHER_GOLD_ORE) && config.dragon_pickaxe_extra_gold)
			{
				LootPool.Builder pool = oreDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalGoldNuggetDropChance(), Items.GOLD_NUGGET, 1, 3, ADDITIONAL_GOLD_DROPS);
				tableBuilder.withPool(pool);
			}
			// ADDITIONAL ORE DROPS END //
		}));
	}
	
	public static boolean hasExternalTooltipTag(ItemStack stack) {
		return stack.is(EXTERNAL_TOOLTIP);
	}
	
	public static boolean hasAbilityTooltips(ItemStack stack) {
		if(ConfigHelper.get().getCommon().getAdditionalDrops().enableAdditionalOreDrops()) {
	    	final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
			final boolean extraIronDrops    = config.dragon_pickaxe_extra_iron    && stack.is(ADDITIONAL_IRON_DROPS);
			final boolean extraGoldDrops    = config.dragon_pickaxe_extra_gold    && stack.is(ADDITIONAL_GOLD_DROPS);
			final boolean extraEmeraldDrops = config.dragon_pickaxe_extra_emerald && stack.is(ADDITIONAL_EMERALD_DROPS);
			final boolean extraDiamondDrops = config.dragon_pickaxe_extra_diamond && stack.is(ADDITIONAL_DIAMOND_DROPS);
			return extraIronDrops || extraGoldDrops || extraEmeraldDrops || extraDiamondDrops;
		}
		return false;
	}
	
	public static void appendPickaxeOreHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
    	final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
		final boolean extraIronDrops    = config.dragon_pickaxe_extra_iron    && stack.is(ADDITIONAL_IRON_DROPS);
		final boolean extraGoldDrops    = config.dragon_pickaxe_extra_gold    && stack.is(ADDITIONAL_GOLD_DROPS);
		final boolean extraEmeraldDrops = config.dragon_pickaxe_extra_emerald && stack.is(ADDITIONAL_EMERALD_DROPS);
		final boolean extraDiamondDrops = config.dragon_pickaxe_extra_diamond && stack.is(ADDITIONAL_DIAMOND_DROPS);
		if ((extraIronDrops || extraGoldDrops || extraEmeraldDrops || extraDiamondDrops) && ConfigHelper.get().getCommon().getAdditionalDrops().enableAdditionalOreDrops())
		{
			if (Screen.hasShiftDown())
			{
				if (extraIronDrops) tooltip.add(ModTooltips.IRON_ORE_DROP_TOOLTIP);
				if (extraGoldDrops) tooltip.add(ModTooltips.GOLD_ORE_DROP_TOOLTIP);
				if (extraEmeraldDrops) tooltip.add(ModTooltips.EMERALD_ORE_DROP_TOOLTIP);
				if (extraDiamondDrops) tooltip.add(ModTooltips.DIAMOND_ORE_DROP_TOOLTIP);
			}
			else
			{
				tooltip.add(ModTooltips.SHIFT_KEY_TOOLTIP);
			}
		}
	}
	
	private static LootPool.Builder oreDropPool(float dropChance, Item dropItem, int minDrop, int maxDrop, TagKey<Item> tag)
	{
		return LootPool.lootPool()
		        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(tag)))
		        .when(InvertedLootItemCondition.invert(
		                MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.ANY)))
		        ))
		        .when(LootItemRandomChanceCondition.randomChance(dropChance))
		        .add(LootItem.lootTableItem(dropItem))
		        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrop, maxDrop)).build());
	}
}
