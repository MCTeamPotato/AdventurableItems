package com.teampotato.adventurableitems.mixin;

import com.teampotato.adventurableitems.AdventurableItems;
import com.teampotato.adventurableitems.api.Adventurable;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ForgeHooks.class)
public abstract class ForgeHooksMixin {
    @Redirect(method = "onPlaceItemIntoWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z"))
    private static boolean adventurableitems$onCheckPlayerMayBuild(Abilities instance) {
        return true;
    }

    @Inject(method = "onPlaceItemIntoWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z", shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void adventurableitems$onPlaceItemIntoWorld(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir, ItemStack itemstack, Level world, Player player) {
        if (player != null && !itemstack.hasAdventureModePlaceTagForBlock(world.registryAccess().registryOrThrow(Registry.BLOCK_REGISTRY), new BlockInWorld(world, context.getClickedPos(), false))) {
            if (AdventurableItems.ALLOW_EVERY_ITEM_USING_IN_ADVENTURE_MODE.get()) return;
            if (((Adventurable)itemstack.getItem()).adventurableItems$isAdventurable()) return;
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
