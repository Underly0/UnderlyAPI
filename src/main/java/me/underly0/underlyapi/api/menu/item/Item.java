package me.underly0.underlyapi.api.menu.item;

import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.actions.ClickAction;

public interface Item {

    ItemStack getItem();
    ClickAction getAction();
//    default boolean isDraggable() {
//        return false;
//    }
//    Item setDraggable(boolean draggable);
}
