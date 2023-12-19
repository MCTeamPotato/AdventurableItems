package com.teampotato.adventurableitems.mixin;

import com.mojang.authlib.GameProfile;
import com.teampotato.adventurableitems.AdventurableItems;
import com.teampotato.adventurableitems.api.Adventurable;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(MinecraftServer server, ServerWorld world, GameProfile profile, CallbackInfo ci) {
        if (AdventurableItems.isInited) return;
        AdventurableItems.isInited = true;
        try {
            for (String itemRegistryName : AdventurableItems.ADVENTURABLE_ITEMS.get()) {
                String[] strings = itemRegistryName.split(":");
                Identifier id = new Identifier(strings[0], strings[1]);
                Item item = Registries.ITEM.get(id);
                ((Adventurable)item).adventurableItems$setIsAdventurable(true);
            }
        } catch (Throwable throwable) {
            AdventurableItems.LOGGER.error("Error occurs when initalizing Adventurable Items config", throwable);
        }
    }
}
