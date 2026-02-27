package com.joshiegemfinder.dragonlootadvancednetheritepatch;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.autovw.advancednetherite.common.ModLootTableModifiers;
import com.autovw.advancednetherite.config.ConfigHelper;
import com.autovw.advancednetherite.core.util.ModTooltips;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DLANPConfigHolder;
import com.joshiegemfinder.dragonlootadvancednetheritepatch.config.DragonLootAdvancedNetheritePatchConfig;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * Original author Autovw
 * Modified version of {@linkplain ModLootTableModifiers}
 */
public final class DLANPHoeLootTableModifiers
{
	private static final ResourceLocation WHEAT = new ResourceLocation("blocks/wheat");
	private static final ResourceLocation CARROTS = new ResourceLocation("blocks/carrots");
	private static final ResourceLocation POTATOES = new ResourceLocation("blocks/potatoes");
	private static final ResourceLocation BEETROOTS = new ResourceLocation("blocks/beetroots");

	public static final TagKey<Item> EXTERNAL_TOOLTIP = createKey("external_hoe_tooltip");
	
	public static final TagKey<Item> ADDITIONAL_CROP_DROPS = createKey("additional_crop_drops");
	public static final TagKey<Item> DOUBLE_CROP_DROPS = createKey("double_crop_drops");

	private static TagKey<Item> createKey(String name) {
		return TagKey.create(Registries.ITEM, DragonLootAdvancedNetheritePatch.prefix(name));
	}

	public static void modifyTables()
	{
		LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) ->
		{
			final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
			
			// ADDITIONAL CROP DROPS START //
			if(config.dragon_hoe_extra_crop_drops) {
				addAdditionalCropDrops(resourceManager, lootManager, id, tableBuilder, source);
			}
			// ADDITIONAL CROP DROPS END //
		}));
		
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if(player.getMainHandItem().is(DOUBLE_CROP_DROPS) && DLANPConfigHolder.get().dragon_hoe_double_crop_drops) {
				addDoubleCropDrops(world, player, pos, state, blockEntity);
			}
		});
	}
	
	public static boolean hasExternalTooltipTag(ItemStack stack) {
		return stack.is(EXTERNAL_TOOLTIP);
	}
	
	public static boolean hasAbilityTooltips(ItemStack stack) {
		if(ConfigHelper.get().getCommon().getAdditionalDrops().enableAdditionalCropDrops()) {
	    	final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
			final boolean extraCropDrops  = config.dragon_hoe_extra_crop_drops  && stack.is(ADDITIONAL_CROP_DROPS);
			final boolean doubleCropDrops = config.dragon_hoe_double_crop_drops && stack.is(DOUBLE_CROP_DROPS);
			return extraCropDrops || doubleCropDrops;
		}
		return false;
	}
	
	public static void appendHoeCropHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flag) {
    	final DragonLootAdvancedNetheritePatchConfig config = DLANPConfigHolder.get();
		final boolean extraCropDrops  = config.dragon_hoe_extra_crop_drops  && stack.is(ADDITIONAL_CROP_DROPS);
		final boolean doubleCropDrops = config.dragon_hoe_double_crop_drops && stack.is(DOUBLE_CROP_DROPS);
		if ((extraCropDrops || doubleCropDrops) && ConfigHelper.get().getCommon().getAdditionalDrops().enableAdditionalCropDrops())
		{
			if (Screen.hasShiftDown())
			{
				if (extraCropDrops) tooltip.add(ModTooltips.ADDITIONAL_CROP_DROPS_TOOLTIP);
				if (doubleCropDrops) tooltip.add(DragonLootAdvancedNetheritePatch.DOUBLE_CROP_DROPS_TOOLTIP);
			}
			else
			{
				tooltip.add(ModTooltips.SHIFT_KEY_TOOLTIP);
			}
		}
	}
	
	private static void addAdditionalCropDrops(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) {
		if (source.isBuiltin() && id.equals(WHEAT))
		{
			LootPool.Builder pool = cropDropPool(Blocks.WHEAT, BlockStateProperties.AGE_7, (float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalWheatDropChance(), Items.WHEAT, 0, 2, ADDITIONAL_CROP_DROPS);
			tableBuilder.withPool(pool);
		}

		if (source.isBuiltin() && id.equals(CARROTS))
		{
			LootPool.Builder pool = cropDropPool(Blocks.CARROTS, BlockStateProperties.AGE_7, (float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalCarrotsDropChance(), Items.CARROT, 0, 2, ADDITIONAL_CROP_DROPS);
			tableBuilder.withPool(pool);
		}

		if (source.isBuiltin() && id.equals(POTATOES))
		{
			LootPool.Builder pool = cropDropPool(Blocks.POTATOES, BlockStateProperties.AGE_7, (float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalPotatoesDropChance(), Items.POTATO, 0, 1, ADDITIONAL_CROP_DROPS);
			tableBuilder.withPool(pool);
		}

		if (source.isBuiltin() && id.equals(BEETROOTS))
		{
			LootPool.Builder pool = cropDropPool(Blocks.BEETROOTS, BlockStateProperties.AGE_3, (float) ConfigHelper.get().getServer().getAdditionalDropProperties().getAdditionalBeetrootsDropChance(), Items.BEETROOT, 1, 2, ADDITIONAL_CROP_DROPS);
			tableBuilder.withPool(pool);
		}
	}
	
	private static void addDoubleCropDrops(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
		if(state.getBlock() instanceof CropBlock cropBlock && cropBlock.getAge(state) >= cropBlock.getMaxAge()) {
			Block.dropResources(state, world, pos, blockEntity);
		}
	}

	private static LootPool.Builder cropDropPool(Block cropBlock, Property<?> ageProperty, float dropChance, ItemLike dropItem, int minDrop, int maxDrop, TagKey<Item> tag)
	{
		String maxAge = String.valueOf(((CropBlock) cropBlock).getMaxAge());
		return LootPool.lootPool()
		        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cropBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ageProperty, maxAge)))
		        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(tag)))
		        .when(LootItemRandomChanceCondition.randomChance(dropChance))
		        .add(LootItem.lootTableItem(dropItem))
		        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrop, maxDrop)).build());
	}
}
