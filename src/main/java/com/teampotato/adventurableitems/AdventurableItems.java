package com.teampotato.adventurableitems;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdventurableItems implements ModInitializer {
	public static boolean isInited;

	public static final Logger LOGGER = LoggerFactory.getLogger(AdventurableItems.class);

	@Override
	public void onInitialize() {
		ForgeConfigRegistry.INSTANCE.register("adventurableitems", ModConfig.Type.COMMON, CONFIG);
	}

	private static final ForgeConfigSpec CONFIG;
	public static final ForgeConfigSpec.ConfigValue<List<? extends String>> ADVENTURABLE_ITEMS;
	public static final ForgeConfigSpec.BooleanValue ALLOW_EVERY_ITEM_USING_IN_ADVENTURE_MODE;

	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		builder.push("AdventurableItems");
		ADVENTURABLE_ITEMS = builder.defineList("ItemsUsableInAdventureMode", new ObjectArrayList<>(), o -> o instanceof String);
		ALLOW_EVERY_ITEM_USING_IN_ADVENTURE_MODE = builder.define("AllowEveryItemUsingInAdventureMode", false);
		builder.pop();
		CONFIG = builder.build();
	}
}