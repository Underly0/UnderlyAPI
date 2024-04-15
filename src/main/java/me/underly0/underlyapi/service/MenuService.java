package me.underly0.underlyapi.service;

import me.underly0.underlyapi.common.menu.MenuImpl;
import me.underly0.underlyapi.common.menu.action.AbstractMenuQuoteAction;
import me.underly0.underlyapi.common.menu.action.IMenuAction;
import me.underly0.underlyapi.common.menu.action.MenuAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import java.util.List;

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

        event.setCancelled(true);

        List<IMenuAction> actions = menu.actionSlots.get(event.getSlot());

        if (actions == null) {
            return;
        }

        actions.forEach(action -> {
            if (action != null) {
                if (action instanceof AbstractMenuQuoteAction) {
                    AbstractMenuQuoteAction quoteAction = (AbstractMenuQuoteAction) action;
                    quoteAction.onAction(player, event.getClick(), quoteAction.getQuote());
                } else {
                    ((MenuAction) action).onAction(player, event.getClick());
                }
            }
        });

    }
}
