package me.underly0.underlyapi.api.menu;

import me.underly0.underlyapi.common.menu.action.MenuQuoteAction;
import me.underly0.underlyapi.common.menu.item.CustomItem;
import me.underly0.underlyapi.common.menu.action.MenuAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface Menu {

    Menu build();

    Menu addPlaceholder(String value, String key);

    Menu addPlaceholders(Map<String, String> placeholders);

    void addAction(String actionName, MenuAction action);
    void addQuoteAction(String actionName, MenuQuoteAction action);

    void open(Player target);

    void setItems(ItemStack item, List<Integer> slots);

    void setItems(ItemStack item, int... slots);

    void setItems(ItemStack item, String type);

    void setCustomItem(CustomItem customItem);

    Map<String, List<Integer>> getTypeItems(String type);

}
