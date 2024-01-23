package me.underly0.underlyapi.api.menu.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public interface MenuListener extends Listener {
    void onClick(InventoryClickEvent event);
    void onDrag(InventoryDragEvent event);
    void onClose(InventoryCloseEvent event);
}
