package com.teampotato.adventurableitems.mixin;

import com.teampotato.adventurableitems.AdventurableItems;
import com.teampotato.adventurableitems.api.Adventurable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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


    @Shadow public abstract Item getItem();

    @Shadow public abstract boolean hasAdventureModePlaceTagForBlock(TagContainer tagContainer, BlockInWorld block);

    @Redirect(method = "onItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z"))
    private boolean adventurableitems$onCheckPlayerMayBuild(Abilities instance) {
        return true;
    }

    @Inject(method = "onItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;mayBuild:Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void adventurableitems$onItemUse(UseOnContext arg, Function<UseOnContext, InteractionResult> callback, CallbackInfoReturnable<InteractionResult> cir, Player player, BlockPos blockPos, BlockInWorld cachedBlockInfo) {
        if (player != null && !player.abilities.mayBuild && !this.hasAdventureModePlaceTagForBlock(arg.getLevel().getTagManager(), cachedBlockInfo)) {
            if (AdventurableItems.ALLOW_EVERY_ITEM_USING_IN_ADVENTURE_MODE.get()) return;
            if (((Adventurable)this.getItem()).adventurableItems$isAdventurable()) return;
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
