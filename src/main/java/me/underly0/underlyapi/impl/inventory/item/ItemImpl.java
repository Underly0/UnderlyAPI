package me.underly0.underlyapi.impl.inventory.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.item.Item;
import me.underly0.underlyapi.api.menu.actions.ClickAction;

@Getter
public class ItemImpl implements Item {

    private final ItemStack item;
    private ClickAction action;
    public ItemImpl(ItemStack item) {
        this.item = item;
    }
    public ItemImpl(ItemStack item, ClickAction action) {
        this.item = item;
        this.action = action;
    }

}
