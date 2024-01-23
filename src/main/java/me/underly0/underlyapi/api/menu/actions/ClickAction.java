package me.underly0.underlyapi.api.menu.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ClickAction {
    void function(Player player, ClickType type, int slot);
}
