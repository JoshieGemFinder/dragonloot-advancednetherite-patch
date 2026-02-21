package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import java.util.ArrayList;
import java.util.List;

import com.autovw.advancednetherite.common.ModLootTableModifiers;
import com.autovw.advancednetherite.config.ConfigHelper;
import com.autovw.advancednetherite.core.util.ModTooltips;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DragonLootAdvancedNetheritePatchConfig;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * Original author Autovw
 * Modified version of {@linkplain ModLootTableModifiers}
 */
public final class DLANPSwordLootTableModifiers
{
	private static final ResourceLocation PHANTOM = new ResourceLocation("entities/phantom");
	private static final ResourceLocation ZOMBIFIED_PIGLIN = new ResourceLocation("entities/zombified_piglin");
	private static final ResourceLocation PIGLIN = new ResourceLocation("entities/piglin");
	private static final ResourceLocation ENDERMAN = new ResourceLocation("entities/enderman");
	
	public static final TagKey<Item> EXTERNAL_TOOLTIP = createKey("external_sword_tooltip");
	
	public static final TagKey<Item> ADDITIONAL_PHANTOM_DROPS = createKey("additional_phantom_drops");
	public static final TagKey<Item> ADDITIONAL_PIGLIN_DROPS = createKey("additional_piglin_drops");
	public static final TagKey<Item> ADDITIONAL_ZOMBIFIED_PIGLIN_DROPS = createKey("additional_zombified_piglin_drops");
	public static final TagKey<Item> ADDITIONAL_ENDERMAN_DROPS = createKey("additional_enderman_drops");

	private static TagKey<Item> createKey(String name) {
		return TagKey.create(Registries.ITEM, DragonLootAdvancedNetheritePatch.prefix(name));
	}

	public static void modifyTables()
	{
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) ->
		{
			final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();

			// ADDITIONAL MOB DROPS START //
			if (source.isBuiltin() && id.equals(PHANTOM) && config.dragon_sword_extra_phantom_drops)
			{
				LootPool.Builder pool = mobDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalPhantomDropChance(), Items.PHANTOM_MEMBRANE, 0, 2, ADDITIONAL_PHANTOM_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && id.equals(ZOMBIFIED_PIGLIN) && config.dragon_sword_extra_zombified_piglin_drops)
			{
				LootPool.Builder pool = mobDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalZombifiedPiglinDropChance(), Items.GOLD_NUGGET, 0, 3, ADDITIONAL_ZOMBIFIED_PIGLIN_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && id.equals(PIGLIN) && config.dragon_sword_extra_piglin_drops)
			{
				LootPool.Builder pool = mobDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalPiglinDropChance(), Items.GOLD_INGOT, 1, 1, ADDITIONAL_PIGLIN_DROPS);
				tableBuilder.withPool(pool);
			}

			if (source.isBuiltin() && id.equals(ENDERMAN) && config.dragon_sword_extra_enderman_drops)
			{
				LootPool.Builder pool = mobDropPool((float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalEndermanDropChance(), Items.ENDER_PEARL, 0, 1, ADDITIONAL_ENDERMAN_DROPS);
				tableBuilder.withPool(pool);
			}
			// ADDITIONAL MOB DROPS END //
		}));
		
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			if(stack.getItemHolder().is(EXTERNAL_TOOLTIP)) {
				List<Component> tooltipLines = new ArrayList<>();
				appendSwordLootHoverText(stack, null, tooltipLines, context);
				lines.addAll(1, tooltipLines);
			}
		});
	}
	
	public static void appendSwordLootHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
    	final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
		final boolean extraPhantomDrops         = config.dragon_sword_extra_phantom_drops          && stack.is(ADDITIONAL_PHANTOM_DROPS);
		final boolean extraPiglinDrops          = config.dragon_sword_extra_piglin_drops           && stack.is(ADDITIONAL_PIGLIN_DROPS);
		final boolean extraZombifiedPiglinDrops = config.dragon_sword_extra_zombified_piglin_drops && stack.is(ADDITIONAL_ZOMBIFIED_PIGLIN_DROPS);
		final boolean extraEndermanDrops        = config.dragon_sword_extra_enderman_drops         && stack.is(ADDITIONAL_ENDERMAN_DROPS);
		if ((extraPhantomDrops || extraPiglinDrops || extraZombifiedPiglinDrops || extraEndermanDrops) && ConfigHelper.get().getCommon().getAdditionalDrops().enableAdditionalMobDrops())
		{
			if (Screen.hasShiftDown())
			{
				if (extraPhantomDrops) tooltip.add(ModTooltips.PHANTOM_MOB_DROP_TOOLTIP);
				if (extraPiglinDrops) tooltip.add(ModTooltips.PIGLIN_MOB_DROP_TOOLTIP);
				if (extraZombifiedPiglinDrops) tooltip.add(ModTooltips.ZOMBIFIED_PIGLIN_MOB_DROP_TOOLTIP);
				if (extraEndermanDrops) tooltip.add(ModTooltips.ENDERMAN_MOB_DROP_TOOLTIP);
			}
			else
			{
				tooltip.add(ModTooltips.SHIFT_KEY_TOOLTIP);
			}
		}
	}

	private static LootPool.Builder mobDropPool(float dropChance, Item dropItem, int minDrop, int maxDrop, TagKey<Item> tag)
	{
		ItemPredicate mainHandItemPredicate = new ItemPredicate(tag, null, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY);
		EntityEquipmentPredicate equipmentPredicate = new EntityEquipmentPredicate(ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, mainHandItemPredicate, ItemPredicate.ANY);
		return LootPool.lootPool()
		        .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().equipment(equipmentPredicate)))
		        .when(LootItemRandomChanceCondition.randomChance(dropChance))
		        .add(LootItem.lootTableItem(dropItem))
		        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrop, maxDrop)).build());
	}
}
