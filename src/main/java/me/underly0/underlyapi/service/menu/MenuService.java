package me.underly0.underlyapi.service.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import me.underly0.underlyapi.api.menu.Menu;
import me.underly0.underlyapi.api.menu.item.Item;
import me.underly0.underlyapi.api.menu.listener.MenuListener;

public class MenuService implements MenuListener {

    public MenuService(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    public Menu getMenu(InventoryView view) {
        Inventory inv = view.getTopInventory();
        return inv == null ? null : !(inv.getHolder() instanceof Menu) ? null : (Menu) inv.getHolder();
    }

    @Override
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = getMenu(event.getView());

        if (menu == null) return;
        event.setCancelled(true);

        Item item = menu.findItemBySlot(event.getSlot());
        if (item == null) return;

        if (item.getAction() != null) {
            item.getAction().function(player, event.getClick(), event.getSlot());
        }
        player.updateInventory();

    }

    @Override
    @EventHandler
    public void onDrag(InventoryDragEvent event) {

    }

    @Override
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
    }
}
