package me.underly0.underlyapi.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.item.Item;

import java.util.Map;
import java.util.Set;

public interface Menu extends InventoryHolder {

    Menu createInventory();
    Menu createInventory(String title, int size);
    Menu createInventory(String title, InventoryType type);
    Menu createInventory(InventoryHolder holder, String title, int rows);
    int getSize();
    ItemStack[] getContents();
    Map<Item, Set<Integer>> getItems();
    Menu addItem(Item item, int... slots);
    Menu addItem(Item item, Set<Integer> slots);
    Menu addItems(Map<Item, Set<Integer>> items);
    Menu setItems(Map<Item, Set<Integer>> items);
    void open(Player player);
    Item findItemBySlot(int slot);
    Menu setTitle(String name);
    Menu fillBorder(Item item);
    Menu fillAll(Item item);

}
