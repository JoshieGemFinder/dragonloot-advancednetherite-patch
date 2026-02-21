package com.joshiegemfinder.dragonlootadvancednetheritepatch.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class DLANPConfigHolder {

	private static ConfigHolder<DragonLootAdvancedNetheritePatchConfig> HOLDER;
	private static DragonLootAdvancedNetheritePatchConfig INSTANCE;
	private static boolean configRegistered = false;
	
	private static void registerConfig() {
		HOLDER = AutoConfig.register(DragonLootAdvancedNetheritePatchConfig.class, JanksonConfigSerializer::new);
        updateConfig();
        configRegistered = true;
	}
	
	public static void updateConfig() {
		HOLDER.load();
		INSTANCE = HOLDER.getConfig();
	}
	
	public static DragonLootAdvancedNetheritePatchConfig get() {
		if(!configRegistered) {
			registerConfig();
		}
		return INSTANCE;
	}
	
	public static void initializeConfig() {
		if(!configRegistered) {
			registerConfig();
		}
        
        // Register reload listener to 
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new DLANPConfigResourceReloadListener());
	}
	
}
