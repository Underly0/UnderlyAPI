package me.underly0.underlyapi.impl.inventory;

import com.google.common.collect.Sets;
import lombok.Getter;
import me.underly0.underlyapi.impl.inventory.MenuImpl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.underly0.underlyapi.api.menu.Menu;
import me.underly0.underlyapi.api.menu.item.Item;
import me.underly0.underlyapi.impl.inventory.item.ItemImpl;
import me.underly0.underlyapi.api.menu.PagedMenu;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PagedMenuImpl implements PagedMenu {

    @Getter
    private List<Menu> inventories = new ArrayList<>();

    @Getter
    private Map<Item, Set<Integer>> staticItems = new ConcurrentHashMap<>();

    private final String name;
    private final int rows;

    public PagedMenuImpl(String name, int rows) {
        this.name = name;
        this.rows = rows;
    }

    @Override
    public PagedMenu addPages(int pages) {
        for (int i = 0; i < inventories.size(); i++) {
            Menu inv = inventories.get(i);
//            inv.setTitle(title.substring(0, title.lastIndexOf(" "))
//                    + String.format(" %d/%d", i + 1, inventories.size() + 1));
        }

        int pagesSize = inventories.size();
        for (int i = 0; i < pages; i++) {
            Menu last = new MenuImpl()
                    .createInventory(name + String.format(" %d/%d", inventories.size() + 1,
                            pagesSize + pages), rows);
            last.addItems(staticItems);

            inventories.add(last);
        }
        return this;
    }

    @Override
    public PagedMenu fillBorder(Item item) {
        Set<Integer> slots = Sets.newHashSet();

        int size = (rows * 9) / 9;
        for (int i = 0; i < size; ++i) {
            int row = i / 9;
            int col = i % 9;

            if (col == 0 || col == 8 || row == 0
                    || row == size - 1) {
                slots.add(i);
            }
        }

        putIf(item, slots);
        return this;
    }

    @Override
    public PagedMenu setNextPage(ItemStack item, int... slots) {
        for (int i = 0; i < inventories.size(); i++) {

            if (i == inventories.size() - 1)
                continue;

            Menu inv = inventories.get(i);
            Menu next = inventories.get(i + 1);
            inv.addItem(new ItemImpl(item, (player, type, slot) -> next.open(player)),
                    Arrays.stream(slots).boxed().collect(Collectors.toSet()));
        }
        return this;
    }

    @Override
    public PagedMenu setBackPage(ItemStack item, int... slots) {
        for (int i = 0; i < inventories.size(); i++) {

            if (i == 0)
                continue;

            Menu inv = inventories.get(i);
            Menu back = inventories.get(i - 1);
            inv.addItem(new ItemImpl(item, (player, type, slot) -> back.open(player)),
                    Arrays.stream(slots).boxed().collect(Collectors.toSet()));
        }
        return this;
    }

    @Override
    public void open(Player player, int page) {
        if (inventories.size() < page)
            throw new NullPointerException("The page was not found");

        inventories.get(page).open(player);
    }

    @Override
    public PagedMenu addStaticItems(Map<Item, Set<Integer>> items) {
        putAllIf(staticItems);
        return this;
    }

    @Override
    public PagedMenu addStaticItem(Item item, Set<Integer> slots) {
        putIf(item, slots);
        return this;
    }

    @Override
    public PagedMenu addStaticItem(Item item, int... slot) {
        putIf(item, Arrays.stream(slot).boxed().collect(Collectors.toSet()));
        return this;
    }

    @Override
    public Menu getPage(int page) {
        return inventories.get(page);
    }

    private void putAllIf(Map<Item, Set<Integer>> items) {
        items.forEach(this::putIf);
    }

    private void putIf(Item item, Set<Integer> slots) {
        staticItems.values().removeIf(values -> !Collections.disjoint(values, slots));
        staticItems.put(item, slots);
    }
}
