package me.underly0.underlyapi.common.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

@FunctionalInterface
public interface MenuAction {
    void onAction(Player player, ClickType clickType);
}