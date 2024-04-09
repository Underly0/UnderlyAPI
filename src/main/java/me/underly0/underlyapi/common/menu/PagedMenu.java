package me.underly0.underlyapi.common.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PagedMenu {

    private final List<Menu> pages = new ArrayList<>();
    private int page = 0;
    private int pageSize;
    private ItemStack backItem = new ItemStack(Material.BARRIER);
    private ItemStack nextItem = new ItemStack(Material.BARRIER);

    public PagedMenu(Menu menu) {
        init(menu, 1);
    }

    public PagedMenu(Menu menu, int pageSize) {
        init(menu, pageSize);
    }

    public PagedMenu(Menu menu, List<CustomItem> items, String type) {
        List<Integer> slots = new ArrayList<>();
        menu.getTypeItems(type).values().forEach(slots::addAll);

        int pageSize = (int) Math.ceil((double) items.size() / slots.size());
        if (pageSize < 1)
            pageSize = 1;

        init(menu, pageSize);
        setCustomItems(items, type);
    }

    private void init(Menu menu, int pageSize) {
        this.pageSize = pageSize;

        for (int i = 0; i < pageSize; i++) {
            pages.add(menu.cloneMenu());
        }
    }

    public PagedMenu build() {
        for (int i = 0; i < this.pageSize; i++) {
            if (i < this.pageSize - 1) {
                setNextItemToMenu(this.pages.get(i));
            }
            if (i > 0) {
                setBackItemToMenu(this.pages.get(i));
            }
        }
        return this;
    }

    private void setBackItemToMenu(Menu menu) {
        setPagedItemToMenu(menu, "back", backItem, (player, clickType) -> openBackPage(player));
    }

    private void setNextItemToMenu(Menu menu) {
        setPagedItemToMenu(menu, "next", nextItem, (player, clickType) -> openNextPage(player));
    }

    private void setPagedItemToMenu(Menu menu, String type, ItemStack item, MenuAction action) {
        menu.getTypeItems(type)
                .values()
                .forEach(list
                        -> list.forEach(slot
                        -> menu.setCustomItem(new CustomItem(item, action, slot)))
                );
    }

    private void openNextPage(Player player) {
        this.page++;

        if (this.page > pages.size() - 1)
            return;

        pages.get(this.page).open(player);
    }

    private void openBackPage(Player player) {
        this.page--;

        if (this.page < 0)
            return;

        pages.get(this.page).open(player);
    }

    public PagedMenu setBackItem(ItemStack item) {
        this.backItem = item;
        return this;
    }

    public PagedMenu setNextItem(ItemStack item) {
        this.nextItem = item;
        return this;
    }

    private void setCustomItems(List<CustomItem> items, String type) {
        setCustomItems(0, items, type);
    }

    public void setCustomItems(int page, List<CustomItem> items, String type) {
        if (this.pages.size() == page) {
            return;
        }

        Menu menu = this.pages.get(page);

        List<CustomItem> itemList = new ArrayList<>(items);
        List<Integer> slots = new ArrayList<>();
        menu.getTypeItems(type).values().forEach(slots::addAll);

        for (int slot : slots) {
            if (itemList.isEmpty()) {
                return;
            }

            itemList.get(0).setSlots(slot);

            menu.setCustomItem(itemList.get(0));
            itemList.remove(0);
        }

        if (!itemList.isEmpty()) {
            setCustomItems(page + 1, itemList, type);
        }
    }

    public void open(Player target) {
        this.pages.get(0).open(target);
    }

}
