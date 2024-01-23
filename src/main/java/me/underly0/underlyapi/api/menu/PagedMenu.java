package me.underly0.underlyapi.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.item.Item;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PagedMenu {
    List<Menu> getInventories();
    Menu getPage(int page);
    PagedMenu addPages(int page);
    void open(Player player, int page);
    PagedMenu addStaticItems(Map<Item, Set<Integer>> items);
    PagedMenu addStaticItem(Item item, Set<Integer> slots);
    PagedMenu addStaticItem(Item item, int... slot);
    Map<Item, Set<Integer>> getStaticItems();
    PagedMenu setNextPage(ItemStack item, int... slots);
    PagedMenu setBackPage(ItemStack item, int... slots);
    PagedMenu fillBorder(Item item);
}
