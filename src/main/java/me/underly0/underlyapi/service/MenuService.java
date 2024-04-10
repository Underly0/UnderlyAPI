package me.underly0.underlyapi.service;

import me.underly0.underlyapi.common.menu.MenuAction;
import me.underly0.underlyapi.common.menu.MenuImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

public class MenuService implements Listener {

    public MenuService(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    public MenuImpl getMenu(InventoryView view) {
        Inventory inv = view.getTopInventory();
        return !(inv.getHolder() instanceof MenuImpl) ? null : (MenuImpl) inv.getHolder();
    }

    @EventHandler()
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        MenuImpl menu = getMenu(event.getView());

        if (menu == null) {
            return;
        }

        MenuAction action = menu.actionSlots.get(event.getSlot());

        if (action != null) {
            action.onAction(player, event.getClick());
        }

        event.setCancelled(true);
    }
}
