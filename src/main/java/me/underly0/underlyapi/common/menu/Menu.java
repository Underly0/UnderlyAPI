package me.underly0.underlyapi.common.menu;

import me.underly0.underlyapi.builder.ItemBuilder;
import me.underly0.underlyapi.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Menu implements InventoryHolder {

    private ConfigurationSection menuSection;
    private Inventory inventory;
    private List<String> inventoryWords;
    private List<String> words;
    public final Map<Integer, MenuAction> actionSlots = new HashMap<>();
    private final Map<String, String> placeholders = new HashMap<>();
    private boolean isBuilded = false;

    public Menu(ConfigurationSection menuSection) {
        this.menuSection = menuSection;

        String title = StringUtil.color(menuSection.getString("title"));
        this.inventoryWords = getInventoryWords(menuSection.getStringList("inventory"));
        this.words = new ArrayList<>(menuSection.getConfigurationSection("words").getKeys(false));

        this.inventory = Bukkit.createInventory(this, inventoryWords.size(), title);
    }


    public Menu cloneMenu() {
        Menu clonedMenu = new Menu(this.menuSection);
        clonedMenu.actionSlots.putAll(this.actionSlots);
        clonedMenu.placeholders.putAll(this.placeholders);

        if (this.isBuilded) {
            clonedMenu.build();
        }

        return clonedMenu;
    }

    public Menu build() {
        Map<String, List<Integer>> items = getItems();

        items.forEach((word, slots) -> {
            setItems(
                    new ItemBuilder(menuSection.getConfigurationSection("words." + word).getValues(false))
                            .setPlaceholders(placeholders)
                            .build(),
                    slots
            );
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
            ConfigurationSection wordSection = this.menuSection.getConfigurationSection("words." + invWord);

            if (wordSection == null || !wordSection.isList("actions")) {
                index.getAndIncrement();
                return;
            }

            wordSection.getStringList("actions")
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

        words.forEach(word -> {
            ConfigurationSection wordSection = menuSection.getConfigurationSection("words." + word);

            if (!wordSection.contains("type") || !wordSection.getString("type").equals(type))
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

        words.forEach(word -> {
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
}