package me.underly0.underlyapi.api.menu;

import me.underly0.underlyapi.common.menu.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface PagedMenu {

    PagedMenu setBackItem(ItemStack item);

    PagedMenu setNextItem(ItemStack item);

    void setCustomItems(int page, List<CustomItem> items, String type);

    void open(Player target);
    PagedMenu build();
}
