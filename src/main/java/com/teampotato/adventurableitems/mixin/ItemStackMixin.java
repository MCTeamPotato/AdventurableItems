package com.teampotato.adventurableitems.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Function;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean hasAdventureModePlaceTagForBlock(TagContainer tagContainer, BlockInWorld block);

    @Redirect(method = "onItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z"))
    private boolean adventurableitems$onCheckPlayerMayBuild(Abilities instance) {
        return false;
    }

    @Inject(method = "onItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void adventurableitems$onItemUse(UseOnContext arg, Function<UseOnContext, InteractionResult> callback, CallbackInfoReturnable<InteractionResult> cir, Player player, BlockPos blockPos, BlockInWorld cachedBlockInfo) {
        if (player != null && !this.hasAdventureModePlaceTagForBlock(arg.getLevel().getTagManager(), cachedBlockInfo)) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
