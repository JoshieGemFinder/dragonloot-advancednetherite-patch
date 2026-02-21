package com.joshiegemfinder.dragonlootadvancednetheritepatch.compat;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class DLANPCompatMixinPlugin implements IMixinConfigPlugin {

//	public static final Logger LOGGER = LoggerFactory.getLogger("dragon_loot_advanced_netherite_patch_mixin_plugin");
	
	@Override
	public void onLoad(String mixinPackage) {
		// NO-OP
//		System.out.println("Loaded Dragon Loot Advanced Netherite Patch mixin plugin");
//		LOGGER.info("Loaded Dragon Loot Advanced Netherite Patch mixin plugin");
	}

	@Override
	public String getRefMapperConfig() {
		// Use default
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
//		System.out.println("shouldApplyMixin for mixin %s for target class %s".formatted(mixinClassName, targetClassName));
//		LOGGER.info("shouldApplyMixin for mixin %s for target class %s".formatted(mixinClassName, targetClassName));
		if(
				Objects.equals(mixinClassName, "com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat.MWCompatRecipesMixin") ||
				Objects.equals(mixinClassName, "com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat.MWRecipeManagerFallbackMixin") ||
				Objects.equals(mixinClassName, "com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat.MWCompatItemsMixin")
			) {
			return AdvancedNetheriteMedievalWeaponsCompat.MedievalWeaponsCompatMixinAccess.MODS_LOADED;
		}
		return false;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
		// NO-OP
	}

	@Override
	public List<String> getMixins() {
		// Use default
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
		// NO-OP
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
//		System.out.println("Applied mixin %s for target class %s".formatted(mixinClassName, targetClassName));
//		LOGGER.info("Applied mixin %s for target class %s".formatted(mixinClassName, targetClassName));
		if(Objects.equals(mixinClassName, "com.joshiegemfinder.dragonlootadvancednetheritepatch.mixin.compat.MWCompatRecipesMixin")) {
			AdvancedNetheriteMedievalWeaponsCompat.MedievalWeaponsCompatMixinAccess.compatRecipesMixinApplied = true;
		}
	}

}
