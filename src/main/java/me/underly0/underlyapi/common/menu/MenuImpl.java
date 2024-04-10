package me.underly0.underlyapi.common.menu;

import me.underly0.underlyapi.api.menu.Menu;
import me.underly0.underlyapi.builder.ItemBuilder;
import me.underly0.underlyapi.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MenuImpl implements InventoryHolder, Cloneable, Menu {


    private Inventory inventory;
    private List<String> inventoryWords;
    private Map<String, Object> words;
    public final Map<Integer, MenuAction> actionSlots = new HashMap<>();
    private final Map<String, String> placeholders = new HashMap<>();
    private boolean isBuilded = false;

    public MenuImpl(Map<String, Object> values) {
        String title = StringUtil.color((String) values.get("title"));
        List<String> layout = (List<String>) values.get("inventory");

        this.words = (Map<String, Object>) values.get("words");

        this.inventoryWords = getInventoryWords(layout);
        this.inventory = Bukkit.createInventory(this, inventoryWords.size(), title);
    }



//    public Menu cloneMenu() {
//        Menu clonedMenu = new Menu(this.menuSection);
//        clonedMenu.actionSlots.putAll(this.actionSlots);
//        clonedMenu.placeholders.putAll(this.placeholders);
//
//        if (this.isBuilded) {
//            clonedMenu.build();
//        }
//
//        return clonedMenu;
//    }

    public Menu build() {
        Map<String, List<Integer>> items = getItems();

        items.forEach((word, slots) -> {
            Map<String, Object> item = (Map<String, Object>) words.get(word);

            ItemStack itemStack = new ItemBuilder(item)
                    .setPlaceholders(placeholders)
                    .build();

            setItems(itemStack, slots);
        });
        this.isBuilded = true;
        return this;
    }

    public Menu addPlaceholder(String value, String key) {
        this.placeholders.put(value, key);
        return this;
    }

    public Menu addPlaceholders(Map<String, String> placeholders) {
        this.placeholders.putAll(placeholders);
        return this;
    }

    public void addAction(String actionName, MenuAction action) {
        AtomicInteger index = new AtomicInteger();

        this.inventoryWords.forEach(invWord -> {
            Map<String, Object> item = (Map<String, Object>) words.get(invWord);

            if (item == null || !item.containsKey("actions")) {
                index.getAndIncrement();
                return;
            }

            ((List<String>) item.get("actions"))
                    .forEach(wordAction -> {
                        if (wordAction.equals(actionName)) {
                            this.actionSlots.put(index.get(), action);
                        }
                    });

            index.getAndIncrement();
        });
    }

    public void open(Player target) {
        target.openInventory(this.inventory);
    }


    public void setItems(ItemStack item, List<Integer> slots) {
        slots.forEach(slot -> this.inventory.setItem(slot, item));
    }

    public void setItems(ItemStack item, int ...slots) {
        Arrays.stream(slots).forEach(slot -> this.inventory.setItem(slot, item));
    }

    public void setItems(ItemStack item, String type) {
        Map<String, List<Integer>> items = getTypeItems(type);

        items.forEach((word, slots) -> setItems(item, slots));
    }

    public void setCustomItem(CustomItem customItem) {
        ItemStack item = customItem.getItem();
        List<Integer> slots = customItem.getSlots();
        MenuAction action = customItem.getAction();

        setItems(item, slots);
        slots.forEach(slot -> actionSlots.put(slot, action));
    }

    public Map<String, List<Integer>> getTypeItems(String type) {
        Map<String, List<Integer>> slots = new HashMap<>();

        words.forEach((word, _item) -> {
            Map<String, Object> item = (Map<String, Object>) _item;

            if (!item.containsKey("type") || !item.get("type").equals(type))
                return;

            List<Integer> wordSlots = new ArrayList<>();

            AtomicInteger index = new AtomicInteger();
            inventoryWords.forEach(invWord -> {
                if (word.equals(invWord)) {
                    wordSlots.add(index.get());
                }
                index.getAndIncrement();
            });

            slots.put(word, wordSlots);
        });

        return slots;
    }

    private Map<String, List<Integer>> getItems() {
        Map<String, List<Integer>> slots = new HashMap<>();

        words.keySet().forEach(word -> {
            List<Integer> wordSlots = new ArrayList<>();

            AtomicInteger index = new AtomicInteger();
            inventoryWords.forEach(invWord -> {
                if (word.equals(invWord)) {
                    wordSlots.add(index.get());
                }
                index.getAndIncrement();
            });

            slots.put(word, wordSlots);
        });

        return slots;
    }

    private List<String> getInventoryWords(List<String> lines) {
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split("")))
                .collect(Collectors.toList());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public MenuImpl clone() {
        try {
            return (MenuImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}