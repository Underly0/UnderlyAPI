package me.underly0.underlyapi.api.menu.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;


public interface MenuQuoteAction extends IMenuAction {
     void onAction(Player player, ClickType clickType, String quote);
}