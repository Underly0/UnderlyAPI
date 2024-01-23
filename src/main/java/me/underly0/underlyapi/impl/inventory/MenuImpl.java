package me.underly0.underlyapi.impl.inventory;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.Menu;
import me.underly0.underlyapi.api.menu.item.Item;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {
    private Inventory inventory;
    @Getter
    private final Map<Item, Set<Integer>> items = new ConcurrentHashMap<>();

    @Override
    public Menu createInventory() {
        inventory = Bukkit.createInventory(this, 3 * 9);
        return this;
    }

    @Override
    public Menu createInventory(String title, int rows) {
        inventory = Bukkit.createInventory(this, rows * 9, title);
        return this;
    }

    @Override
    public Menu createInventory(String title, InventoryType type) {
        inventory = Bukkit.createInventory(this, type, title);
        return this;
    }
    @Override
    public Menu createInventory(InventoryHolder holder, String title, int rows) {
        inventory = Bukkit.createInventory(holder, rows * 9, title);
        return this;
    }

    @Override
    public int getSize() {
        return inventory.getSize();
    }

    @Override
    public ItemStack[] getContents() {
        return inventory.getContents();
    }

    @Override
    public Menu addItem(Item item, int... slot) {
        addItem(item, Arrays.stream(slot).boxed().collect(Collectors.toSet()));
        return this;
    }
    @Override
    public Menu addItem(Item item, Set<Integer> slots) {
        setItemsInventory(ImmutableMap.of(item, slots));
        putAllIf(ImmutableMap.of(item, slots));
        return this;
    }

    @Override
    public Menu addItems(Map<Item, Set<Integer>> items) {
        setItemsInventory(items);
        putAllIf(items);
        return this;
    }

    @Override
    public Menu setItems(Map<Item, Set<Integer>> items) {
        setItemsInventory(items);
        this.items.clear();
        this.items.putAll(items);
        return this;
    }

    private void setItemsInventory(Map<Item, Set<Integer>> items) {
        items.forEach((cItem, slots) -> slots.forEach(slot -> inventory.setItem(slot, cItem.getItem())));
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public Item findItemBySlot(int slot) {
        return items.entrySet().stream()
                .filter(entry -> entry.getValue().contains(slot))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    @Override
    public Menu setTitle(String name) {
        Inventory tmp = Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), name);
        ItemStack[] contents = inventory.getContents();
        tmp.setContents(contents);
        inventory = tmp;
        return null;
    }

    @Override
    public Menu fillBorder(Item item) {
        Set<Integer> slots = new HashSet<>();

        int size = inventory.getSize();
        for (int i = 0; i < size; ++i) {
            int row = i / 9;
            int col = i % 9;

            if (col == 0 || col == 8 || row == 0
                    || row == size / 9 - 1) {
                inventory.setItem(i, item.getItem());
                slots.add(i);
            }
        }

        putIf(item, slots);
        return this;
    }

    @Override
    public Menu fillAll(Item item) {
        Set<Integer> slots = new HashSet<>();

        for (int i = 0; i < inventory.getSize(); ++i) {
            inventory.setItem(i, item.getItem());
            slots.add(i);
        }

        putIf(item, slots);
        return this;
    }

    private void putAllIf(Map<Item, Set<Integer>> items) {
        items.forEach(this::putIf);
    }

    private void putIf(Item item, Set<Integer> slots) {
        items.values().removeIf(values -> !Collections.disjoint(values, slots));
        items.put(item, slots);
    }
}
