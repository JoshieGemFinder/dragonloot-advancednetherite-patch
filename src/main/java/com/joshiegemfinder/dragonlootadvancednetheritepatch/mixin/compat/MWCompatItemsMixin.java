package com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import com.joshiegemfinder.dragonlootadvancednetheritepatch.compat.AdvancedNetheriteMedievalWeaponsCompat;

import net.medievalweapons.compat.CompatItems;

@Mixin(value = CompatItems.class, remap = false)
public class MWCompatItemsMixin {

	@ModifyArg(
			method = "loadItems()V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/medievalweapons/item/Healing_Staff_Item;<init>(Lnet/minecraft/world/item/Tier;IFILnet/minecraft/world/item/Item$Properties;)V",
					ordinal = 0
//					,
//					remap = true
				),
			index = 3,
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "net.medievalweapons.compat.CompatItems.isDragonLootLoaded:Ljava/lang/Boolean;", 
							opcode = Opcodes.GETSTATIC
						)
				)
//			,
//			remap = true
		)
	private static int modifyDragonHealingStaffAddition(int originalAddition) {
		if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			return 8;
		}
		return originalAddition;
	}


	@ModifyArg(
			method = "loadItems()V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/medievalweapons/item/Mace_Item;<init>(Lnet/minecraft/world/item/Tier;IFILnet/minecraft/world/item/Item$Properties;)V",
					ordinal = 0,
					remap = true
				),
			index = 3,
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "net.medievalweapons.compat.CompatItems.isDragonLootLoaded:Ljava/lang/Boolean;", 
							opcode = Opcodes.GETSTATIC
						)
				),
			remap = false
		)
	private static int modifyDragonMaceAddition(int originalAddition) {
		if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			return 7;
		}
		return originalAddition;
	}


	@ModifyArg(
			method = "loadItems()V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/medievalweapons/item/Rapier_Item;<init>(Lnet/minecraft/world/item/Tier;IFILnet/minecraft/world/item/Item$Properties;)V",
					ordinal = 0,
					remap = true
				),
			index = 3,
			slice = @Slice(
					from = @At(
							value = "FIELD",
							target = "net.medievalweapons.compat.CompatItems.isDragonLootLoaded:Ljava/lang/Boolean;", 
							opcode = Opcodes.GETSTATIC
						)
				),
			remap = false
		)
	private static int modifyDragonRapierAddition(int originalAddition) {
		if(AdvancedNetheriteMedievalWeaponsCompat.isEnabled()) {
			return 5;
		}
		return originalAddition;
	}

}
