package com.teampotato.adventurableitems.mixin;

import com.teampotato.adventurableitems.api.Adventurable;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.class)
public class ItemMixin implements Adventurable {
    @Unique
    private boolean adventurableItems$isAdventurable;
    @Override
    public boolean adventurableItems$isAdventurable() {
        return this.adventurableItems$isAdventurable;
    }

    @Override
    public void adventurableItems$setIsAdventurable(boolean isAdventurable) {
        this.adventurableItems$isAdventurable = isAdventurable;
    }
}
