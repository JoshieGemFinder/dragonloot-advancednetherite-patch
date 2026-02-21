package com.joshiegemfinder.dragonlootadvancednetheritepatch.config;

import com.joshiegemfinder.dragonlootadvancednetheritepatch.DragonLootAdvancedNetheritePatch;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class DLANPConfigResourceReloadListener implements SimpleSynchronousResourceReloadListener {

	public static final ResourceLocation ID = DragonLootAdvancedNetheritePatch.prefix("config_reloader");
	
	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		DLANPConfigHolder.updateConfig();
	}

}
