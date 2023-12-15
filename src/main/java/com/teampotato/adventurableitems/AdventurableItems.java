package com.teampotato.adventurableitems;

import com.teampotato.adventurableitems.api.Adventurable;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod(AdventurableItems.MOD_ID)
public class AdventurableItems {
    public static final String MOD_ID = "adventurableitems";

    public AdventurableItems() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent event) -> event.enqueueWork(() -> {
            for (Item item : ForgeRegistries.ITEMS) {
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                if (id == null) continue;
                ((Adventurable)item).adventurableItems$setIsAdventurable(ADVENTURABLE_ITEMS.get().contains(id.toString()));
            }
        }));
    }

    private static final ForgeConfigSpec CONFIG;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ADVENTURABLE_ITEMS;
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
